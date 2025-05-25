package com.ai.Tool-Leveling.network;

import com.eai.Tool-Leveling.ToolLevelingMod;
import com.ai.Tool-Leveling.Tool-LevelingScreenHandler;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.util.math.BlockPos;

public class NetworkHandler {
    public static final Identifier ENCHANT_PACKET = new Identifier(Tool-LevelingMod.MODID, "enchant_item");

    public static void register() {
        ServerPlayNetworking.registerGlobalReceiver(ENCHANT_PACKET, (server, player, handler, buf, sender) -> {
            Enchantment ench = Registry.ENCHANTMENT.get(buf.readIdentifier());
            int level = buf.readInt();
            BlockPos pos = buf.readBlockPos();
            server.execute(() -> {
                if (player.currentScreenHandler instanceof Tool-LevelingScreenHandler sh)
                    sh.applyEnchantment(ench, level, player);
            });
        });
    }
}
