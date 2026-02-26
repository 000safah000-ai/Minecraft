/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  foundry.veil.api.client.render.CullFrustum
 *  foundry.veil.api.client.render.MatrixStack
 *  foundry.veil.api.client.render.VeilLevelPerspectiveRenderer
 *  foundry.veil.api.quasar.particle.ParticleSystemManager
 *  net.minecraft.class_4184
 *  net.minecraft.class_4597
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 */
package com.sp.mixin.quasar;

import foundry.veil.api.client.render.CullFrustum;
import foundry.veil.api.client.render.MatrixStack;
import foundry.veil.api.client.render.VeilLevelPerspectiveRenderer;
import foundry.veil.api.quasar.particle.ParticleSystemManager;
import net.minecraft.class_4184;
import net.minecraft.class_4597;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={ParticleSystemManager.class})
public class ParticleSystemManagerMixin {
    @Inject(method={"render"}, at={@At(value="HEAD")}, cancellable=true)
    private void noRenderOnShadowMap(MatrixStack matrixStack, class_4597 bufferSource, class_4184 camera, CullFrustum frustum, float partialTicks, CallbackInfo ci) {
        if (VeilLevelPerspectiveRenderer.isRenderingPerspective()) {
            ci.cancel();
        }
    }
}

