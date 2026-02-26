/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  foundry.veil.api.client.render.shader.program.ShaderProgram
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.minecraft.class_1937
 *  net.minecraft.class_2960
 *  net.minecraft.class_310
 *  org.joml.Matrix4f
 */
package com.sp.render.postshaders.custom;

import com.sp.DestroyingMinecraft;
import com.sp.render.ShadowMapRenderer;
import com.sp.render.postshaders.PostShader;
import com.sp.util.BetterUniforms;
import foundry.veil.api.client.render.shader.program.ShaderProgram;
import java.util.Optional;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1937;
import net.minecraft.class_2960;
import net.minecraft.class_310;
import org.joml.Matrix4f;

@Environment(value=EnvType.CLIENT)
public class SupernovaPostShader
extends PostShader {
    public static final class_2960 SUPERNOVA_POST = DestroyingMinecraft.idOf("sky");
    public static final class_2960 SUPERNOVA_SHADER = DestroyingMinecraft.idOf("sky/sky");

    public SupernovaPostShader() {
        super(SUPERNOVA_POST, SUPERNOVA_SHADER);
    }

    @Override
    public void setUniformsForShader(ShaderProgram shaderProgram, float tickDelta, class_310 client, class_1937 clientWorld) {
        Optional<Matrix4f> matrix4f = ShadowMapRenderer.getShadowViewMat();
        if (matrix4f.isPresent()) {
            BetterUniforms.setMatrix(shaderProgram, "sunMat", matrix4f.get().invert());
        }
        super.setUniformsForShader(shaderProgram, tickDelta, client, clientWorld);
    }
}

