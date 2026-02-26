/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.llamalad7.mixinextras.injector.wrapoperation.Operation
 *  com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation
 *  com.mojang.blaze3d.systems.RenderSystem
 *  foundry.veil.api.client.render.VeilLevelPerspectiveRenderer
 *  net.minecraft.class_1921
 *  net.minecraft.class_243
 *  net.minecraft.class_289
 *  net.minecraft.class_291
 *  net.minecraft.class_291$class_8555
 *  net.minecraft.class_310
 *  net.minecraft.class_3532
 *  net.minecraft.class_4063
 *  net.minecraft.class_4587
 *  net.minecraft.class_5944
 *  net.minecraft.class_638
 *  net.minecraft.class_758
 *  net.minecraft.class_761
 *  net.minecraft.class_9801
 *  org.jetbrains.annotations.Nullable
 *  org.joml.Matrix4f
 *  org.spongepowered.asm.mixin.Final
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.Shadow
 *  org.spongepowered.asm.mixin.Unique
 *  org.spongepowered.asm.mixin.injection.At
 */
package com.sp.mixin.compat.sodium.clouds;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.systems.RenderSystem;
import foundry.veil.api.client.render.VeilLevelPerspectiveRenderer;
import net.minecraft.class_1921;
import net.minecraft.class_243;
import net.minecraft.class_289;
import net.minecraft.class_291;
import net.minecraft.class_310;
import net.minecraft.class_3532;
import net.minecraft.class_4063;
import net.minecraft.class_4587;
import net.minecraft.class_5944;
import net.minecraft.class_638;
import net.minecraft.class_758;
import net.minecraft.class_761;
import net.minecraft.class_9801;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value={class_761.class})
public abstract class WorldRendererMixin {
    @Shadow
    @Nullable
    private class_638 field_4085;
    @Shadow
    private int field_4073;
    @Shadow
    private int field_4082;
    @Shadow
    private int field_4097;
    @Shadow
    private int field_4116;
    @Shadow
    @Final
    private class_310 field_4088;
    @Shadow
    @Nullable
    private class_4063 field_4080;
    @Shadow
    private class_243 field_4072;
    @Shadow
    private boolean field_4107;
    @Shadow
    @Nullable
    private class_291 field_4094;

    @Shadow
    protected abstract class_9801 method_3239(class_289 var1, double var2, double var4, double var6, class_243 var8);

    @WrapOperation(method={"render"}, at={@At(value="INVOKE", target="Lnet/minecraft/client/render/WorldRenderer;renderClouds(Lnet/minecraft/client/util/math/MatrixStack;Lorg/joml/Matrix4f;Lorg/joml/Matrix4f;FDDD)V")})
    private void renderOldClouds(class_761 instance, class_4587 matrices, Matrix4f matrix4f, Matrix4f matrix4f2, float tickDelta, double cameraX, double cameraY, double cameraZ, Operation<Void> original) {
        if (this.field_4080 == class_4063.field_18164 || this.field_4088.field_1690.method_1632() == class_4063.field_18164) {
            this.field_4088.field_1690.method_42528().method_41748((Object)class_4063.field_18163);
            this.field_4080 = class_4063.field_18163;
        }
        if (!VeilLevelPerspectiveRenderer.isRenderingPerspective()) {
            this.renderCloudsTheSequel(matrices, matrix4f, matrix4f2, tickDelta, cameraX, cameraY, cameraZ);
        }
    }

    @Unique
    private void renderCloudsTheSequel(class_4587 matrices, Matrix4f matrix4f, Matrix4f matrix4f2, float tickDelta, double cameraX, double cameraY, double cameraZ) {
        float f = this.field_4085.method_28103().method_28108();
        if (!Float.isNaN(f)) {
            double e = ((float)this.field_4073 + tickDelta) * 0.03f;
            double i = (cameraX + e) / 12.0;
            double j = f - (float)cameraY + 0.33f;
            double k = cameraZ / 12.0 + (double)0.33f;
            i -= (double)(class_3532.method_15357((double)(i / 2048.0)) * 2048);
            k -= (double)(class_3532.method_15357((double)(k / 2048.0)) * 2048);
            float l = (float)(i - (double)class_3532.method_15357((double)i));
            float m = (float)(j / 4.0 - (double)class_3532.method_15357((double)(j / 4.0))) * 4.0f;
            float n = (float)(k - (double)class_3532.method_15357((double)k));
            class_243 vec3d = this.field_4085.method_23785(tickDelta);
            int o = (int)Math.floor(i);
            int p = (int)Math.floor(j / 4.0);
            int q = (int)Math.floor(k);
            if (o != this.field_4082 || p != this.field_4097 || q != this.field_4116 || this.field_4088.field_1690.method_1632() != this.field_4080 || this.field_4072.method_1025(vec3d) > 2.0E-4) {
                this.field_4082 = o;
                this.field_4097 = p;
                this.field_4116 = q;
                this.field_4072 = vec3d;
                this.field_4080 = this.field_4088.field_1690.method_1632();
                this.field_4107 = true;
            }
            if (this.field_4107) {
                this.field_4107 = false;
                if (this.field_4094 != null) {
                    this.field_4094.close();
                }
                this.field_4094 = new class_291(class_291.class_8555.field_44793);
                this.field_4094.method_1353();
                this.field_4094.method_1352(this.method_3239(class_289.method_1348(), i, j, k, vec3d));
                class_291.method_1354();
            }
            class_758.method_3212();
            matrices.method_22903();
            matrices.method_34425(matrix4f);
            matrices.method_22905(12.0f, 1.0f, 12.0f);
            matrices.method_46416(-l, m, -n);
            if (this.field_4094 != null) {
                this.field_4094.method_1353();
                class_1921 renderLayer = class_1921.method_56849();
                renderLayer.method_23516();
                class_5944 shaderProgram = RenderSystem.getShader();
                this.field_4094.method_34427(matrices.method_23760().method_23761(), matrix4f2, shaderProgram);
                renderLayer.method_23518();
                class_291.method_1354();
            }
            matrices.method_22909();
        }
    }
}

