/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1297
 *  net.minecraft.class_1309
 *  net.minecraft.class_310
 *  net.minecraft.class_4587
 *  net.minecraft.class_4597
 *  net.minecraft.class_5498
 *  net.minecraft.class_583
 *  net.minecraft.class_591
 *  net.minecraft.class_922
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.Shadow
 *  org.spongepowered.asm.mixin.Unique
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.ModifyArg
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 */
package com.sp.mixin.layingdown.render;

import com.sp.mixininterfaces.LayingDownPlayerEntity;
import net.minecraft.class_1297;
import net.minecraft.class_1309;
import net.minecraft.class_310;
import net.minecraft.class_4587;
import net.minecraft.class_4597;
import net.minecraft.class_5498;
import net.minecraft.class_583;
import net.minecraft.class_591;
import net.minecraft.class_922;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={class_922.class})
public abstract class RenderLyingDownPlayerMixin<T extends class_1309, M extends class_583<T>> {
    @Shadow
    protected M field_4737;
    @Unique
    private static final String RENDER = "render(Lnet/minecraft/entity/LivingEntity;FFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V";
    @Unique
    private static final String SET_ANGLES = "Lnet/minecraft/client/render/entity/model/EntityModel;setAngles(Lnet/minecraft/entity/Entity;FFFFF)V";
    @Unique
    private T entity;

    @Inject(method={"render(Lnet/minecraft/entity/LivingEntity;FFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V"}, at={@At(value="HEAD")})
    private void setEntity(T livingEntity, float f, float g, class_4587 matrixStack, class_4597 vertexConsumerProvider, int i, CallbackInfo ci) {
        this.entity = livingEntity;
    }

    @ModifyArg(method={"render(Lnet/minecraft/entity/LivingEntity;FFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V"}, at=@At(value="INVOKE", target="Lnet/minecraft/client/render/entity/model/EntityModel;setAngles(Lnet/minecraft/entity/Entity;FFFFF)V"), index=4)
    private float modifyYawRotation(float value) {
        LayingDownPlayerEntity layingDownPlayerEntity;
        T t = this.entity;
        if (t instanceof LayingDownPlayerEntity && (layingDownPlayerEntity = (LayingDownPlayerEntity)t).isLayingDown()) {
            float delta = class_310.method_1551().method_60646().method_60637(true);
            float yawRot = this.entity.method_5705(delta) - 90.0f;
            return yawRot - 90.0f;
        }
        return value;
    }

    @Inject(method={"render(Lnet/minecraft/entity/LivingEntity;FFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V"}, at={@At(value="INVOKE", target="Lnet/minecraft/client/render/entity/model/EntityModel;render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumer;III)V")})
    private void makeBodyVisible(T livingEntity, float f, float g, class_4587 matrixStack, class_4597 vertexConsumerProvider, int i, CallbackInfo ci) {
        LayingDownPlayerEntity layingDownPlayerEntity;
        class_591 playerEntityModel;
        block5: {
            block4: {
                class_1297 cameraEntity = class_310.method_1551().field_1719;
                M m = this.field_4737;
                if (!(m instanceof class_591)) break block4;
                playerEntityModel = (class_591)m;
                if (cameraEntity.equals(livingEntity) && class_310.method_1551().field_1690.method_31044() == class_5498.field_26664) break block5;
            }
            return;
        }
        T t = this.entity;
        if (t instanceof LayingDownPlayerEntity && (layingDownPlayerEntity = (LayingDownPlayerEntity)t).isLayingDown()) {
            playerEntityModel.method_2805(true);
            playerEntityModel.field_3398.field_3665 = false;
            playerEntityModel.field_3394.field_3665 = false;
        }
    }
}

