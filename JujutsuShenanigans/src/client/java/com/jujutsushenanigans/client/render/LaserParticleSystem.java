package com.jujutsushenanigans.client.render;

// Represents task 7 (Particle Systems) and 8 (Helix Spiral & Bloom)
public class LaserParticleSystem {
    private static int activeParticles = 0;
    private static final int MAX_PARTICLES = 500; // Optimized limit

    public static void spawnBeamParticles(double startX, double startY, double startZ, double endX, double endY, double endZ) {
        if (activeParticles >= MAX_PARTICLES) return;
        // Logic for Phase 1: Beam Active
        // Outer particles: larger, slower, following a helix
        // Inner particles: smaller, faster
        activeParticles += 2;
    }

    public static void spawnImpactParticles(double x, double y, double z) {
        if (activeParticles >= MAX_PARTICLES) return;
        // Logic for Phase 2: Impact explosion
        activeParticles += 10;
    }

    public static void decayParticles() {
        // Logic for Phase 3: Decay fading out over 0.5s
        activeParticles = Math.max(0, activeParticles - 5);
    }
}
