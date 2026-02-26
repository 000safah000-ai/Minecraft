/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_287
 *  net.minecraft.class_296
 *  org.lwjgl.system.MemoryUtil
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.Shadow
 */
package com.sp.mixin;

import com.sp.mixininterfaces.BufferBuilderPosition;
import com.sp.render.CustomRenderLayersAndVertexFormats;
import net.minecraft.class_287;
import net.minecraft.class_296;
import org.lwjgl.system.MemoryUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value={class_287.class})
public abstract class BufferBuilderPositionMixin
implements BufferBuilderPosition {
    @Shadow
    protected abstract long method_60798(class_296 var1);

    @Shadow
    private static byte method_60795(float f) {
        return 0;
    }

    @Override
    public void setPosition(float x, float y, float z, float w) {
        long l = this.method_60798(CustomRenderLayersAndVertexFormats.ENTITY_POSITION);
        if (l != -1L) {
            MemoryUtil.memPutFloat((long)l, (float)x);
            MemoryUtil.memPutFloat((long)(l + 4L), (float)y);
            MemoryUtil.memPutFloat((long)(l + 8L), (float)z);
            MemoryUtil.memPutFloat((long)(l + 12L), (float)w);
        }
    }
}

