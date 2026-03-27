package com.jujutsuminecraft.client.render;

public class LaserParticleSystem {
    private static int activeParticles = 0;
    private static final int MAX_PARTICLES = 500; // Optimized limit

    public static void spawnBeamParticles(double startX, double startY, double startZ, double endX, double endY, double endZ) {
        if (activeParticles >= MAX_PARTICLES) return;
        activeParticles += 2;
    }

    public static void spawnImpactParticles(double x, double y, double z) {
        if (activeParticles >= MAX_PARTICLES) return;
        activeParticles += 10;
    }

    public static void decayParticles() {
        activeParticles = Math.max(0, activeParticles - 5);
    }
}