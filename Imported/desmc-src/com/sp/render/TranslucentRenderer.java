/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  foundry.veil.Veil
 *  foundry.veil.api.client.render.CullFrustum
 *  foundry.veil.api.client.render.VeilRenderSystem
 *  foundry.veil.api.client.render.VeilRenderer
 *  foundry.veil.api.client.render.ext.VeilDebug
 *  foundry.veil.api.client.render.framebuffer.AdvancedFbo
 *  foundry.veil.api.client.render.framebuffer.FramebufferAttachmentDefinition$Format
 *  foundry.veil.api.client.render.post.PostPipeline
 *  foundry.veil.api.client.render.post.PostProcessingManager
 *  foundry.veil.ext.RenderTargetExtension
 *  foundry.veil.impl.client.render.dynamicbuffer.DynamicBufferManager
 *  net.minecraft.class_2960
 *  net.minecraft.class_310
 *  net.minecraft.class_3695
 */
package com.sp.render;

import com.sp.DestroyingMinecraft;
import foundry.veil.Veil;
import foundry.veil.api.client.render.CullFrustum;
import foundry.veil.api.client.render.VeilRenderSystem;
import foundry.veil.api.client.render.VeilRenderer;
import foundry.veil.api.client.render.ext.VeilDebug;
import foundry.veil.api.client.render.framebuffer.AdvancedFbo;
import foundry.veil.api.client.render.framebuffer.FramebufferAttachmentDefinition;
import foundry.veil.api.client.render.post.PostPipeline;
import foundry.veil.api.client.render.post.PostProcessingManager;
import foundry.veil.ext.RenderTargetExtension;
import foundry.veil.impl.client.render.dynamicbuffer.DynamicBufferManager;
import net.minecraft.class_2960;
import net.minecraft.class_310;
import net.minecraft.class_3695;

public class TranslucentRenderer {
    private static final class_2960 TRANSLUCENT = DestroyingMinecraft.idOf("translucent");
    private static boolean printedError;
    private static AdvancedFbo translucent;

    public static void bind(int mask) {
        VeilDebug.get().pushDebugGroup((CharSequence)"Destroying-Minecraft Translucent");
        AdvancedFbo mainRenderTarget = AdvancedFbo.getMainFramebuffer();
        int w = mainRenderTarget.getWidth();
        int h = mainRenderTarget.getHeight();
        int framebufferTexture = mainRenderTarget.getColorTextureAttachment(0).method_4624();
        if (translucent == null || translucent.getWidth() != w || translucent.getHeight() != h) {
            TranslucentRenderer.free();
            translucent = AdvancedFbo.withSize((int)w, (int)h).addColorTextureWrapper(framebufferTexture).setFormat(FramebufferAttachmentDefinition.Format.DEPTH_COMPONENT).setDepthTextureBuffer().setDebugLabel("Destroying-Minecraft Translucent").build(true);
        }
        DynamicBufferManager dynamicBufferManager = VeilRenderSystem.renderer().getDynamicBufferManger();
        dynamicBufferManager.setEnabled(true);
        AdvancedFbo fbo = dynamicBufferManager.getDynamicFbo(translucent);
        dynamicBufferManager.setEnabled(false);
        VeilRenderSystem.renderer().getFramebufferManager().setFramebuffer(TRANSLUCENT, fbo);
        fbo.clear(mask);
        fbo.toRenderTarget().method_29329(mainRenderTarget.toRenderTarget());
        fbo.bind(false);
        ((RenderTargetExtension)class_310.method_1551().method_1522()).veil$setWrapper(fbo);
    }

    public static void unbind() {
        VeilRenderer renderer;
        PostProcessingManager postProcessingManager;
        PostPipeline pipeline;
        class_3695 profiler = class_310.method_1551().method_16011();
        boolean rendered = VeilRenderSystem.drawLights((class_3695)profiler, (CullFrustum)VeilRenderSystem.getCullingFrustum());
        ((RenderTargetExtension)class_310.method_1551().method_1522()).veil$setWrapper(null);
        if (rendered) {
            VeilRenderSystem.compositeLights((class_3695)profiler);
        }
        if ((pipeline = (postProcessingManager = (renderer = VeilRenderSystem.renderer()).getPostProcessingManager()).getPipeline(TRANSLUCENT)) == null) {
            if (!printedError) {
                Veil.LOGGER.error("Failed to apply translucent pipeline");
                printedError = true;
            }
        } else {
            postProcessingManager.runPipeline(pipeline, false);
        }
        AdvancedFbo.unbind();
        VeilDebug.get().popDebugGroup();
    }

    public static void free() {
        if (translucent != null) {
            VeilRenderSystem.renderer().getFramebufferManager().removeFramebuffer(TRANSLUCENT);
            translucent.free();
            translucent = null;
        }
        printedError = false;
    }
}

