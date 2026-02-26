/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1297
 *  net.minecraft.class_1313
 *  net.minecraft.class_1937
 *  net.minecraft.class_238
 *  net.minecraft.class_243
 *  net.minecraft.class_3965
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.Shadow
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 *  org.spongepowered.asm.mixin.injection.callback.LocalCapture
 */
package com.sp.mixin.collision;

import com.sp.entity.ModEntities;
import com.sp.entity.custom.BlockPhysicsEntity;
import java.util.List;
import net.minecraft.class_1297;
import net.minecraft.class_1313;
import net.minecraft.class_1937;
import net.minecraft.class_238;
import net.minecraft.class_243;
import net.minecraft.class_3965;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(value={class_1297.class})
public abstract class IsGroundedCheckMixin {
    @Shadow
    public abstract class_1937 method_37908();

    @Shadow
    public abstract class_238 method_5829();

    @Shadow
    public abstract void method_38785();

    @Inject(method={"move"}, at={@At(value="INVOKE", target="Lnet/minecraft/util/hit/BlockHitResult;getType()Lnet/minecraft/util/hit/HitResult$Type;")}, locals=LocalCapture.CAPTURE_FAILSOFT)
    public void move(class_1313 movementType, class_243 movement, CallbackInfo ci, class_243 vec3d, double d, class_3965 blockHitResult) {
        List blockPhysicsEntities = this.method_37908().method_18023(ModEntities.BLOCK_PHYSICS_ENTITY, this.method_5829().method_18804(vec3d), entity1 -> true);
        if (!blockPhysicsEntities.isEmpty()) {
            for (BlockPhysicsEntity blockPhysicsEntity : blockPhysicsEntities) {
                if (blockPhysicsEntity == null || !blockPhysicsEntity.collides(this.method_5829().method_18804(vec3d))) continue;
                this.method_38785();
            }
        }
    }
}

