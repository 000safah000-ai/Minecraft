package com.jujutsuminecraft.client;

import com.jujutsuminecraft.client.render.LaserSatinPostProcess;
import com.jujutsuminecraft.client.render.LaserVeilRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.client.MinecraftClient;

public class JujutsuMinecraftClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        LaserKeybind.register();
        LaserSatinPostProcess.init();
        LaserVeilRenderer.register();
        
        WorldRenderEvents.LAST.register(context -> {
            if (LaserKeybind.isLaserActive) {
                // Raycast distance logic placeholder (will need real raycast later)
                float distance = 50.0f;
                float time = (context.tickDelta() + MinecraftClient.getInstance().player.age) / 20.0f;
                
                // Render laser core
                LaserVeilRenderer.renderLaserCore(time, distance, context.matrixStack());
                
                // Render Satin post process
                if (LaserSatinPostProcess.LASER_VIGNETTE_BLUR != null) {
                    LaserSatinPostProcess.LASER_VIGNETTE_BLUR.render(context.tickDelta());
                }
            }
        });
    }
}