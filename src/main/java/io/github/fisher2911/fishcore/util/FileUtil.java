/*
 * MIT License
 *
 * Copyright (c) 2021 Fisher2911
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package io.github.fisher2911.fishcore.util;

import io.github.fisher2911.fishcore.FishCore;
import io.github.fisher2911.fishcore.logger.Logger;
import io.github.fisher2911.fishcore.message.ErrorMessages;
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
        final MessageHandler messageHandler = MessageHandler.getInstance();
        final Logger logger = plugin.logger();

        final Material material = Utils.stringToEnum(
                Utils.replaceIfNull(section.getString("material"),
                        "null",
                        m -> logger.configWarning(String.format(ErrorMessages.INVALID_ITEM, m, "material", fileName))),
                Material.class,
                Material.AIR);

        int amount = Math.max(section.getInt("amount"), 1);

        final String name = messageHandler.parseStringToString(
                Utils.replaceIfNull(section.getString("name"), "")
        );

        final boolean unbreakable = section.getBoolean("unbreakable");

        final boolean glowing = section.getBoolean("glowing");

        final List<String> lore = section.getStringList("lore").
                stream().map(messageHandler::parseStringToString).
                collect(Collectors.toList());

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

        final ItemBuilder itemBuilder;

        if (material == Material.PLAYER_HEAD) {
            final String texture = section.getString("texture");
            final String ownerName = section.getString("owner");
            itemBuilder = SkullBuilder.
                    create();

            if (texture != null) {
                ((SkullBuilder) itemBuilder).texture(texture);
            } else if (ownerName != null) {
                final OfflinePlayer player = Bukkit.getOfflinePlayer(ownerName);
                ((SkullBuilder) itemBuilder).owner(player);
            }
        } else if (LeatherArmorBuilder.isLeatherArmor(material)) {
            itemBuilder = LeatherArmorBuilder.from(material);
            final Color color = section.getColor("color");
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
                enchants(enchantmentMap, true).
                itemFlags(itemFlags).
                build();
    }

    /**
     *
     * @param section Section that enchantments are loaded from
     * @param fileName name of file section is in
     * @return Map of enchantments and their levels
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
