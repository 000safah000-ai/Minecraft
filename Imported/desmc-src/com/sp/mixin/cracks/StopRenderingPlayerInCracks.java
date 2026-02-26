/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.llamalad7.mixinextras.injector.v2.WrapWithCondition
 *  net.minecraft.class_1007
 *  net.minecraft.class_1309
 *  net.minecraft.class_1657
 *  net.minecraft.class_4587
 *  net.minecraft.class_4597
 *  net.minecraft.class_922
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 */
package com.sp.mixin.cracks;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.sp.cca.InitializeComponents;
import com.sp.cca.custom.entity.PlayerComponent;
import net.minecraft.class_1007;
import net.minecraft.class_1309;
import net.minecraft.class_1657;
import net.minecraft.class_4587;
import net.minecraft.class_4597;
import net.minecraft.class_922;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value={class_1007.class})
public class StopRenderingPlayerInCracks {
    @WrapWithCondition(method={"render(Lnet/minecraft/client/network/AbstractClientPlayerEntity;FFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V"}, at={@At(value="INVOKE", target="Lnet/minecraft/client/render/entity/LivingEntityRenderer;render(Lnet/minecraft/entity/LivingEntity;FFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V")})
    private <T extends class_1309> boolean stopRendering(class_922 instance, T livingEntity, float f, float g, class_4587 matrixStack, class_4597 vertexConsumerProvider, int i) {
        if (livingEntity instanceof class_1657) {
            class_1657 player = (class_1657)livingEntity;
            PlayerComponent component = (PlayerComponent)InitializeComponents.PLAYERS.get((Object)player);
            return !component.isInHole();
        }
        return true;
    }
}

