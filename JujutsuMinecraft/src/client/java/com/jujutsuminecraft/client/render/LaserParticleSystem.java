package com.jujutsuminecraft.client.render;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.particle.Particle;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.particle.DustColorTransitionParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.Vec3d;
import org.joml.Vector3f;

public class LaserParticleSystem {
    private static int activeParticles = 0;
    private static final int MAX_PARTICLES = 500; // Optimized limit

    public static void spawnBeamParticles(double startX, double startY, double startZ, double endX, double endY, double endZ) {
        if (activeParticles >= MAX_PARTICLES) return;
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.world != null) {
            // Spawn helix particles with purple/blue random colors instead of default sparks
            boolean isPurple = Math.random() > 0.5;
            Vector3f startColor = isPurple ? new Vector3f(0.6f, 0.1f, 0.9f) : new Vector3f(0.1f, 0.5f, 1.0f);
            Vector3f endColor = new Vector3f(0.8f, 0.8f, 1.0f);
            DustColorTransitionParticleEffect effect = new DustColorTransitionParticleEffect(startColor, endColor, 1.5f);
            
            client.world.addParticle(effect, startX, startY, startZ, (Math.random() - 0.5) * 0.1, 0.1, (Math.random() - 0.5) * 0.1);
            activeParticles += 2;
        }
    }

    public static void spawnImpactParticles(double x, double y, double z) {
        if (activeParticles >= MAX_PARTICLES) return;
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.world != null) {
            // Impact explosion
            client.world.addParticle(ParticleTypes.FLAME, x, y, z, 0.1, 0.1, 0.1);
            client.world.addParticle(ParticleTypes.LAVA, x, y, z, 0, 0.2, 0);
            
            // Orbiting heat particles
            Vector3f heatColor = new Vector3f(1.0f, 0.3f, 0.0f);
            Vector3f fadeColor = new Vector3f(0.2f, 0.0f, 0.0f);
            DustColorTransitionParticleEffect heatEffect = new DustColorTransitionParticleEffect(heatColor, fadeColor, 2.0f);
            client.world.addParticle(heatEffect, x, y + 0.5, z, 0.05, 0, 0.05);
            
            activeParticles += 10;
        }
    }

    public static void decayParticles() {
        activeParticles = Math.max(0, activeParticles - 5);
    }
}