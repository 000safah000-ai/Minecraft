/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  foundry.veil.api.client.render.shader.program.ShaderProgram
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.minecraft.class_1109
 *  net.minecraft.class_1113
 *  net.minecraft.class_1937
 *  net.minecraft.class_310
 */
package com.sp.destruction.client.custom;

import com.sp.destruction.DestructionType;
import com.sp.destruction.client.ClientDestructionEvent;
import com.sp.render.postshaders.PostShaders;
import com.sp.util.keyframes.Keyframe;
import com.sp.util.keyframes.KeyframeAnimation;
import foundry.veil.api.client.render.shader.program.ShaderProgram;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1109;
import net.minecraft.class_1113;
import net.minecraft.class_1937;
import net.minecraft.class_310;

@Environment(value=EnvType.CLIENT)
public class BlackHoleDestructionClientPart2
extends ClientDestructionEvent {
    private class_1109 destructionSoundInstance;

    public BlackHoleDestructionClientPart2() {
        super(DestructionType.BLACK_HOLE, PostShaders.BLACK_HOLE, 3400);
    }

    @Override
    public void resetEvent() {
        if (this.destructionSoundInstance != null) {
            class_310.method_1551().method_1483().method_4870((class_1113)this.destructionSoundInstance);
        }
        super.resetEvent();
    }

    @Override
    protected KeyframeAnimation initAnimations(class_1937 world) {
        return new KeyframeAnimation.KeyframeAnimationBuilder(this.duration, new Keyframe(0.0)).build();
    }

    @Override
    public void setUniforms(ShaderProgram shaderProgram, float tickDelta) {
    }
}

