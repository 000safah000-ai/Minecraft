/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  foundry.veil.api.client.render.shader.program.ShaderProgram
 *  foundry.veil.api.client.util.Easing
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.minecraft.class_1109
 *  net.minecraft.class_1113
 *  net.minecraft.class_1144
 *  net.minecraft.class_1937
 *  net.minecraft.class_310
 *  net.minecraft.class_3414
 */
package com.sp.destruction.client.custom;

import com.sp.DestroyingMinecraftClient;
import com.sp.destruction.DestructionType;
import com.sp.destruction.client.ClientDestructionEvent;
import com.sp.render.camerashake.CameraShakeManager;
import com.sp.render.camerashake.custom.CameraShakeInstance;
import com.sp.render.postshaders.PostShaders;
import com.sp.sounds.ModSounds;
import com.sp.sounds.instances.FadingSoundInstance;
import com.sp.util.BetterUniforms;
import com.sp.util.keyframes.Keyframe;
import com.sp.util.keyframes.KeyframeAnimation;
import com.sp.util.timer.ShaderTimer;
import foundry.veil.api.client.render.shader.program.ShaderProgram;
import foundry.veil.api.client.util.Easing;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1109;
import net.minecraft.class_1113;
import net.minecraft.class_1144;
import net.minecraft.class_1937;
import net.minecraft.class_310;
import net.minecraft.class_3414;

@Environment(value=EnvType.CLIENT)
public class PlanetDestructionClient
extends ClientDestructionEvent {
    private static final ShaderTimer planetFallTimer = new ShaderTimer();
    private static final ShaderTimer flashTimer = new ShaderTimer();
    private static FadingSoundInstance ambientSound;

    public PlanetDestructionClient() {
        super(DestructionType.PLANET, PostShaders.PLANET, 2400);
    }

    @Override
    public void resetEvent() {
        DestroyingMinecraftClient.destructionDistance = Integer.MAX_VALUE;
        planetFallTimer.reset();
        flashTimer.reset();
        if (ambientSound != null) {
            class_310.method_1551().method_1483().method_4870((class_1113)ambientSound);
        }
        super.resetEvent();
    }

    @Override
    public void setUniforms(ShaderProgram shaderProgram, float tickDelta) {
        BetterUniforms.setFloat(shaderProgram, "planetFallTimer", planetFallTimer.getTimer(tickDelta));
        BetterUniforms.setFloat(shaderProgram, "flashTimer", flashTimer.getTimer(tickDelta));
    }

    @Override
    protected KeyframeAnimation initAnimations(class_1937 world) {
        class_1144 soundManager = class_310.method_1551().method_1483();
        return new KeyframeAnimation.KeyframeAnimationBuilder(this.duration, new Keyframe(0.0, () -> {
            ambientSound = FadingSoundInstance.ambient(ModSounds.PLANET_AMBIENCE, 80, true, 0, 1.0f, 1.0f);
            soundManager.method_4873((class_1113)ambientSound);
        }), new Keyframe(300.0 / (double)this.duration, () -> {
            soundManager.method_4873((class_1113)class_1109.method_4757((class_3414)ModSounds.PLANET_RUMBLE, (float)1.0f, (float)1.0f));
            CameraShakeInstance cameraShakeInstance = new CameraShakeInstance(0.8f, 0.0f, 120, Easing.LINEAR);
            CameraShakeManager.addCameraShake(cameraShakeInstance);
        }), new Keyframe(1800.0 / (double)this.duration), new Keyframe(2170.0 / (double)this.duration, () -> {
            ambientSound.fadeOut();
            soundManager.method_4873((class_1113)class_1109.method_4757((class_3414)ModSounds.PLANET_IMPACT_INITIAL, (float)1.0f, (float)1.0f));
            CameraShakeInstance cameraShakeInstance = new CameraShakeInstance(0.8f, 0.0f, 40, Easing.LINEAR);
            CameraShakeManager.addCameraShake(cameraShakeInstance);
        }, (globalTime, localTime) -> flashTimer.setTimer((float)Math.min(localTime * 2.0, 1.0))), new Keyframe(2200.0f / (float)this.duration, () -> {
            soundManager.method_4873((class_1113)class_1109.method_4757((class_3414)ModSounds.PLANET_IMPACT, (float)1.0f, (float)1.0f));
            CameraShakeInstance cameraShakeInstance = new CameraShakeInstance(0.8f, 4.5f, 220, Easing.EASE_IN_QUINT);
            CameraShakeManager.addCameraShake(cameraShakeInstance);
        }, (globalTime, localTime) -> flashTimer.setTimer(1.0f)), new Keyframe((double)(2300.0f / (float)this.duration), (globalTime, localTime) -> {
            DestroyingMinecraftClient.destructionDistance = (int)((1.0 - localTime) * 300.0);
        })).globalAction((globalTime, localTime) -> planetFallTimer.setTimer((float)globalTime)).build();
    }
}

