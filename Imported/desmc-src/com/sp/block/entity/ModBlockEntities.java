/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_2248
 *  net.minecraft.class_2378
 *  net.minecraft.class_2591
 *  net.minecraft.class_2591$class_2592
 *  net.minecraft.class_2960
 *  net.minecraft.class_7923
 */
package com.sp.block.entity;

import com.sp.DestroyingMinecraft;
import com.sp.block.ModBlocks;
import com.sp.block.entity.custom.PhysicsDoorBlockEntity;
import net.minecraft.class_2248;
import net.minecraft.class_2378;
import net.minecraft.class_2591;
import net.minecraft.class_2960;
import net.minecraft.class_7923;

public class ModBlockEntities {
    public static final class_2591<PhysicsDoorBlockEntity> PHYSICS_DOOR_BE = (class_2591)class_2378.method_10230((class_2378)class_7923.field_41181, (class_2960)DestroyingMinecraft.idOf("physics_door_be"), (Object)class_2591.class_2592.method_20528(PhysicsDoorBlockEntity::new, (class_2248[])new class_2248[]{ModBlocks.PHYSICS_DOOR_BLOCK}).build());

    public static void registerBlockEntities() {
        DestroyingMinecraft.LOGGER.info("Registering Block Entities for destroying-minecraft");
    }
}

