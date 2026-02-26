/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  foundry.veil.api.client.render.VeilRenderSystem
 *  foundry.veil.api.quasar.particle.ParticleEmitter
 *  foundry.veil.api.quasar.particle.ParticleSystemManager
 *  net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking$Context
 *  net.minecraft.class_2394
 *  net.minecraft.class_2398
 *  net.minecraft.class_2960
 *  net.minecraft.class_310
 *  net.minecraft.class_5819
 *  org.joml.Vector3f
 */
package com.sp.networking.S2C;

import com.sp.DestroyingMinecraft;
import com.sp.networking.CustomPayloads;
import foundry.veil.api.client.render.VeilRenderSystem;
import foundry.veil.api.quasar.particle.ParticleEmitter;
import foundry.veil.api.quasar.particle.ParticleSystemManager;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.class_2394;
import net.minecraft.class_2398;
import net.minecraft.class_2960;
import net.minecraft.class_310;
import net.minecraft.class_5819;
import org.joml.Vector3f;

public class LavaSpewPacket {
    private static final class_5819 random = class_5819.method_43047();
    private static final class_2960 LAVA_EMITTER = DestroyingMinecraft.idOf("lava");

    public static void receive(CustomPayloads.LavaSpewPacketPayload payload, ClientPlayNetworking.Context context) {
        context.client().execute(() -> {
            class_310 client = context.client();
            if (client.field_1687 == null) {
                return;
            }
            int numOfParticles = random.method_39332(100, 150);
            Vector3f pos = payload.position();
            try {
                ParticleSystemManager manager = VeilRenderSystem.renderer().getParticleManager();
                ParticleEmitter emitter = manager.createEmitter(LAVA_EMITTER);
                if (emitter != null) {
                    emitter.setPosition((double)pos.x, (double)pos.y, (double)pos.z);
                    manager.addParticleSystem(emitter);
                }
            }
            catch (Exception manager) {
                // empty catch block
            }
            for (int i = 0; i < numOfParticles; ++i) {
                float spreadX = random.method_43057() * 1.0f * 0.2f;
                float spreadZ = random.method_43057() * 1.0f * 0.2f;
                client.field_1687.method_8406((class_2394)class_2398.field_11203, (double)pos.x, (double)pos.y, (double)pos.z, (double)spreadX, (double)random.method_43057() * 0.5, (double)spreadZ);
            }
        });
    }
}

