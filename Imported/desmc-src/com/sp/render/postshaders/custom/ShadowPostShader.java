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
 */
package com.sp.render.postshaders.custom;

import com.sp.DestroyingMinecraft;
import com.sp.config.DestroyingMinecraftConfig;
import com.sp.destruction.client.ClientDestructionEvents;
import com.sp.render.ShaderType;
import com.sp.render.ShadowMapRenderer;
import com.sp.render.postshaders.PostShader;
import com.sp.util.BetterUniforms;
import foundry.veil.api.client.render.shader.program.ShaderProgram;
import java.util.Objects;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1937;
import net.minecraft.class_2960;
import net.minecraft.class_310;

@Environment(value=EnvType.CLIENT)
public class ShadowPostShader
extends PostShader {
    public static final class_2960 SHADOWS_POST = DestroyingMinecraft.idOf("shadows");
    public static final class_2960 SHADOWS_SHADER = DestroyingMinecraft.idOf("shadows/shadows");

    public ShadowPostShader() {
        super(SHADOWS_POST, SHADOWS_SHADER);
    }

    @Override
    public void setUniformsForShader(ShaderProgram shaderProgram, float tickDelta, class_310 client, class_1937 clientWorld) {
        ShadowMapRenderer.setShadowUniforms(shaderProgram);
        if (Objects.requireNonNull(DestroyingMinecraftConfig.shaderType) == ShaderType.SUPERNOVA) {
            ClientDestructionEvents.SUPERNOVA_CLIENT.setUniforms(shaderProgram, tickDelta);
        } else {
            BetterUniforms.setFloat(shaderProgram, "flashTimer", 1.0f);
        }
    }
}

