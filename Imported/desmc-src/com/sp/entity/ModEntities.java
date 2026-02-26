/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1299
 *  net.minecraft.class_1299$class_1300
 *  net.minecraft.class_1311
 *  net.minecraft.class_2378
 *  net.minecraft.class_2960
 *  net.minecraft.class_7923
 */
package com.sp.entity;

import com.sp.DestroyingMinecraft;
import com.sp.entity.custom.BlockPhysicsEntity;
import com.sp.entity.custom.MeteorEntity;
import com.sp.entity.custom.SpinningBlockEntity;
import com.sp.entity.custom.StarPiercerEntity;
import net.minecraft.class_1299;
import net.minecraft.class_1311;
import net.minecraft.class_2378;
import net.minecraft.class_2960;
import net.minecraft.class_7923;

public class ModEntities {
    public static final class_1299<SpinningBlockEntity> SPINNING_BLOCK = (class_1299)class_2378.method_10230((class_2378)class_7923.field_41177, (class_2960)DestroyingMinecraft.idOf("spinning_block"), (Object)class_1299.class_1300.method_5903(SpinningBlockEntity::new, (class_1311)class_1311.field_17715).method_17687(1.0f, 1.0f).build());
    public static final class_1299<BlockPhysicsEntity> BLOCK_PHYSICS_ENTITY = (class_1299)class_2378.method_10230((class_2378)class_7923.field_41177, (class_2960)DestroyingMinecraft.idOf("blockphysicsentity"), (Object)class_1299.class_1300.method_5903(BlockPhysicsEntity::new, (class_1311)class_1311.field_17715).method_17687(1.0f, 1.0f).build());
    public static final class_1299<MeteorEntity> METEOR_ENTITY = (class_1299)class_2378.method_10230((class_2378)class_7923.field_41177, (class_2960)DestroyingMinecraft.idOf("meteor"), (Object)class_1299.class_1300.method_5903(MeteorEntity::new, (class_1311)class_1311.field_17715).method_17687(1.0f, 1.0f).build());
    public static final class_1299<StarPiercerEntity> STAR_PIERCER_ENTITY = (class_1299)class_2378.method_10230((class_2378)class_7923.field_41177, (class_2960)DestroyingMinecraft.idOf("starpiercer"), (Object)class_1299.class_1300.method_5903(StarPiercerEntity::new, (class_1311)class_1311.field_17715).method_17687(10.0f, 10.0f).build());

    public static void registerEntities() {
        DestroyingMinecraft.LOGGER.info("Registering entities for destroying-minecraft");
    }
}

