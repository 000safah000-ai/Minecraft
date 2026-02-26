/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.caffeinemc.mods.sodium.client.render.immediate.CloudRenderer
 *  net.minecraft.class_4184
 *  net.minecraft.class_4587
 *  net.minecraft.class_638
 *  org.joml.Matrix4f
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 */
package com.sp.mixin.compat.sodium.clouds;

import net.caffeinemc.mods.sodium.client.render.immediate.CloudRenderer;
import net.minecraft.class_4184;
import net.minecraft.class_4587;
import net.minecraft.class_638;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={CloudRenderer.class}, remap=false)
public class CloudRendererMixin {
    @Inject(method={"render"}, at={@At(value="HEAD")}, cancellable=true)
    private void disableSodiumClouds(class_4184 camera, class_638 level, Matrix4f projectionMatrix, class_4587 poseStack, float ticks, float tickDelta, CallbackInfo ci) {
        ci.cancel();
    }
}

