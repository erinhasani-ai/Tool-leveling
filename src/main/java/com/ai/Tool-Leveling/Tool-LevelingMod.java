package com.ai.Tool-Leveling;

import com.ai.Tool-Leveling.block.Tool-LevelingBlock;
import com.ai.Tool-Leveling.block.Tool-LevelingBlockEntity;
import com.ai.Tool-Leveling.network.NetworkHandler;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class Tool-LevelingMod implements ModInitializer {
    public static final String MODID = "Tool-Leveling";
    public static Tool-LevelingConfig CONFIG;
    public static Block TOOL-LEVELING_BLOCK;
    public static BlockEntityType<Tool-LevelingBlockEntity> TOOL-LEVELING_BE_TYPE;
    public static ScreenHandlerType<Tool-LevelingScreenHandler> SCREEN_HANDLER_TYPE;

    @Override
    public void onInitialize() {
        AutoConfig.register(Tool-LevelingConfig.class, JanksonConfigSerializer::new);
        CONFIG = AutoConfig.getConfigHolder(Tool-LevelingConfig.class).getConfig();

        TOOL-LEVELING_BLOCK = Registry.register(
                Registry.BLOCK,
                new Identifier(MODID, "Tool-Leveling"),
                new Tool-LevelingBlock(Block.Settings.of(Material.METAL).strength(3.5f).nonOpaque())
        );

        TOOL-LEVELING_BE_TYPE = Registry.register(
                Registry.BLOCK_ENTITY_TYPE,
                new Identifier(MODID, "tool-leveling_entity"),
                BlockEntityType.Builder.create(PowerEnchanterBlockEntity::new, POWER_ENCHANTER_BLOCK).build(null)
        );

        SCREEN_HANDLER_TYPE = ScreenHandlerRegistry.registerSimple(
                new Identifier(MODID, "tool-leveling_ui"),
                Tool_LevelingScreenHandler::new
        );

        NetworkHandler.register();
    }
}
