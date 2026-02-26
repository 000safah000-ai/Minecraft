/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.Object2IntMap
 *  it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap
 *  net.minecraft.class_1921
 *  net.minecraft.class_2248
 *  net.minecraft.class_2680
 *  net.minecraft.class_4696
 *  net.minecraft.class_7923
 */
package com.sp.render.materialsampler;

import com.sp.DestroyingMinecraft;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.class_1921;
import net.minecraft.class_2248;
import net.minecraft.class_2680;
import net.minecraft.class_4696;
import net.minecraft.class_7923;

public class BlockIdMap {
    private static Object2IntMap<class_2248> BlockIDs = null;
    public static boolean init = false;
    private static int numOfBlocks;

    public static void init() {
        BlockIDs = new Object2IntOpenHashMap();
        BlockIDs.clear();
        init = true;
        for (class_2248 block : class_7923.field_41175) {
            class_1921 renderLayer = class_4696.method_23679((class_2680)block.method_9564());
            if (renderLayer == class_1921.method_23577()) {
                BlockIDs.put((Object)block, 0);
            } else if (renderLayer == class_1921.method_23581()) {
                BlockIDs.put((Object)block, 1);
            } else if (renderLayer == class_1921.method_23579()) {
                BlockIDs.put((Object)block, 2);
            } else if (renderLayer == class_1921.method_23583()) {
                BlockIDs.put((Object)block, 3);
            }
            ++numOfBlocks;
        }
        DestroyingMinecraft.LOGGER.info("Loaded {} Default Block IDs", (Object)numOfBlocks);
    }

    public static int getBlockID(class_2248 block) {
        if (BlockIDs == null || !init) {
            return -1;
        }
        return BlockIDs.getOrDefault((Object)block, -1);
    }
}

