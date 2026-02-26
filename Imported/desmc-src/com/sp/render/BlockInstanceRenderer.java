/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.systems.RenderSystem
 *  foundry.veil.api.client.render.VeilRenderSystem
 *  foundry.veil.api.client.render.framebuffer.AdvancedFbo
 *  foundry.veil.api.client.render.shader.program.ShaderProgram
 *  foundry.veil.impl.client.render.dynamicbuffer.DynamicBufferManager
 *  net.minecraft.class_287
 *  net.minecraft.class_289
 *  net.minecraft.class_291
 *  net.minecraft.class_291$class_8555
 *  net.minecraft.class_293$class_5596
 *  net.minecraft.class_2960
 *  org.joml.Vector3f
 */
package com.sp.render;

import com.mojang.blaze3d.systems.RenderSystem;
import com.sp.DestroyingMinecraft;
import com.sp.render.CustomRenderLayersAndVertexFormats;
import com.sp.util.BetterUniforms;
import foundry.veil.api.client.render.VeilRenderSystem;
import foundry.veil.api.client.render.framebuffer.AdvancedFbo;
import foundry.veil.api.client.render.shader.program.ShaderProgram;
import foundry.veil.impl.client.render.dynamicbuffer.DynamicBufferManager;
import net.minecraft.class_287;
import net.minecraft.class_289;
import net.minecraft.class_291;
import net.minecraft.class_293;
import net.minecraft.class_2960;
import org.joml.Vector3f;

public class BlockInstanceRenderer {
    class_291 vertexBuffer = new class_291(class_291.class_8555.field_44793);
    private static final class_2960 shaderPath = DestroyingMinecraft.idOf("blackhole/blackholeterrain/blackholeterrain");
    private static final class_2960 dirtTexture = class_2960.method_60656((String)"textures/block/dirt.png");
    private static final class_2960 stoneTexture = class_2960.method_60656((String)"textures/block/stone.png");
    private static final class_2960 gravelTexture = class_2960.method_60656((String)"textures/block/gravel.png");
    private static final class_2960 deepslateTexture = class_2960.method_60656((String)"textures/block/deepslate.png");

