/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  foundry.veil.api.client.render.VeilLevelPerspectiveRenderer
 *  foundry.veil.api.client.render.VeilRenderSystem
 *  foundry.veil.api.client.render.shader.program.ShaderProgram
 *  net.minecraft.class_1041
 *  net.minecraft.class_284
 *  net.minecraft.class_293
 *  net.minecraft.class_293$class_5596
 *  net.minecraft.class_5912
 *  net.minecraft.class_5944
 *  org.jetbrains.annotations.Nullable
 *  org.joml.Matrix4f
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.Shadow
 *  org.spongepowered.asm.mixin.Unique
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 */
package com.sp.mixin;

import com.sp.util.BetterUniforms;
import foundry.veil.api.client.render.VeilLevelPerspectiveRenderer;
import foundry.veil.api.client.render.VeilRenderSystem;
import foundry.veil.api.client.render.shader.program.ShaderProgram;
import net.minecraft.class_1041;
import net.minecraft.class_284;
import net.minecraft.class_293;
import net.minecraft.class_5912;
import net.minecraft.class_5944;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={class_5944.class})
public abstract class SetShadowUniformMixin {
    @Unique
    public class_284 renderingShadow;
    @Unique
    private int attempt;

    @Shadow
    @Nullable
    public abstract class_284 method_34582(String var1);

    @Inject(method={"<init>"}, at={@At(value="TAIL")})
    private void setShadowUniform(class_5912 factory, String name, class_293 format, CallbackInfo ci) {
        this.renderingShadow = this.method_34582("renderingShadow");
    }

    @Inject(method={"initializeUniforms"}, at={@At(value="HEAD")})
    private void setRenderingShadow(class_293.class_5596 drawMode, Matrix4f viewMatrix, Matrix4f projectionMatrix, class_1041 window, CallbackInfo ci) {
        int rendering;
        ShaderProgram shader = VeilRenderSystem.getShader();
        int n = rendering = VeilLevelPerspectiveRenderer.isRenderingPerspective() ? 1 : 0;
        if (shader != null) {
            BetterUniforms.setInt(shader, "renderingShadow", rendering);
        }
        if (this.renderingShadow != null) {
            this.renderingShadow.method_35649(rendering);
        } else if (this.attempt < 3) {
            ++this.attempt;
            this.renderingShadow = this.method_34582("renderingShadow");
        }
    }
}

