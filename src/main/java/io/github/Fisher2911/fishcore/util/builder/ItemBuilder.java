package io.github.Fisher2911.fishcore.util.builder;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class ItemBuilder {

    private Material material;
    private int amount;
    private ItemMeta itemMeta;

    /**
     *
     * @param material builder material
     */

    private ItemBuilder(final Material material) {
        this.material = material;
        this.itemMeta = Bukkit.getItemFactory().getItemMeta(material);
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
     *
     * @param lore ItemStack lore
     * @return this
     */

    public ItemBuilder lore(final List<String> lore) {
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
        final ItemStack itemStack = new ItemStack(this.material, this.amount);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

}
