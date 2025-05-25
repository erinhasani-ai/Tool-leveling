package com.ai.Tool-Leveling.block;

import com.ai.Tool-Leveling.Tool-LevelingMod;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.text.Text;
import net.minecraft.text.LiteralText;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;

public class Tool-LevelingBlockEntity extends BlockEntity implements NamedScreenHandlerFactory {
    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(1, ItemStack.EMPTY);

    public Tool-LevelingBlockEntity(BlockPos pos, BlockState state) {
        super(Tool-LevelingMod.TOOL-LEVELING_BE_TYPE, pos, state);
    }

    @Override public Text getDisplayName() { return new LiteralText("Tool-Leverer"); }

    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
        return new Tool-LevelingScreenHandler(syncId, inv, this);
    }

    public DefaultedList<ItemStack> getInventory() { return inventory; }

    @Override public void readNbt(NbtCompound tag) {
        super.readNbt(tag);
        Inventories.readNbt(tag, inventory);
    }

    @Override public NbtCompound writeNbt(NbtCompound tag) {
        super.writeNbt(tag);
        Inventories.writeNbt(tag, inventory);
        return tag;
    }
}