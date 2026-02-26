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
 *  org.joml.Vector3f
 */
package com.sp.render.postshaders.custom;

import com.sp.DestroyingMinecraft;
import com.sp.cca.InitializeComponents;
import com.sp.cca.custom.world.WorldDestructionEventsComponent;
import com.sp.render.PrevUniforms;
import com.sp.render.postshaders.PostShader;
import com.sp.util.BetterUniforms;
import foundry.veil.api.client.render.shader.program.ShaderProgram;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1937;
import net.minecraft.class_2960;
import net.minecraft.class_310;
import org.joml.Vector3f;

@Environment(value=EnvType.CLIENT)
public class BlackHolePostShader
extends PostShader {
    public static final class_2960 BLACK_HOLE_POST = DestroyingMinecraft.idOf("black_hole");
    public static final class_2960 BLACK_HOLE_SHADER = DestroyingMinecraft.idOf("blackhole/black_hole");

    public BlackHolePostShader() {
        super(BLACK_HOLE_POST, BLACK_HOLE_SHADER);
    }

    @Override
    public void setUniformsForShader(ShaderProgram shaderProgram, float tickDelta, class_310 client, class_1937 clientWorld) {
        super.setUniformsForShader(shaderProgram, tickDelta, client, clientWorld);
        if (PrevUniforms.isInitialized()) {
            BetterUniforms.setMatrix(shaderProgram, "prevProjMat", PrevUniforms.getPrevProjMat());
            BetterUniforms.setMatrix(shaderProgram, "prevViewMat", PrevUniforms.getPrevModelViewMat());
            BetterUniforms.setVector3f(shaderProgram, "prevCameraPos", PrevUniforms.getPrevCameraPos());
        }
        WorldDestructionEventsComponent component = (WorldDestructionEventsComponent)InitializeComponents.EVENTS.get((Object)clientWorld);
        float redMultiplier = (float)Math.max(1.0 - component.getGravityLerp(), (double)0.01f);
        BetterUniforms.setVector3f(shaderProgram, "redMultiplier", new Vector3f(1.0f, redMultiplier, redMultiplier));
        PrevUniforms.update();
    }
}

