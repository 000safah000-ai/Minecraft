package com.jujutsuminecraft.client.render;

import net.minecraft.util.math.BlockPos;
import java.util.concurrent.ConcurrentHashMap;

// Represents task 9 (Block Heating Physics) & 10 (Optimization using HashMap and logic ticks)
public class LaserBlockHeatManager {
    // Maps a BlockPos to its heat timer (max 40 ticks = 2 seconds at 20 tps)
    public static final ConcurrentHashMap<BlockPos, Integer> HEATED_BLOCKS = new ConcurrentHashMap<>();

    public static void heatBlock(BlockPos pos) {
        // Automatically reset or jump back to 100% heat
        HEATED_BLOCKS.put(pos, 40);
    }

    public static void tick() {
        // Decrease heat and remove if 0 to maintain fast execution and <5% frame time
        HEATED_BLOCKS.entrySet().removeIf(entry -> {
            int newHeat = entry.getValue() - 1;
            if (newHeat <= 0) return true;
            entry.setValue(newHeat);
            return false;
        });
    }

    public static float getHeatIntensity(BlockPos pos) {
        return HEATED_BLOCKS.getOrDefault(pos, 0) / 40.0f;
    }
}