/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.serialization.MapCodec
 *  net.minecraft.class_1269
 *  net.minecraft.class_1657
 *  net.minecraft.class_1937
 *  net.minecraft.class_2237
 *  net.minecraft.class_2248
 *  net.minecraft.class_2338
 *  net.minecraft.class_2464
 *  net.minecraft.class_2586
 *  net.minecraft.class_2591
 *  net.minecraft.class_2680
 *  net.minecraft.class_3965
 *  net.minecraft.class_4970$class_2251
 *  net.minecraft.class_5558
 *  org.jetbrains.annotations.Nullable
 */
package com.sp.block.custom;

import com.mojang.serialization.MapCodec;
import com.sp.block.entity.ModBlockEntities;
import com.sp.block.entity.custom.PhysicsDoorBlockEntity;
import net.minecraft.class_1269;
import net.minecraft.class_1657;
import net.minecraft.class_1937;
import net.minecraft.class_2237;
import net.minecraft.class_2248;
import net.minecraft.class_2338;
import net.minecraft.class_2464;
import net.minecraft.class_2586;
import net.minecraft.class_2591;
import net.minecraft.class_2680;
import net.minecraft.class_3965;
import net.minecraft.class_4970;
import net.minecraft.class_5558;
import org.jetbrains.annotations.Nullable;

public class PhysicsDoorBlock
extends class_2237 {
    public static final MapCodec<PhysicsDoorBlock> CODEC = PhysicsDoorBlock.method_54094(PhysicsDoorBlock::new);

    public PhysicsDoorBlock(class_4970.class_2251 settings) {
        super(settings);
    }

    protected void method_9612(class_2680 state, class_1937 world, class_2338 pos, class_2248 sourceBlock, class_2338 sourcePos, boolean notify) {
        if (!world.field_9236) {
            class_2586 blockEntity = world.method_8321(pos);
            if (world.method_49803(pos) && blockEntity instanceof PhysicsDoorBlockEntity) {
                PhysicsDoorBlockEntity physicsDoorBlockEntity = (PhysicsDoorBlockEntity)blockEntity;
                if (physicsDoorBlockEntity.isDoorMoving()) {
                    return;
                }
                physicsDoorBlockEntity.moveDoor(world);
                physicsDoorBlockEntity.method_5431();
            }
        }
    }

    protected class_1269 method_55766(class_2680 state, class_1937 world, class_2338 pos, class_1657 player, class_3965 hit) {
        class_2586 blockEntity = world.method_8321(pos);
        if (blockEntity instanceof PhysicsDoorBlockEntity) {
            PhysicsDoorBlockEntity physicsDoorBlockEntity = (PhysicsDoorBlockEntity)blockEntity;
            return physicsDoorBlockEntity.openScreen(player) ? class_1269.method_29236((boolean)world.field_9236) : class_1269.field_5811;
        }
        return class_1269.field_5811;
    }

    @Nullable
    public class_2586 method_10123(class_2338 pos, class_2680 state) {
        return new PhysicsDoorBlockEntity(pos, state);
    }

    @Nullable
    public <T extends class_2586> class_5558<T> method_31645(class_1937 world, class_2680 state, class_2591<T> type) {
        return PhysicsDoorBlock.method_31618(type, ModBlockEntities.PHYSICS_DOOR_BE, (world1, pos, state1, blockEntity) -> blockEntity.tick(world1, pos, state1));
    }

    protected class_2464 method_9604(class_2680 state) {
        return class_2464.field_11458;
    }

    protected MapCodec<? extends class_2237> method_53969() {
        return CODEC;
    }
}

