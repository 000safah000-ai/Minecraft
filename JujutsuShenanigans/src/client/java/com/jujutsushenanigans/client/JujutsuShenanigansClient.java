package com.jujutsushenanigans.client;

import com.jujutsushenanigans.JujutsuShenanigans;
import com.jujutsushenanigans.item.custom.SixEyesItem;
import com.jujutsushenanigans.networking.ModPackets;
import foundry.veil.api.client.render.VeilRenderSystem;
import foundry.veil.api.client.render.post.PostProcessingManager;
import foundry.veil.api.client.render.shader.program.ShaderProgram;
import foundry.veil.api.event.VeilEventPlatform;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.Identifier;

import net.fabricmc.fabric.api.client.rendering.v1.LivingEntityFeatureRendererRegistrationCallback;
import net.minecraft.client.render.entity.PlayerEntityRenderer;

import foundry.veil.api.quasar.particle.ParticleSystemManager;
import foundry.veil.api.quasar.particle.ParticleEmitter;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3d;
import com.jujutsushenanigans.item.ModItems;

@Environment(EnvType.CLIENT)
public class JujutsuShenanigansClient implements ClientModInitializer {

    public static final Identifier SIX_EYES_PIPELINE = JujutsuShenanigans.id("six_eyes_hallucination");
    public static final Identifier SIX_EYES_SHADER = JujutsuShenanigans.id("six_eyes_hallucination");
    public static final Identifier SIX_EYES_PARTICLE = JujutsuShenanigans.id("six_eyes_glow");

    @Override
    public void onInitializeClient() {
        JujutsuShenanigans.LOGGER.info("Initializing Jujutsu Shenanigans Client...");

        // ── Client Networking ──
        ModPackets.registerClientPackets();

        // ── Entity Renderers & Layers ──
        // (Removed PNG-based feature renderer, using particles instead)

        // ── Veil Render Pipeline ──
        // Manage the Six Eyes post-processing pipeline and particles
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player != null) {
                // 1. Post-Processing Pipeline
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

                // 2. Six Eyes Passive Particle Effect
                boolean hasSixEyes = false;
                for (ItemStack stack : client.player.getInventory().main) {
                    if (stack.isOf(ModItems.SIX_EYES)) { hasSixEyes = true; break; }
                }
                if (!hasSixEyes) {
                    for (ItemStack stack : client.player.getInventory().offHand) {
                        if (stack.isOf(ModItems.SIX_EYES)) { hasSixEyes = true; break; }
                    }
                }

                if (hasSixEyes && client.world != null) {
                    try {
                        ParticleSystemManager manager = VeilRenderSystem.renderer().getParticleManager();
                        ParticleEmitter emitter = manager.createEmitter(SIX_EYES_PARTICLE);
                        if (emitter != null) {
                            // Calculate eye position based on player rotation
                            Vec3d eyePos = client.player.getEyePos();
                            Vec3d lookVec = client.player.getRotationVec(1.0f);
                            
                            // Offset slightly forward and to the sides for the two eyes
                            Vec3d rightEye = eyePos.add(lookVec.multiply(0.2)).add(lookVec.crossProduct(new Vec3d(0, 1, 0)).multiply(0.1));
                            Vec3d leftEye = eyePos.add(lookVec.multiply(0.2)).add(lookVec.crossProduct(new Vec3d(0, 1, 0)).multiply(-0.1));

                            // Spawn particles for both eyes
                            emitter.setPosition(rightEye.x, rightEye.y, rightEye.z);
                            manager.addParticleSystem(emitter);
                            
                            ParticleEmitter emitterLeft = manager.createEmitter(SIX_EYES_PARTICLE);
                            if (emitterLeft != null) {
                                emitterLeft.setPosition(leftEye.x, leftEye.y, leftEye.z);
                                manager.addParticleSystem(emitterLeft);
                            }
                        }
                    } catch (Exception e) {
                        // Ignore particle errors
                    }
                }
            }
        });

        // Inject uniforms for the Six Eyes shader
        VeilEventPlatform.INSTANCE.preVeilPostProcessing((name, pipeline, context) -> {
            if (name.equals(SIX_EYES_PIPELINE)) {
                MinecraftClient client = MinecraftClient.getInstance();
                if (client.player instanceof SixEyesItem.SixEyesState state && state.isSixEyesActive()) {
                    ShaderProgram shader = context.getShader(SIX_EYES_SHADER);
                    if (shader != null) {
                        float time = client.player.age + client.getRenderTickCounter().getTickDelta(true);
                        float intensity = Math.min(1.0f, state.getSixEyesTimer() / 300.0f); // 0.0 to 1.0 over 15s
                        
                        // Make intensity spike at the end
                        if (state.getSixEyesTimer() > 200) {
                            intensity += (float) (Math.sin(time * 2.0) * 0.1);
                        }

                        shader.setFloat("Time", time * 0.05f);
                        shader.setFloat("Intensity", intensity);
                    }
                }
            }
        });

        JujutsuShenanigans.LOGGER.info("Jujutsu Shenanigans Client initialized successfully.");
    }
}
