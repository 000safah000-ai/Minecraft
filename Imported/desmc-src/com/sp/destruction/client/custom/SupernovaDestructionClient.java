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
 *  net.minecraft.class_1297
 *  net.minecraft.class_1937
 *  net.minecraft.class_243
 *  net.minecraft.class_310
 *  net.minecraft.class_3414
 *  net.minecraft.class_746
 */
package com.sp.destruction.client.custom;

import com.sp.DestroyingMinecraftClient;
import com.sp.destruction.DestructionType;
import com.sp.destruction.client.ClientDestructionEvent;
import com.sp.entity.custom.StarPiercerEntity;
import com.sp.render.camerashake.CameraShakeManager;
import com.sp.render.camerashake.custom.CameraShakeInstance;
import com.sp.render.camerashake.custom.SustainedCameraShakeInstance;
import com.sp.render.postshaders.PostShaders;
import com.sp.sounds.ModSounds;
import com.sp.util.BetterUniforms;
import com.sp.util.keyframes.Keyframe;
import com.sp.util.keyframes.KeyframeAnimation;
import com.sp.util.timer.ShaderTimer;
import foundry.veil.api.client.render.shader.program.ShaderProgram;
import foundry.veil.api.client.util.Easing;
import java.util.ArrayList;
import java.util.List;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1109;
import net.minecraft.class_1113;
import net.minecraft.class_1144;
import net.minecraft.class_1297;
import net.minecraft.class_1937;
import net.minecraft.class_243;
import net.minecraft.class_310;
import net.minecraft.class_3414;
import net.minecraft.class_746;

@Environment(value=EnvType.CLIENT)
public class SupernovaDestructionClient
extends ClientDestructionEvent {
    private static final ShaderTimer implodeTimer = new ShaderTimer();
    private static final ShaderTimer flashTimer = new ShaderTimer();
    private static final ShaderTimer explosionTimer = new ShaderTimer();
    private static float laserLength;
    private static List<StarPiercerEntity> starPiercers;
    private static class_243 laserPos;
    private static int flashFrame;

    public SupernovaDestructionClient() {
        super(DestructionType.SUPERNOVA, PostShaders.SUPERNOVA, 2930);
    }

    @Override
    public void resetEvent() {
        implodeTimer.reset();
        flashTimer.reset();
        explosionTimer.reset();
        laserLength = 0.0f;
        starPiercers.forEach(StarPiercerEntity::reset);
        DestroyingMinecraftClient.destructionDistance = Integer.MAX_VALUE;
        super.resetEvent();
    }

    @Override
    public void setUniforms(ShaderProgram shaderProgram, float tickDelta) {
        BetterUniforms.setVector3f(shaderProgram, "laserPos", laserPos.method_46409());
        BetterUniforms.setFloat(shaderProgram, "supernovaTimer", implodeTimer.getTimer(tickDelta));
        BetterUniforms.setFloat(shaderProgram, "flashTimer", flashTimer.getTimer(tickDelta));
        BetterUniforms.setFloat(shaderProgram, "explosionTimer", explosionTimer.getTimer(tickDelta));
        BetterUniforms.setFloat(shaderProgram, "laserLength", laserLength);
        BetterUniforms.setInt(shaderProgram, "flashFrame", flashFrame);
    }

    @Override
    protected KeyframeAnimation initAnimations(class_1937 world) {
        class_1144 soundManager = class_310.method_1551().method_1483();
        return new KeyframeAnimation.KeyframeAnimationBuilder(this.duration, new Keyframe(0.0), new Keyframe(300.0 / (double)this.duration, () -> {
            class_746 player = class_310.method_1551().field_1724;
            if (player != null) {
                starPiercers = world.method_8390(StarPiercerEntity.class, player.method_5829().method_1014(1000.0), class_1297::method_5805);
                starPiercers.getFirst().startup();
            }
            soundManager.method_4873((class_1113)class_1109.method_4757((class_3414)ModSounds.LASER_CHARGE, (float)1.0f, (float)1.0f));
        }), new Keyframe(1200.0 / (double)this.duration, () -> {
            soundManager.method_4873((class_1113)class_1109.method_4757((class_3414)ModSounds.LASER_PAUSE, (float)1.0f, (float)1.0f));
            laserPos = SupernovaDestructionClient.starPiercers.getFirst().field_22467.method_1031(15.4, 13.0, 0.0);
        }, (globalTime, localTime) -> {
            flashFrame = flashFrame == 0 ? 1 : 0;
        }), new Keyframe(1210.0 / (double)this.duration, () -> {
            SustainedCameraShakeInstance shakeInstance = new SustainedCameraShakeInstance(0.8f, 280, 100, Easing.LINEAR);
            CameraShakeManager.addCameraShake(shakeInstance);
            soundManager.method_4873((class_1113)class_1109.method_4757((class_3414)ModSounds.LASER_FIRE, (float)1.0f, (float)1.0f));
            flashFrame = 0;
        }, (globalTime, localTime) -> {
            laserLength = (float)localTime;
        }), new Keyframe(1500.0 / (double)this.duration, () -> {
            starPiercers.getFirst().powerDown();
            laserLength = 0.0f;
            soundManager.method_4873((class_1113)class_1109.method_4757((class_3414)ModSounds.LASER_POWER_DOWN, (float)1.0f, (float)1.0f));
        }), new Keyframe(2000.0 / (double)this.duration), new Keyframe(2300.0 / (double)this.duration, () -> soundManager.method_4873((class_1113)class_1109.method_4757((class_3414)ModSounds.SUPERNOVA_EXPLOSION, (float)1.0f, (float)1.0f)), (globalTime, localTime) -> {
            DestroyingMinecraftClient.destructionDistance = 300 - (int)((globalTime - 2880.0 / (double)this.duration) / 0.015 * 300.0);
            if (localTime < 0.335) {
                implodeTimer.setTimer(Easing.EASE_IN_CUBIC.ease((float)(localTime * 2.985074520111084)));
            } else {
                implodeTimer.maxTimer();
                flashTimer.setTimer(Math.clamp(Easing.EASE_IN_OUT_CUBIC.ease((float)((localTime - (double)0.335f) / (double)0.38f)), 0.0f, 1.0f));
                explosionTimer.setTimer((float)((localTime - (double)0.335f) / (double)0.77f));
            }
            if (globalTime >= 0.99) {
                CameraShakeInstance shakeInstance = new CameraShakeInstance(1.0f, 0.0f, 20, Easing.LINEAR);
                CameraShakeManager.addCameraShake(shakeInstance);
            }
        })).build();
    }

    static {
        starPiercers = new ArrayList<StarPiercerEntity>();
        laserPos = class_243.field_1353;
        flashFrame = -1;
    }
}

