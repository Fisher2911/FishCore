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

package io.github.fisher2911.fishcore.economy;

import io.github.fisher2911.fishcore.FishCore;
import io.github.fisher2911.fishcore.message.MessageHandler;
import io.github.fisher2911.fishcore.message.MessageHandlerRegistry;
import io.github.fisher2911.fishcore.message.Messages;
import io.github.fisher2911.fishcore.user.BaseUser;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Cost {

    private final double moneyCost;
    private final List<ItemStack> itemStackCost;

    public Cost(final double moneyCost, final List<ItemStack> itemStackCost) {
        this.moneyCost = moneyCost;
        this.itemStackCost = Collections.unmodifiableList(itemStackCost);
    }

    /**
     *
     * @return money cost
     */

    public double getMoneyCost() {
        return moneyCost;
    }

    /**
     *
     * @return UnmodifiableList of ItemStack costs
     */


    public boolean hasEnough(final Inventory inventory, final double balance) {
        return this.hasEnoughMoney(balance) && this.hasEnoughItems(inventory);
    }

    public boolean hasEnough(final List<ItemStack> itemStacks, final float balance) {
        return this.hasEnoughMoney(balance) && this.hasEnoughItems(itemStacks);
    }

    public boolean hasEnoughMoney(final double balance) {
        return balance >= this.moneyCost;
    }

    public boolean hasEnoughItems(final List<ItemStack> itemStacks) {
        return itemStacks.containsAll(this.itemStackCost);
    }

    public boolean hasEnoughItems(final Inventory inventory) {
        return this.hasEnoughItems(Arrays.asList(inventory.getStorageContents()));
    }

    public boolean pay(final BaseUser user) {
        final MessageHandler messageHandler = MessageHandlerRegistry.REGISTRY.get(FishCore.class);
        final Player player = user.getPlayer();
        if (player == null) {
            return false;
        }
        final Inventory inventory = player.getInventory();
        if (!hasEnoughItems(inventory)) {
            messageHandler.sendMessage(player, Messages.NOT_ENOUGH_ITEMS);
            return false;
        }
        if (!hasEnough(inventory, user.getMoney())) {
            messageHandler.sendMessage(player, Messages.NOT_ENOUGH_MONEY);
            return false;
        }
        inventory.removeItem(this.itemStackCost.toArray(new ItemStack[0]));
        user.subtractMoney(this.moneyCost);
        return true;
    }
}
