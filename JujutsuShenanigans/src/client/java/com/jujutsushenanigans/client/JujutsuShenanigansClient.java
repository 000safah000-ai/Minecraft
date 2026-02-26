package com.jujutsushenanigans.client;

import com.jujutsushenanigans.JujutsuShenanigans;
import com.jujutsushenanigans.item.custom.SixEyesItem;
import com.jujutsushenanigans.networking.ModPackets;
import foundry.veil.api.client.render.VeilRenderSystem;
import foundry.veil.api.client.render.post.PostProcessingManager;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.Identifier;

import net.fabricmc.fabric.api.client.rendering.v1.LivingEntityFeatureRendererRegistrationCallback;
import net.minecraft.client.render.entity.PlayerEntityRenderer;

import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3d;
import com.jujutsushenanigans.item.ModItems;

@Environment(EnvType.CLIENT)
public class JujutsuShenanigansClient implements ClientModInitializer {

    public static final Identifier SIX_EYES_PIPELINE = JujutsuShenanigans.id("six_eyes_hallucination");

    @Override
    public void onInitializeClient() {
        JujutsuShenanigans.LOGGER.info("Initializing Jujutsu Shenanigans Client...");

        // ── Client Networking ──
        ModPackets.registerClientPackets();

        // ── Veil Render Pipeline ──
        // Manage the Six Eyes post-processing pipeline
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player != null) {
                // Post-Processing Pipeline
                try {
                    PostProcessingManager postManager = VeilRenderSystem.renderer().getPostProcessingManager();
                    if (client.player instanceof SixEyesItem.SixEyesState state && state.isSixEyesActive()) {
                        if (!postManager.isActive(SIX_EYES_PIPELINE)) {
                            postManager.add(SIX_EYES_PIPELINE);
                        }
                    } else {
                        if (postManager.isActive(SIX_EYES_PIPELINE)) {
                            postManager.remove(SIX_EYES_PIPELINE);
                        }
                    }
                } catch (Exception e) {
                    // Graceful fallback if Veil pipeline isn't ready
                }
            }
        });

        JujutsuShenanigans.LOGGER.info("Jujutsu Shenanigans Client initialized successfully.");
    }
}
