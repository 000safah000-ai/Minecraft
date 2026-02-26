/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1747
 *  net.minecraft.class_1792
 *  net.minecraft.class_1792$class_1793
 *  net.minecraft.class_2246
 *  net.minecraft.class_2248
 *  net.minecraft.class_2378
 *  net.minecraft.class_2960
 *  net.minecraft.class_4970
 *  net.minecraft.class_4970$class_2251
 *  net.minecraft.class_7923
 */
package com.sp.block;

import com.sp.DestroyingMinecraft;
import com.sp.block.custom.ChairBlock;
import com.sp.block.custom.PhysicsDoorBlock;
import net.minecraft.class_1747;
import net.minecraft.class_1792;
import net.minecraft.class_2246;
import net.minecraft.class_2248;
import net.minecraft.class_2378;
import net.minecraft.class_2960;
import net.minecraft.class_4970;
import net.minecraft.class_7923;

public class ModBlocks {
    public static final class_2248 PHYSICS_DOOR_BLOCK = ModBlocks.registerBlock("physics_door_block", (class_2248)new PhysicsDoorBlock(class_4970.class_2251.method_9630((class_4970)class_2246.field_10340).method_36557(-1.0f).method_51369().method_45477()));
    public static final class_2248 CHAIR_BLOCK = ModBlocks.registerBlock("chair_block", (class_2248)new ChairBlock(class_4970.class_2251.method_9630((class_4970)class_2246.field_10569).method_51369()));

    private static class_2248 registerBlock(String name, class_2248 block) {
        ModBlocks.registerBlockItem(name, block);
        return (class_2248)class_2378.method_10230((class_2378)class_7923.field_41175, (class_2960)DestroyingMinecraft.idOf(name), (Object)block);
    }

    private static class_1792 registerBlockItem(String name, class_2248 block) {
        return (class_1792)class_2378.method_10230((class_2378)class_7923.field_41178, (class_2960)DestroyingMinecraft.idOf(name), (Object)new class_1747(block, new class_1792.class_1793()));
    }

    public static void init() {
    }
}

