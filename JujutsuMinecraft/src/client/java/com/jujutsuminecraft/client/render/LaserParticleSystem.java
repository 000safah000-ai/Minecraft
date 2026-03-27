package com.jujutsuminecraft.client.render;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.particle.Particle;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.particle.ParticleTypes;

public class LaserParticleSystem {
    private static int activeParticles = 0;
    private static final int MAX_PARTICLES = 500; // Optimized limit

    public static void spawnBeamParticles(double startX, double startY, double startZ, double endX, double endY, double endZ) {
        if (activeParticles >= MAX_PARTICLES) return;
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.world != null) {
            // Spawn helix particles
            client.world.addParticle(ParticleTypes.ELECTRIC_SPARK, startX, startY, startZ, 0, 0, 0);
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
            client.world.addParticle(ParticleTypes.SMOKE, x, y + 0.5, z, 0.05, 0, 0.05);
            activeParticles += 10;
        }
    }

    public static void decayParticles() {
        activeParticles = Math.max(0, activeParticles - 5);
    }
}