/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.llamalad7.mixinextras.injector.wrapoperation.Operation
 *  com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation
 *  com.llamalad7.mixinextras.sugar.Local
 *  foundry.veil.api.client.render.framebuffer.AdvancedFboTextureAttachment
 *  foundry.veil.impl.client.render.framebuffer.DSAAdvancedFboImpl
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 */
package com.sp.mixin.materialsampler.linux;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import foundry.veil.api.client.render.framebuffer.AdvancedFboTextureAttachment;
import foundry.veil.impl.client.render.framebuffer.DSAAdvancedFboImpl;
import java.nio.FloatBuffer;
import java.util.Objects;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value={DSAAdvancedFboImpl.class}, remap=false)
public class LinuxFixMixin {
    @WrapOperation(method={"clear"}, at={@At(value="INVOKE", target="Lorg/lwjgl/opengl/ARBClearTexture;glClearTexImage(IIIILjava/nio/FloatBuffer;)V", ordinal=0)})
    private void redirectMethod(int textureId, int level, int format, int type, FloatBuffer data, Operation<Void> original, @Local AdvancedFboTextureAttachment texture) {
        if (texture.getFormat() == 33330 || Objects.equals(texture.getName(), "VeilDynamicMaterial")) {
            original.call(new Object[]{textureId, level, 36244, 5124, data});
            return;
        }
        original.call(new Object[]{textureId, level, format, type, data});
    }
}

