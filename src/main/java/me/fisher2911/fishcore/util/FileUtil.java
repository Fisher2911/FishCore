package me.fisher2911.fishcore.util;

import me.fisher2911.fishcore.FishCore;
import me.fisher2911.fishcore.logger.Logger;
import me.fisher2911.fishcore.message.ErrorMessages;
import me.fisher2911.fishcore.util.builder.ItemBuilder;
import me.fisher2911.fishcore.util.helper.Utils;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class FileUtil {

    private static final FishCore plugin;

    static {
        plugin = FishCore.getPlugin(FishCore.class);
    }

    public static File getFile(final String name) {
        final File file = new File(plugin.getDataFolder(), name);
        if (!file.exists()) {
            plugin.saveResource(name, false);
        }
        return file;
    }

    /**
     *
     * @param section Section that ItemStack is loaded from
     * @param fileName Name of the file the section is in
     * @return loaded ItemStack
     */

    public static ItemStack loadItemStack(final ConfigurationSection section, final String fileName) {
        final Logger logger = plugin.logger();

        final Material material = Utils.stringToEnum(
                Utils.replaceIfNull(section.getString("material"),
                        "null",
                        m -> logger.configWarning(String.format(ErrorMessages.INVALID_ITEM, m, "material", fileName))),
                Material.class,
                Material.AIR);

        int amount = Math.max(section.getInt("amount"), 1);

        final String name = Utils.replaceIfNull(section.getString("name"), "");

        final boolean unbreakable = section.getBoolean("unbreakable");

        final boolean glowing = section.getBoolean("glowing");

        final List<String> lore = section.getStringList("lore");

        final int modelData = section.getInt("model-data");

        final Map<Enchantment, Integer> enchantmentMap = new HashMap<>();

        final ConfigurationSection enchantmentSection = section.getConfigurationSection("enchants");

        Utils.doIfNotNull(enchantmentSection, checkedSection ->
                enchantmentMap.putAll(loadItemStackEnchantments(checkedSection, fileName)));

        final Set<ItemFlag> itemFlags = section.getStringList("item-flags").
                stream().
                map(itemFlag -> Utils.stringToEnum(itemFlag, ItemFlag.class, null)).
                filter(itemFlag -> itemFlag != null).
                collect(Collectors.toSet());

        return ItemBuilder.
                from(material).
                amount(amount).
                name(name).
                unbreakable(unbreakable).
                glow(glowing).
                lore(lore).
                modelData(modelData).
                enchants(enchantmentMap, true).
                itemFlags(itemFlags).
                build();
    }

    /**
     *
     * @param section Section that enchantments are loaded from
     * @param fileName name of file section is in
     * @return Map<Enchantment, Integer> of enchantments
     */

    public static Map<Enchantment, Integer> loadItemStackEnchantments(final ConfigurationSection section, final String fileName) {
        final Map<Enchantment, Integer> enchantmentMap = new HashMap<>();
        for (final String enchantAsString : section.getKeys(false)) {
            final int level = section.getInt(enchantAsString);
            final NamespacedKey namespacedKey = NamespacedKey.minecraft(enchantAsString.toLowerCase());
            final Enchantment enchantment = Registry.ENCHANTMENT.get(namespacedKey);

            if (enchantment != null) {
                enchantmentMap.put(enchantment, level);
            }
        }
        return enchantmentMap;
    }

}
