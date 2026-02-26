/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup
 *  net.minecraft.class_1761
 *  net.minecraft.class_1799
 *  net.minecraft.class_1935
 *  net.minecraft.class_2246
 *  net.minecraft.class_2378
 *  net.minecraft.class_2561
 *  net.minecraft.class_2960
 *  net.minecraft.class_7923
 */
package com.sp.item;

import com.sp.DestroyingMinecraft;
import com.sp.block.ModBlocks;
import com.sp.item.ModItems;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.class_1761;
import net.minecraft.class_1799;
import net.minecraft.class_1935;
import net.minecraft.class_2246;
import net.minecraft.class_2378;
import net.minecraft.class_2561;
import net.minecraft.class_2960;
import net.minecraft.class_7923;

public class ModItemGroups {
    public static final class_1761 BACKROOMS_GROUP = (class_1761)class_2378.method_10230((class_2378)class_7923.field_44687, (class_2960)DestroyingMinecraft.idOf("destroying-minecraft"), (Object)FabricItemGroup.builder().method_47321((class_2561)class_2561.method_43471((String)"itemgroup.destroying-minecraft")).method_47320(() -> new class_1799((class_1935)class_2246.field_10375)).method_47317((displayContext, entries) -> {
        entries.method_45421((class_1935)ModItems.CAMERA_SHAKE_STICK_ITEM);
        entries.method_45421((class_1935)ModItems.WALKIE_TALKIE_ITEM);
        entries.method_45421((class_1935)ModBlocks.PHYSICS_DOOR_BLOCK);
        entries.method_45421((class_1935)ModBlocks.CHAIR_BLOCK);
    }).method_47324());

    public static void registerItemGroups() {
        DestroyingMinecraft.LOGGER.info("Registering Item Groups");
    }
}

