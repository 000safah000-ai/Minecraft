package com.jujutsuminecraft.client;

import net.fabricmc.api.ClientModInitializer;

public class JujutsuMinecraftClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        LaserKeybind.register();
    }
}