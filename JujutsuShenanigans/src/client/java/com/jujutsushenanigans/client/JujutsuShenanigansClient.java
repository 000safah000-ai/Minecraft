package com.jujutsushenanigans.client;

import com.jujutsushenanigans.JujutsuShenanigans;
import com.jujutsushenanigans.networking.S2C.SixEyesSyncS2CPacket;
import com.jujutsushenanigans.networking.S2C.InfinitySyncS2CPacket;
import com.jujutsushenanigans.networking.S2C.InfinityTouchS2CPacket;
import com.jujutsushenanigans.networking.C2S.ToggleInfinityC2SPacket;
import com.jujutsushenanigans.client.render.InfinityTouchEffect;
import com.jujutsushenanigans.InfinityState;
import foundry.veil.api.client.render.VeilRenderSystem;
import foundry.veil.api.client.render.post.PostProcessingManager;
import foundry.veil.api.client.render.shader.program.ShaderProgram;
import foundry.veil.platform.VeilEventPlatform;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.util.Identifier;
import org.lwjgl.glfw.GLFW;

@Environment(EnvType.CLIENT)
public class JujutsuShenanigansClient implements ClientModInitializer {

    public static final Identifier SIX_EYES_PIPELINE = JujutsuShenanigans.id("six_eyes_hallucination");
    private static KeyBinding toggleInfinityKeyBinding;

    @Override
    public void onInitializeClient() {
        JujutsuShenanigans.LOGGER.info("Initializing Jujutsu Shenanigans Client...");

        // ── KeyBindings ──
        toggleInfinityKeyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.jujutsushenanigans.toggle_infinity",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_R,
                "category.jujutsushenanigans.abilities"
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (toggleInfinityKeyBinding.wasPressed()) {
                ClientPlayNetworking.send(new ToggleInfinityC2SPacket());
            }
        });

        // ── Client Networking ──
        ClientPlayNetworking.registerGlobalReceiver(SixEyesSyncS2CPacket.ID, (payload, context) -> {
            // Currently unused — Six Eyes effect is tracked via StatusEffect directly
        });

        ClientPlayNetworking.registerGlobalReceiver(InfinitySyncS2CPacket.ID, (payload, context) -> {
            context.client().execute(() -> {
                if (context.client().world != null) {
                    net.minecraft.entity.Entity entity = context.client().world.getEntityById(payload.entityId());
                    if (entity instanceof InfinityState state) {
                        state.setInfinityActive(payload.isActive());
                    }
                }
            });
        });

        ClientPlayNetworking.registerGlobalReceiver(InfinityTouchS2CPacket.ID, (payload, context) -> {
            context.client().execute(() -> {
                InfinityTouchEffect.spawnTouchFlare(payload.position());
            });
        });

        // ── Veil Render Pipeline ──
        // Manage the Six Eyes post-processing pipeline
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player != null) {
                try {
                    PostProcessingManager postManager = VeilRenderSystem.renderer().getPostProcessingManager();
                    boolean hasEffect = client.player.hasStatusEffect(JujutsuShenanigans.SIX_EYES_EFFECT);
                    if (hasEffect) {
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

        VeilEventPlatform.INSTANCE.preVeilPostProcessing((name, pipeline, context) -> {
            if (name.equals(SIX_EYES_PIPELINE)) {
                MinecraftClient client = MinecraftClient.getInstance();
                if (client.player != null && client.player.hasStatusEffect(JujutsuShenanigans.SIX_EYES_EFFECT)) {
                    var effectInstance = client.player.getStatusEffect(JujutsuShenanigans.SIX_EYES_EFFECT);
                    if (effectInstance != null) {
                        ShaderProgram shader = context.getShader(JujutsuShenanigans.id("six_eyes_hallucination"));
                        if (shader != null) {
                            float timer = 300.0f - effectInstance.getDuration();
                            float intensity = Math.min(timer / 300.0f, 1.0f);
                            float time = (client.world.getTime() + client.getRenderTickCounter().getTickDelta(true)) * 0.05f;
                            
                            foundry.veil.api.client.render.shader.uniform.ShaderUniform timeUniform = shader.getUniform("Time");
                            if (timeUniform != null) {
                                timeUniform.setFloat(time);
                            }
                            
                            foundry.veil.api.client.render.shader.uniform.ShaderUniform intensityUniform = shader.getUniform("Intensity");
                            if (intensityUniform != null) {
                                intensityUniform.setFloat(intensity);
                            }
                        }
                    }
                }
            }
        });

        JujutsuShenanigans.LOGGER.info("Jujutsu Shenanigans Client initialized successfully.");
    }
}
