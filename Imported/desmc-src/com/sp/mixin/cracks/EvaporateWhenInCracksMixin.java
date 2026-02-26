/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.llamalad7.mixinextras.injector.wrapoperation.Operation
 *  com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation
 *  net.minecraft.class_1309
 *  net.minecraft.class_1657
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 */
package com.sp.mixin.cracks;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.sp.cca.InitializeComponents;
import com.sp.cca.custom.entity.PlayerComponent;
import net.minecraft.class_1309;
import net.minecraft.class_1657;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value={class_1309.class})
public class EvaporateWhenInCracksMixin {
    @WrapOperation(method={"baseTick"}, at={@At(value="INVOKE", target="Lnet/minecraft/entity/LivingEntity;updatePostDeath()V")})
    private void disablePostDeath(class_1309 instance, Operation<Void> original) {
        class_1657 player;
        PlayerComponent component;
        if (instance instanceof class_1657 && (component = (PlayerComponent)InitializeComponents.PLAYERS.get((Object)(player = (class_1657)instance))).isInHole()) {
            return;
        }
        original.call(new Object[]{instance});
    }
}

