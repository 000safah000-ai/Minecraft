/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod
 *  com.llamalad7.mixinextras.injector.wrapoperation.Operation
 *  net.minecraft.class_1297
 *  net.minecraft.class_4184
 *  net.minecraft.class_757
 *  org.joml.Matrix4f
 *  org.spongepowered.asm.mixin.Mixin
 */
package com.sp.mixin.layingdown.render;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.sp.mixininterfaces.LayingDownPlayerEntity;
import net.minecraft.class_1297;
import net.minecraft.class_4184;
import net.minecraft.class_757;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value={class_757.class})
public class NoHandMixin {
    @WrapMethod(method={"renderHand"})
    private void noHandWhenLayingDown(class_4184 camera, float tickDelta, Matrix4f matrix4f, Operation<Void> original) {
        LayingDownPlayerEntity layingDownPlayerEntity;
        class_1297 cameraEntity = camera.method_19331();
        if (cameraEntity instanceof LayingDownPlayerEntity && (layingDownPlayerEntity = (LayingDownPlayerEntity)cameraEntity).isLayingDown()) {
            return;
        }
        original.call(new Object[]{camera, Float.valueOf(tickDelta), matrix4f});
    }
}

