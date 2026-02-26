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
package com.sp.render.postshaders.custom;

import com.sp.DestroyingMinecraft;
import com.sp.render.postshaders.PostShader;
import com.sp.util.BetterUniforms;
import foundry.veil.api.client.render.post.PostPipeline;
import foundry.veil.api.client.render.shader.program.ShaderProgram;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1937;
import net.minecraft.class_2960;
import net.minecraft.class_310;

@Environment(value=EnvType.CLIENT)
public class BloomPostShader
extends PostShader {
    public static final class_2960 BLOOM_POST = DestroyingMinecraft.idOf("bloom");
    public static final class_2960[] BLUR_IDENTIFIERS = new class_2960[]{DestroyingMinecraft.idOf("bloom/blur/horizontal"), DestroyingMinecraft.idOf("bloom/blur/vertical")};

    public BloomPostShader() {
        super(BLOOM_POST, BLOOM_POST);
    }

    @Override
    public void setUniforms(PostPipeline.Context context, float tickDelta, class_310 client, class_1937 clientWorld) {
        for (class_2960 identifier : BLUR_IDENTIFIERS) {
            ShaderProgram shaderProgram = context.getShader(identifier);
            if (shaderProgram == null) continue;
            BetterUniforms.setFloat(shaderProgram, "blurStrength", 1.0f);
            BetterUniforms.setFloat(shaderProgram, "xLimit", 0.53f);
        }
    }
}

