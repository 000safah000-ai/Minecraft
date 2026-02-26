/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1297
 *  net.minecraft.class_4538
 *  net.minecraft.class_4587
 *  net.minecraft.class_4597
 *  net.minecraft.class_898
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Redirect
 */
package com.sp.mixin;

import net.minecraft.class_1297;
import net.minecraft.class_4538;
import net.minecraft.class_4587;
import net.minecraft.class_4597;
import net.minecraft.class_898;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value={class_898.class})
public class DisableEntityShadowsMixin {
    @Redirect(method={"render"}, at=@At(value="INVOKE", target="Lnet/minecraft/client/render/entity/EntityRenderDispatcher;renderShadow(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;Lnet/minecraft/entity/Entity;FFLnet/minecraft/world/WorldView;F)V"))
    private void diableEntityShadows(class_4587 matrices, class_4597 vertexConsumers, class_1297 entity, float opacity, float tickDelta, class_4538 world, float radius) {
    }
}

