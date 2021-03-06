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

package io.github.fisher2911.fishcore.util.builder;

import io.github.fisher2911.fishcore.util.helper.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ItemBuilder {

    protected Material material;
    protected int amount;
    protected ItemMeta itemMeta;

    /**
     *
     * @param material builder material
     */

    ItemBuilder(final Material material) {
        this.material = material;
        this.itemMeta = Bukkit.getItemFactory().getItemMeta(material);
    }

    /**
     *
     * @param itemStack builder ItemStack
     */

    ItemBuilder(final ItemStack itemStack) {
        this.material = itemStack.getType();
        this.itemMeta = itemStack.hasItemMeta() ? itemStack.getItemMeta() : Bukkit.getItemFactory().getItemMeta(this.material);
    }

    /**
     *
     * @param material builder material
     * @return
     */

    public static ItemBuilder from(final Material material) {
        return new ItemBuilder(material);
    }

    /**
     *
     * @param itemStack builder ItemStack
     * @return
     */

    public static ItemBuilder from(final ItemStack itemStack) {
        return new ItemBuilder(itemStack);
    }

    /**
     *
     * @param amount ItemStack amount
     * @return this
     */

    public ItemBuilder amount(final int amount) {
        this.amount = Math.min(Math.max(1, amount), 64);
        return this;
    }

    /**
     *
     * @param name ItemStack name
     * @return this
     */

    public ItemBuilder name(final String name) {
        this.itemMeta.setDisplayName(name);
        return this;
    }

    /**
     * Sets placeholders to the item's name
     * @param placeholders placeholders
     */

    public ItemBuilder namePlaceholders(final Map<String, String> placeholders) {
        final String name = StringUtils.
                applyPlaceholders(this.itemMeta.getDisplayName(), placeholders);
        this.itemMeta.setDisplayName(name);
        return this;
    }

    /**
     *
     * @param lore ItemStack lore
     * @return this
     */

    public ItemBuilder lore(final List<String> lore) {
        this.itemMeta.setLore(lore);
        return this;
    }

    /**
     * Sets placeholders to the item's lore
     * @param placeholders placeholders
     */


    public ItemBuilder lorePlaceholders(final Map<String, String> placeholders) {
        final List<String> lore = new ArrayList<>();

        final List<String> previousLore = this.itemMeta.getLore();

        if (previousLore == null) {
            return this;
        }

        for (final String line : previousLore) {
            lore.add(StringUtils.applyPlaceholders(
                    line, placeholders
            ));
        }

        this.itemMeta.setLore(lore);
        return this;
    }

    /**
     *
     * @param unbreakable whether the ItemStack is unbreakable
     * @return this
     */

    public ItemBuilder unbreakable(final boolean unbreakable) {
        this.itemMeta.setUnbreakable(unbreakable);
        return this;
    }

    public ItemBuilder glow(final boolean glow) {
        if (glow) {
            this.itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            this.itemMeta.addEnchant(Enchantment.LUCK, 1, true);
        }
        return this;
    }

    /**
     *
     * @param enchantments enchants to be added to the ItemStack
     * @param ignoreLeveLRestrictions whether to ignore enchantment level restrictions
     * @return this
     */

    public ItemBuilder enchants(final Map<Enchantment, Integer> enchantments, boolean ignoreLeveLRestrictions) {
        enchantments.forEach((enchantment, level) -> this.itemMeta.addEnchant(enchantment, level, ignoreLeveLRestrictions));
        return this;
    }

    /**
     *
     * @param itemFlags ItemStack ItemFlags
     * @return this
     */

    public ItemBuilder itemFlags(final Set<ItemFlag> itemFlags) {
        this.itemMeta.addItemFlags(itemFlags.toArray(new ItemFlag[0]));
        return this;
    }

    /**
     *
     * @param modelData ItemStack modelData
     * @return this
     */

    public ItemBuilder modelData(final int modelData) {
        this.itemMeta.setCustomModelData(modelData);
        return this;
    }

    /**
     *
     * @return built ItemStack
     */

    public ItemStack build() {
        final ItemStack itemStack = new ItemStack(this.material, Math.max(this.amount, 1));
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

}
