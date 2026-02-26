/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  foundry.veil.api.client.render.VeilLevelPerspectiveRenderer
 *  net.caffeinemc.mods.sodium.client.gl.shader.uniform.GlUniformInt
 *  net.caffeinemc.mods.sodium.client.render.chunk.shader.ChunkShaderOptions
 *  net.caffeinemc.mods.sodium.client.render.chunk.shader.DefaultShaderInterface
 *  net.caffeinemc.mods.sodium.client.render.chunk.shader.ShaderBindingContext
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.Unique
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 */
package com.sp.mixin.compat.sodium;

import foundry.veil.api.client.render.VeilLevelPerspectiveRenderer;
import net.caffeinemc.mods.sodium.client.gl.shader.uniform.GlUniformInt;
import net.caffeinemc.mods.sodium.client.render.chunk.shader.ChunkShaderOptions;
import net.caffeinemc.mods.sodium.client.render.chunk.shader.DefaultShaderInterface;
import net.caffeinemc.mods.sodium.client.render.chunk.shader.ShaderBindingContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={DefaultShaderInterface.class}, remap=false)
public class RenderingShadowUniformMixin {
    @Unique
    private GlUniformInt renderingShadow;

    @Inject(method={"<init>"}, at={@At(value="TAIL")})
    private void initRenderShadowUniform(ShaderBindingContext context, ChunkShaderOptions options, CallbackInfo ci) {
        this.renderingShadow = (GlUniformInt)context.bindUniform("renderingShadow", GlUniformInt::new);
    }

    @Inject(method={"setupState"}, at={@At(value="TAIL")})
    private void setRenderingShadow(CallbackInfo ci) {
        this.renderingShadow.set(Integer.valueOf(VeilLevelPerspectiveRenderer.isRenderingPerspective() ? 1 : 0));
    }
}

