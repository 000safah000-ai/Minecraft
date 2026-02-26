/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod
 *  com.llamalad7.mixinextras.injector.wrapoperation.Operation
 *  net.minecraft.class_1297
 *  net.minecraft.class_1937
 *  net.minecraft.class_238
 *  net.minecraft.class_243
 *  org.spongepowered.asm.mixin.Mixin
 */
package com.sp.mixin.collision;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.sp.entity.ModEntities;
import com.sp.entity.custom.BlockPhysicsEntity;
import java.util.List;
import net.minecraft.class_1297;
import net.minecraft.class_1937;
import net.minecraft.class_238;
import net.minecraft.class_243;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value={class_1297.class})
public abstract class PlatformCollisionMixin {
    @WrapMethod(method={"adjustMovementForCollisions(Lnet/minecraft/util/math/Vec3d;)Lnet/minecraft/util/math/Vec3d;"})
    private class_243 adjustMovementForCollisions(class_243 movement, Operation<class_243> original) {
        List blockPhysicsEntities;
        class_1297 entity = (class_1297)this;
        class_1937 world = entity.method_37908();
        class_238 entityBoundingBox = entity.method_5829();
        if (entity != null && !(blockPhysicsEntities = world.method_18023(ModEntities.BLOCK_PHYSICS_ENTITY, entityBoundingBox.method_18804(movement).method_1014(3.0), entity1 -> true)).isEmpty()) {
            class_243 adjustedMovement = movement;
            for (BlockPhysicsEntity blockPhysicsEntity : blockPhysicsEntities) {
                double yAxisCollision;
                if (blockPhysicsEntity == null) continue;
                if (movement.field_1351 != 0.0 && (yAxisCollision = blockPhysicsEntity.getYAxisCollision(entityBoundingBox.method_997(adjustedMovement))) < 0.1 && yAxisCollision > 1.0E-7) {
                    adjustedMovement = adjustedMovement.method_1031(0.0, yAxisCollision, 0.0);
                    adjustedMovement = adjustedMovement.method_1019(blockPhysicsEntity.method_18798());
                    continue;
                }
                class_243 newAdjustment = blockPhysicsEntity.getBestCollisionOffset(entityBoundingBox, adjustedMovement);
                if (Math.abs(movement.method_1033() - adjustedMovement.method_1033()) > 1.0E-7) {
                    newAdjustment = adjustedMovement.method_1019(blockPhysicsEntity.method_18798());
                }
                adjustedMovement = newAdjustment;
            }
            if (adjustedMovement.field_1351 > movement.field_1351) {
                entity.method_24830(true);
            }
            return (class_243)original.call(new Object[]{adjustedMovement});
        }
        return (class_243)original.call(new Object[]{movement});
    }
}

