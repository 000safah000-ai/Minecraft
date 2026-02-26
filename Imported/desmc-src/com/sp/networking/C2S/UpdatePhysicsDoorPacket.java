/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking$Context
 *  net.minecraft.class_2586
 *  net.minecraft.class_2680
 */
package com.sp.networking.C2S;

import com.sp.block.entity.custom.PhysicsDoorBlockEntity;
import com.sp.networking.CustomPayloads;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.class_2586;
import net.minecraft.class_2680;

public class UpdatePhysicsDoorPacket {
    public static void recieve(CustomPayloads.UpdatePhysicsDoorBlock payload, ServerPlayNetworking.Context context) {
        class_2586 blockEntity = context.player().method_37908().method_8321(payload.blockEntityPos());
        class_2680 blockState = context.player().method_37908().method_8320(payload.blockEntityPos());
        if (blockEntity instanceof PhysicsDoorBlockEntity) {
            PhysicsDoorBlockEntity physicsDoorBlockEntity = (PhysicsDoorBlockEntity)blockEntity;
            physicsDoorBlockEntity.setCorner1(payload.corner1());
            physicsDoorBlockEntity.setCorner2(payload.corner2());
            physicsDoorBlockEntity.setMovementDirection(payload.direction());
            physicsDoorBlockEntity.setNumOfBlocks(payload.numOfBlocks());
            physicsDoorBlockEntity.setSpeed(payload.speed());
            physicsDoorBlockEntity.setShowSelection(payload.showSelection());
            physicsDoorBlockEntity.setPlaySound(payload.playSound());
            physicsDoorBlockEntity.method_5431();
            context.player().method_37908().method_8413(payload.blockEntityPos(), blockState, blockState, 3);
        }
    }
}

