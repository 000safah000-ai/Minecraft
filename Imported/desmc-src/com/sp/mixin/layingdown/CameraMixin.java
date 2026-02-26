/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.llamalad7.mixinextras.injector.wrapoperation.Operation
 *  com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation
 *  com.llamalad7.mixinextras.sugar.Local
 *  net.minecraft.class_1297
 *  net.minecraft.class_2350
 *  net.minecraft.class_310
 *  net.minecraft.class_4184
 *  net.minecraft.class_5498
 *  org.joml.Quaternionf
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.Shadow
 *  org.spongepowered.asm.mixin.injection.At
 */
package com.sp.mixin.layingdown;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.sp.mixininterfaces.LayingDownPlayerEntity;
import net.minecraft.class_1297;
import net.minecraft.class_2350;
import net.minecraft.class_310;
import net.minecraft.class_4184;
import net.minecraft.class_5498;
import org.joml.Quaternionf;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value={class_4184.class})
public class CameraMixin {
    @Shadow
    private class_1297 field_18711;

    @WrapOperation(method={"update"}, at={@At(value="INVOKE", target="Lnet/minecraft/client/render/Camera;setPos(DDD)V")})
    private void setCameraPosition(class_4184 instance, double x, double y, double z, Operation<Void> original) {
        LayingDownPlayerEntity playerEntity;
        class_1297 class_12972 = this.field_18711;
        if (class_12972 instanceof LayingDownPlayerEntity && (playerEntity = (LayingDownPlayerEntity)class_12972).isLayingDown() && class_310.method_1551().field_1690.method_31044() == class_5498.field_26664) {
            original.call(new Object[]{instance, x, y + 0.2, z});
        } else {
            original.call(new Object[]{instance, x, y, z});
        }
    }

    @WrapOperation(method={"setRotation"}, at={@At(value="INVOKE", target="Lorg/joml/Quaternionf;rotationYXZ(FFF)Lorg/joml/Quaternionf;")})
    private Quaternionf switchHorizontal(Quaternionf instance, float angleY, float angleX, float angleZ, Operation<Quaternionf> original, @Local(argsOnly=true, ordinal=0) float yaw, @Local(argsOnly=true, ordinal=1) float pitch) {
        LayingDownPlayerEntity layingDownPlayerEntity;
        class_1297 class_12972 = this.field_18711;
        if (class_12972 instanceof LayingDownPlayerEntity && (layingDownPlayerEntity = (LayingDownPlayerEntity)class_12972).isLayingDown()) {
            float localYRot = switch (layingDownPlayerEntity.getLayingDownDirection()) {
                case class_2350.field_11034 -> 90.0f;
                case class_2350.field_11035 -> 0.0f;
                case class_2350.field_11039 -> -90.0f;
                default -> 180.0f;
            };
            return ((Quaternionf)original.call(new Object[]{instance, Float.valueOf(angleY), Float.valueOf(angleX), Float.valueOf(angleZ)})).rotateLocalX((float)Math.toRadians(90.0)).rotateLocalY((float)Math.toRadians(localYRot));
        }
        return (Quaternionf)original.call(new Object[]{instance, Float.valueOf(angleY), Float.valueOf(angleX), Float.valueOf(angleZ)});
    }
}

