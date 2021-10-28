package io.github.fisher2911.fishcore.config.serializer;

import io.github.fisher2911.fishcore.message.MessageHandler;
import io.github.fisher2911.fishcore.util.builder.ItemBuilder;
import io.github.fisher2911.fishcore.util.builder.LeatherArmorBuilder;
import io.github.fisher2911.fishcore.util.builder.SkullBuilder;
import io.github.fisher2911.fishcore.util.helper.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.OfflinePlayer;
import org.bukkit.Registry;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;
import org.spongepowered.configurate.serialize.TypeSerializer;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class ItemSerializer implements TypeSerializer<ItemStack> {

    public static final ItemSerializer INSTANCE = new ItemSerializer();

    private static final String MATERIAL = "material";
    private static final String AMOUNT = "amount";
    private static final String NAME = "name";
    private static final String UNBREAKABLE = "unbreakable";
    private static final String GLOWING = "glowing";
    private static final String LORE = "lore";
    private static final String MODEL_DATA = "model-data";
    private static final String ENCHANTS = "enchants";
    private static final String ITEM_FLAGS = "item-flags";
    private static final String TEXTURE = "texture";
    private static final String OWNER = "owner";
    private static final String COLOR = "color";

    private ItemSerializer() {}

    private ConfigurationNode nonVirtualNode(final ConfigurationNode source, final Object... path) throws SerializationException {
        if (!source.hasChild(path)) {
            throw new SerializationException("Required field " + Arrays.toString(path) + " was not present in node");
        }
        return source.node(path);
    }

    @Override
    public ItemStack deserialize(final Type type, final ConfigurationNode source) throws SerializationException {
        final MessageHandler messageHandler = MessageHandler.getInstance();
        final ConfigurationNode materialNode = this.nonVirtualNode(source, MATERIAL);
        final ConfigurationNode amountNode = source.node(AMOUNT);
        final ConfigurationNode nameNode = source.node(NAME);
        final ConfigurationNode unbreakableNode = source.node(UNBREAKABLE);
        final ConfigurationNode glowingNode = source.node(GLOWING);
        final ConfigurationNode loreNode = source.node(LORE);
        final ConfigurationNode modelDataNode = source.node(MODEL_DATA);
        final ConfigurationNode enchantsNode = source.node(ENCHANTS);
        final ConfigurationNode itemFlagsNode = source.node(ITEM_FLAGS);
        final ConfigurationNode textureNode = source.node(TEXTURE);
        final ConfigurationNode ownerNode = source.node(OWNER);
        final ConfigurationNode colorNode = source.node(COLOR);


        final Material material = Utils.stringToEnum(Utils.replaceIfNull(materialNode.getString(), ""),
                Material.class, Material.AIR);
        final int amount = amountNode.getInt();
        final String name = messageHandler.parseStringToString(Utils.replaceIfNull(nameNode.getString(), ""));

        Bukkit.getLogger().info("Loaded name: " + name + ":::" + nameNode.getString());

        final boolean unbreakable = unbreakableNode.getBoolean();
        final boolean glowing = glowingNode.getBoolean();
        final List<String> lore = Utils.replaceIfNull(loreNode.getList(String.class), new ArrayList<String>()).
                stream().map(messageHandler::parseStringToString).collect(Collectors.toList());
        final int modelData = modelDataNode.getInt();
        final Set<ItemFlag> itemFlags = Utils.replaceIfNull(itemFlagsNode.getList(String.class), new ArrayList<String>()).
                stream().map(flag -> {
                    try {
                        return ItemFlag.valueOf(flag.toUpperCase());
                    } catch (final Exception ignored) {
                        return null;
                    }
                }).collect(Collectors.toSet());
        final String texture = textureNode.getString();
        final String owner = ownerNode.getString();
        final Color color = colorNode.get(Color.class);

        final Map<Enchantment, Integer> enchantments =
                Utils.replaceIfNull(enchantsNode.getList(String.class),
                        new ArrayList<String>()).
                        stream().
                        collect(Collectors.toMap(enchantmentString -> {

                            if (!enchantmentString.contains(":")) {
                                return null;
                            }

                            final NamespacedKey namespacedKey = NamespacedKey.minecraft(enchantmentString.
                                    split(":")[0].
                                    toLowerCase());
                            return Registry.ENCHANTMENT.get(namespacedKey);

                        }, enchantmentString -> {
                            if (!enchantmentString.contains(":")) {
                                return 0;
                            }
                            try {
                                return Integer.parseInt(enchantmentString.split(":")[1]);
                            } catch (final NumberFormatException exception) {
                                return 0;
                            }
                        }));


        final ItemBuilder itemBuilder;

        if (material == Material.PLAYER_HEAD) {
            itemBuilder = SkullBuilder.
                    create();

            if (texture != null) {
                ((SkullBuilder) itemBuilder).texture(texture);
            } else if (owner != null) {
                final OfflinePlayer player = Bukkit.getOfflinePlayer(owner);
                ((SkullBuilder) itemBuilder).owner(player);
            }
        } else if (LeatherArmorBuilder.isLeatherArmor(material)) {
            itemBuilder = LeatherArmorBuilder.from(material);
            if (color != null) {
                ((LeatherArmorBuilder) itemBuilder).color(color);
            }
        } else {
            itemBuilder = ItemBuilder.from(material);
        }

        return itemBuilder.
                amount(amount).
                name(name).
                unbreakable(unbreakable).
                glow(glowing).
                lore(lore).
                modelData(modelData).
                enchants(enchantments, true).
                itemFlags(itemFlags).
                build();

    }

    @Override
    public void serialize(final Type type, @Nullable final ItemStack obj, final ConfigurationNode node) throws SerializationException {

    }
}
