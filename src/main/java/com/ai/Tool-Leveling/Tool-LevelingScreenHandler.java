package com.ai.Tool-Leveling;

import com.eai.Tool-Leveling.block.Tool-LevelingBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.util.registry.Registry;
import net.minecraft.item.ItemStack;

public class Tool-LevelingScreenHandler extends ScreenHandler {
    private final Tool-LevelingBlockEntity blockEntity;

    public Tool-LevelingScreenHandler(int syncId, PlayerInventory inv, PacketByteBuf buf) {
        this(syncId, inv, (Tool-LevelingBlockEntity) inv.player.world.getBlockEntity(buf.readBlockPos()));
    }

    public Tool-LevelingScreenHandler(int syncId, PlayerInventory inv, PowerEnchanterBlockEntity entity) {
        super(Tool-LevelingMod.SCREEN_HANDLER_TYPE, syncId);
        this.blockEntity = entity;
        // Input slot
        addSlot(new Slot(new SimpleInventoryWrapper(blockEntity.getInventory()), 0, 80, 35));
        // Player inv
        for (int row = 0; row < 3; row++)
            for (int col = 0; col < 9; col++)
                addSlot(new Slot(inv, col + row * 9 + 9, 8 + col * 18, 84 + row * 18));
        for (int col = 0; col < 9; col++)
            addSlot(new Slot(inv, col, 8 + col * 18, 142));
    }

    @Override public boolean canUse(PlayerEntity player) { return true; }

    public void applyEnchantment(Enchantment ench, int level, PlayerEntity player) {
        ItemStack stack = blockEntity.getInventory().get(0);
        if (stack.isEmpty()) return;
        int current = stack.getEnchantmentLevel(ench);
        int delta = level - current;
        int cost = delta * PowerEnchanterMod.CONFIG.xpCostPerLevel;
        if (player.experienceLevel >= cost) {
            player.addExperienceLevels(-cost);
            stack.addEnchantment(ench, level);
            blockEntity.markDirty();
        }
    }
}