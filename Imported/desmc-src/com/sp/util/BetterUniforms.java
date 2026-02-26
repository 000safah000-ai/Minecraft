/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  foundry.veil.api.client.render.shader.program.ShaderProgram
 *  foundry.veil.api.client.render.shader.uniform.ShaderUniform
 *  org.joml.Matrix4f
 *  org.joml.Matrix4fc
 *  org.joml.Vector2f
 *  org.joml.Vector2fc
 *  org.joml.Vector3f
 *  org.joml.Vector3fc
 */
package com.sp.util;

import foundry.veil.api.client.render.shader.program.ShaderProgram;
import foundry.veil.api.client.render.shader.uniform.ShaderUniform;
import org.joml.Matrix4f;
import org.joml.Matrix4fc;
import org.joml.Vector2f;
import org.joml.Vector2fc;
import org.joml.Vector3f;
import org.joml.Vector3fc;

public class BetterUniforms {
    public static void setMatrix(ShaderProgram shaderProgram, String charSequence, Matrix4f matrix4f) {
        ShaderUniform uniform = shaderProgram.getUniform((CharSequence)charSequence);
        if (uniform != null) {
            uniform.setMatrix((Matrix4fc)matrix4f);
        }
    }

    public static void setVector3f(ShaderProgram shaderProgram, String charSequence, Vector3f vector3f) {
        ShaderUniform uniform = shaderProgram.getUniform((CharSequence)charSequence);
        if (uniform != null) {
            uniform.setVector((Vector3fc)vector3f);
        }
    }

    public static void setVector2f(ShaderProgram shaderProgram, String charSequence, Vector2f vector2f) {
        ShaderUniform uniform = shaderProgram.getUniform((CharSequence)charSequence);
        if (uniform != null) {
            uniform.setVector((Vector2fc)vector2f);
        }
    }

    public static void setFloat(ShaderProgram shaderProgram, String charSequence, float value) {
        ShaderUniform uniform = shaderProgram.getUniform((CharSequence)charSequence);
        if (uniform != null) {
            uniform.setFloat(value);
        }
    }

    public static void setInt(ShaderProgram shaderProgram, String charSequence, int value) {
        ShaderUniform uniform = shaderProgram.getUniform((CharSequence)charSequence);
        if (uniform != null) {
            uniform.setInt(value);
        }
    }
}

