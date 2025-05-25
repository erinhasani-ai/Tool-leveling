package com.ai.Tool-Leveling;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;

public class SimpleInventoryWrapper implements Inventory {
    private final DefaultedList<ItemStack> inv;

    public SimpleInventoryWrapper(DefaultedList<ItemStack> inv) {
        this.inv = inv;
    }

    @Override public int size() {
        return inv.size();
    }
    @Override public boolean isEmpty() {
        for (ItemStack s : inv) if (!s.isEmpty()) return false;
        return true;
    }
    @Override public ItemStack getStack(int slot) {
        return inv.get(slot);
    }
    @Override public ItemStack removeStack(int slot, int amount) {
        return Inventories.splitStack(inv, slot, amount);
    }
    @Override public ItemStack removeStack(int slot) {
        return Inventories.removeStack(inv, slot);
    }
    @Override public void setStack(int slot, ItemStack stack) {
        inv.set(slot, stack);
    }
    @Override public void markDirty() {}
    @Override public boolean canPlayerUse(PlayerEntity player) { return true; }
    @Override public void clear() { inv.clear(); }
}