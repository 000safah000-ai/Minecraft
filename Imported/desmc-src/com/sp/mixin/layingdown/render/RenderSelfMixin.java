/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.llamalad7.mixinextras.injector.wrapoperation.Operation
 *  com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation
 *  net.minecraft.class_1309
 *  net.minecraft.class_761
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 */
package com.sp.mixin.layingdown.render;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.sp.mixininterfaces.LayingDownPlayerEntity;
import net.minecraft.class_1309;
import net.minecraft.class_761;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value={class_761.class})
public class RenderSelfMixin {
    @WrapOperation(method={"render"}, at={@At(value="INVOKE", target="Lnet/minecraft/entity/LivingEntity;isSleeping()Z")})
    private boolean isLayingDown(class_1309 instance, Operation<Boolean> original) {
        boolean bl = (Boolean)original.call(new Object[]{instance});
        if (instance instanceof LayingDownPlayerEntity) {
            LayingDownPlayerEntity layingDownPlayerEntity = (LayingDownPlayerEntity)instance;
            return layingDownPlayerEntity.isLayingDown() || bl;
        }
        return bl;
    }
}

