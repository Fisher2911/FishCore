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

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.util.EnumSet;
import java.util.Set;

public class LeatherArmorBuilder extends ItemBuilder{

    private static final Set<Material> VALID_ARMOR = EnumSet.of(Material.LEATHER_BOOTS,
            Material.LEATHER_LEGGINGS, Material.LEATHER_CHESTPLATE, Material.LEATHER_HELMET);

    /**
     *
     * @param material ItemStack material
     */

    LeatherArmorBuilder(final Material material) {
        super(material);
    }

    /**
     *
     * @param itemStack ItemStack
     */

    LeatherArmorBuilder(final ItemStack itemStack) {
        super(itemStack);
    }

    /**
     *
     * @param material ItemStack material
     * @return this
     * @throws IllegalArgumentException thrown if material is not leather armor
     */

    public static LeatherArmorBuilder from(final Material material) throws IllegalArgumentException {
        if (!VALID_ARMOR.contains(material)) {
            throw new IllegalArgumentException(material.name() + " is not leather armor!");
        }
        return new LeatherArmorBuilder(material);
    }

    /**
     *
     * @param itemStack ItemStack
     * @return this
     * @throws IllegalArgumentException thrown if itemStack's type is not leather armor
     */

    public static LeatherArmorBuilder from(final ItemStack itemStack) throws IllegalArgumentException {
        final Material material = itemStack.getType();
        if (!VALID_ARMOR.contains(material)) {
            throw new IllegalArgumentException(material.name() + " is not leather armor!");
        }
        return new LeatherArmorBuilder(itemStack);
    }

    /**
     *
     * @param color armor color
     * @return this
     */

    public LeatherArmorBuilder color(final Color color) {
        if (itemMeta instanceof final LeatherArmorMeta meta) {
            meta.setColor(color);
            this.itemMeta = meta;
        }
        return this;
    }

    /**
     *
     * @param material checked material
     * @return true if is leather armor, else false
     */

    public static boolean isLeatherArmor(final Material material) {
        return VALID_ARMOR.contains(material);
    }
}
