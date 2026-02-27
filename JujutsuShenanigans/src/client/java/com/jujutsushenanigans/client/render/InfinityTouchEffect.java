package com.jujutsushenanigans.client.render;

import com.jujutsushenanigans.JujutsuShenanigans;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.Vec3d;

import java.util.Random;

@Environment(EnvType.CLIENT)
public class InfinityTouchEffect {

    private static final Random random = new Random();

    public static void spawnTouchFlare(Vec3d position) {
        try {
            MinecraftClient client = MinecraftClient.getInstance();
            if (client.world != null) {
                // Spawn blue-ish particles at the touch point
                for (int i = 0; i < 10; i++) {
                    double offsetX = (random.nextDouble() - 0.5) * 0.5;
                    double offsetY = (random.nextDouble() - 0.5) * 0.5;
                    double offsetZ = (random.nextDouble() - 0.5) * 0.5;
                    client.world.addParticle(
                            ParticleTypes.SOUL_FIRE_FLAME,
                            position.x + offsetX,
                            position.y + offsetY,
                            position.z + offsetZ,
                            0.0, 0.05, 0.0
                    );
                }
            }
        } catch (Exception e) {
            JujutsuShenanigans.LOGGER.error("Failed to spawn Infinity touch effect", e);
        }
    }
}
