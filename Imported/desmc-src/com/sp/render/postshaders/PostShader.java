/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  foundry.veil.api.client.render.post.PostPipeline$Context
 *  foundry.veil.api.client.render.shader.program.ShaderProgram
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.minecraft.class_1937
 *  net.minecraft.class_2960
 *  net.minecraft.class_310
 */
package com.sp.render.postshaders;

import foundry.veil.api.client.render.post.PostPipeline;
import foundry.veil.api.client.render.shader.program.ShaderProgram;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1937;
import net.minecraft.class_2960;
import net.minecraft.class_310;

@Environment(value=EnvType.CLIENT)
public abstract class PostShader {
    protected final class_2960 POST;
    protected final class_2960 SHADER;
    protected DestructionUniformCallback destructionUniformCallback;

    public PostShader(class_2960 post, class_2960 shader) {
        this.POST = post;
        this.SHADER = shader;
    }

    public void setUniformCallback(DestructionUniformCallback destructionUniformCallback) {
        this.destructionUniformCallback = destructionUniformCallback;
    }

    public void setUniforms(PostPipeline.Context context, float tickDelta, class_310 client, class_1937 clientWorld) {
        ShaderProgram shaderProgram = context.getShader(this.SHADER);
        if (shaderProgram != null) {
            this.setUniformsForShader(shaderProgram, tickDelta, client, clientWorld);
        }
    }

    public void setUniformsForShader(ShaderProgram shaderProgram, float tickDelta, class_310 client, class_1937 clientWorld) {
        if (this.destructionUniformCallback != null) {
            this.destructionUniformCallback.setUniforms(shaderProgram, tickDelta);
        }
    }

    public class_2960 getShader() {
        return this.SHADER;
    }

    public class_2960 getPost() {
        return this.POST;
    }

    public static interface DestructionUniformCallback {
        public void setUniforms(ShaderProgram var1, float var2);
    }
}

