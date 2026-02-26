/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.llamalad7.mixinextras.injector.v2.WrapWithCondition
 *  net.minecraft.class_1297
 *  net.minecraft.class_2561
 *  net.minecraft.class_310
 *  net.minecraft.class_4587
 *  net.minecraft.class_4597
 *  net.minecraft.class_897
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 */
package com.sp.mixin.layingdown.render;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.sp.mixininterfaces.LayingDownPlayerEntity;
import net.minecraft.class_1297;
import net.minecraft.class_2561;
import net.minecraft.class_310;
import net.minecraft.class_4587;
import net.minecraft.class_4597;
import net.minecraft.class_897;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value={class_897.class})
public class NoNameTagMixin<T extends class_1297> {
    @WrapWithCondition(method={"render"}, at={@At(value="INVOKE", target="Lnet/minecraft/client/render/entity/EntityRenderer;renderLabelIfPresent(Lnet/minecraft/entity/Entity;Lnet/minecraft/text/Text;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;IF)V")})
    private boolean noTags(class_897 instance, T entity, class_2561 text, class_4587 matrices, class_4597 vertexConsumers, int light, float tickDelta) {
        LayingDownPlayerEntity layingDownPlayerEntity;
        if (entity instanceof LayingDownPlayerEntity && (layingDownPlayerEntity = (LayingDownPlayerEntity)entity).isLayingDown()) {
            return class_310.method_1551().method_1560() != entity;
        }
        return true;
    }
}