    public BlockInstanceRenderer() {
        class_289 tessellator = class_289.method_1348();
        class_287 bufferBuilder = tessellator.method_60827(class_293.class_5596.field_27382, CustomRenderLayersAndVertexFormats.POSITION_TEXTURE_NORMAL);
        this.createCube(bufferBuilder, 0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
        this.vertexBuffer.method_1353();
        this.vertexBuffer.method_1352(bufferBuilder.method_60800());
        class_291.method_1354();
    }

    public void render() {
        RenderSystem.enableDepthTest();
        AdvancedFbo fbo = VeilRenderSystem.renderer().getFramebufferManager().getFramebuffer(DynamicBufferManager.MAIN_WRAPPER);
        if (fbo == null) {
            return;
        }
        ShaderProgram shader = VeilRenderSystem.setShader((class_2960)shaderPath);
        if (shader == null) {
            return;
        }
        fbo.bind(false);
        Vector3f position = new Vector3f(0.0f, 70.0f, 0.0f);
        RenderSystem.setShaderTexture((int)0, (class_2960)dirtTexture);
        RenderSystem.setShaderTexture((int)1, (class_2960)stoneTexture);
        RenderSystem.setShaderTexture((int)2, (class_2960)gravelTexture);
        RenderSystem.setShaderTexture((int)3, (class_2960)deepslateTexture);
        shader.setSampler((CharSequence)"Sampler0", RenderSystem.getShaderTexture((int)0));
        shader.setSampler((CharSequence)"Sampler1", RenderSystem.getShaderTexture((int)1));
        shader.setSampler((CharSequence)"Sampler2", RenderSystem.getShaderTexture((int)2));
        shader.setSampler((CharSequence)"Sampler3", RenderSystem.getShaderTexture((int)3));
        BetterUniforms.setVector3f(shader, "offset", position);
        shader.bindSamplers(0);
        shader.setDefaultUniforms(class_293.class_5596.field_27382);
        this.vertexBuffer.method_1353();
        shader.bind();
        VeilRenderSystem.drawInstanced((class_291)this.vertexBuffer, (int)1000);
        ShaderProgram.unbind();
        class_291.method_1354();
        AdvancedFbo.unbind();
    }

    private void createCube(class_287 bufferBuilder, float pMinX, float pMinY, float pMinZ, float pMaxX, float pMaxY, float pMaxZ) {
        float scale = 3.0f;
        bufferBuilder.method_60830(new Vector3f(pMinX, pMaxY, pMinZ).mul(scale)).method_22913(1.0f, 1.0f).method_22914(0.0f, 0.0f, -1.0f);
        bufferBuilder.method_60830(new Vector3f(pMaxX, pMaxY, pMinZ).mul(scale)).method_22913(0.0f, 1.0f).method_22914(0.0f, 0.0f, -1.0f);
        bufferBuilder.method_60830(new Vector3f(pMaxX, pMinY, pMinZ).mul(scale)).method_22913(0.0f, 0.0f).method_22914(0.0f, 0.0f, -1.0f);
        bufferBuilder.method_60830(new Vector3f(pMinX, pMinY, pMinZ).mul(scale)).method_22913(1.0f, 0.0f).method_22914(0.0f, 0.0f, -1.0f);
        bufferBuilder.method_60830(new Vector3f(pMaxX, pMinY, pMinZ).mul(scale)).method_22913(0.0f, 1.0f).method_22914(0.0f, -1.0f, 0.0f);
        bufferBuilder.method_60830(new Vector3f(pMaxX, pMinY, pMaxZ).mul(scale)).method_22913(0.0f, 0.0f).method_22914(0.0f, -1.0f, 0.0f);
        bufferBuilder.method_60830(new Vector3f(pMinX, pMinY, pMaxZ).mul(scale)).method_22913(1.0f, 0.0f).method_22914(0.0f, -1.0f, 0.0f);
        bufferBuilder.method_60830(new Vector3f(pMinX, pMinY, pMinZ).mul(scale)).method_22913(1.0f, 1.0f).method_22914(0.0f, -1.0f, 0.0f);
        bufferBuilder.method_60830(new Vector3f(pMinX, pMaxY, pMaxZ).mul(scale)).method_22913(0.0f, 1.0f).method_22914(0.0f, 1.0f, 0.0f);
        bufferBuilder.method_60830(new Vector3f(pMaxX, pMaxY, pMaxZ).mul(scale)).method_22913(0.0f, 0.0f).method_22914(0.0f, 1.0f, 0.0f);
        bufferBuilder.method_60830(new Vector3f(pMaxX, pMaxY, pMinZ).mul(scale)).method_22913(1.0f, 0.0f).method_22914(0.0f, 1.0f, 0.0f);
        bufferBuilder.method_60830(new Vector3f(pMinX, pMaxY, pMinZ).mul(scale)).method_22913(1.0f, 1.0f).method_22914(0.0f, 1.0f, 0.0f);
        bufferBuilder.method_60830(new Vector3f(pMinX, pMaxY, pMaxZ).mul(scale)).method_22913(0.0f, 1.0f).method_22914(0.0f, 0.0f, 1.0f);
        bufferBuilder.method_60830(new Vector3f(pMinX, pMinY, pMaxZ).mul(scale)).method_22913(0.0f, 0.0f).method_22914(0.0f, 0.0f, 1.0f);
        bufferBuilder.method_60830(new Vector3f(pMaxX, pMinY, pMaxZ).mul(scale)).method_22913(1.0f, 0.0f).method_22914(0.0f, 0.0f, 1.0f);
        bufferBuilder.method_60830(new Vector3f(pMaxX, pMaxY, pMaxZ).mul(scale)).method_22913(1.0f, 1.0f).method_22914(0.0f, 0.0f, 1.0f);
        bufferBuilder.method_60830(new Vector3f(pMaxX, pMaxY, pMaxZ).mul(scale)).method_22913(0.0f, 1.0f).method_22914(1.0f, 0.0f, 0.0f);
        bufferBuilder.method_60830(new Vector3f(pMaxX, pMinY, pMaxZ).mul(scale)).method_22913(0.0f, 0.0f).method_22914(1.0f, 0.0f, 0.0f);
        bufferBuilder.method_60830(new Vector3f(pMaxX, pMinY, pMinZ).mul(scale)).method_22913(1.0f, 0.0f).method_22914(1.0f, 0.0f, 0.0f);
        bufferBuilder.method_60830(new Vector3f(pMaxX, pMaxY, pMinZ).mul(scale)).method_22913(1.0f, 1.0f).method_22914(1.0f, 0.0f, 0.0f);
        bufferBuilder.method_60830(new Vector3f(pMinX, pMaxY, pMinZ).mul(scale)).method_22913(0.0f, 1.0f).method_22914(-1.0f, 0.0f, 0.0f);
        bufferBuilder.method_60830(new Vector3f(pMinX, pMinY, pMinZ).mul(scale)).method_22913(0.0f, 0.0f).method_22914(-1.0f, 0.0f, 0.0f);
        bufferBuilder.method_60830(new Vector3f(pMinX, pMinY, pMaxZ).mul(scale)).method_22913(1.0f, 0.0f).method_22914(-1.0f, 0.0f, 0.0f);
        bufferBuilder.method_60830(new Vector3f(pMinX, pMaxY, pMaxZ).mul(scale)).method_22913(1.0f, 1.0f).method_22914(-1.0f, 0.0f, 0.0f);
    }

    public void free() {
        this.vertexBuffer.close();
    }
}

