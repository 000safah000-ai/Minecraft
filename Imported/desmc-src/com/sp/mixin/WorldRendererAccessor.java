/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1297
 *  net.minecraft.class_1921
 *  net.minecraft.class_4184
 *  net.minecraft.class_4587
 *  net.minecraft.class_4597
 *  net.minecraft.class_4599
 *  net.minecraft.class_4604
 *  net.minecraft.class_761
 *  net.minecraft.class_898
 *  org.joml.Matrix4f
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.gen.Accessor
 *  org.spongepowered.asm.mixin.gen.Invoker
 */
package com.sp.mixin;

import net.minecraft.class_1297;
import net.minecraft.class_1921;
import net.minecraft.class_4184;
import net.minecraft.class_4587;
import net.minecraft.class_4597;
import net.minecraft.class_4599;
import net.minecraft.class_4604;
import net.minecraft.class_761;
import net.minecraft.class_898;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(value={class_761.class})
public interface WorldRendererAccessor {
    @Invoker(value="renderLayer")
    public void invokeRenderLayer(class_1921 var1, double var2, double var4, double var6, Matrix4f var8, Matrix4f var9);

    @Invoker(value="setupTerrain")
    public void invokeSetupTerrain(class_4184 var1, class_4604 var2, boolean var3, boolean var4);

    @Invoker(value="updateChunks")
    public void invokeUpdateChunks(class_4184 var1);

    @Invoker(value="renderEntity")
    public void invokeRenderEntity(class_1297 var1, double var2, double var4, double var6, float var8, class_4587 var9, class_4597 var10);

    @Accessor(value="frustum")
    public class_4604 getFrustum();

    @Accessor(value="entityRenderDispatcher")
    public class_898 getEntityRenderDispatcher();

    @Accessor(value="frustum")
    public void setFrustum(class_4604 var1);

    @Accessor(value="bufferBuilders")
    public class_4599 getBufferBuilders();
}

