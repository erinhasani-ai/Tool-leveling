package com.ai.Tool-Leveling.client;

import net.fabricmc.api.ClientModInitializer;

public class Tool-LevelingClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ScreenRegistry.register(
                PowerEnchanterMod.SCREEN_HANDLER_TYPE,
                PowerEnchanterScreen::new
        );
    }
}