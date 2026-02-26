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
 *  net.minecraft.class_1113$class_1114
 *  net.minecraft.class_1144
 *  net.minecraft.class_1937
 *  net.minecraft.class_310
 *  net.minecraft.class_3414
 *  net.minecraft.class_3419
 *  net.minecraft.class_638
 *  org.joml.Vector2f
 */
package com.sp.destruction.client.custom;

import com.sp.cca.InitializeComponents;
import com.sp.cca.custom.world.WorldDestructionEventsComponent;
import com.sp.destruction.DestructionType;
import com.sp.destruction.client.ClientDestructionEvent;
import com.sp.render.camerashake.CameraShakeManager;
import com.sp.render.camerashake.custom.CameraShakeInstance;
import com.sp.render.postshaders.PostShaders;
import com.sp.sounds.ModSounds;
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
import net.minecraft.class_3419;
import net.minecraft.class_638;
import org.joml.Vector2f;

@Environment(value=EnvType.CLIENT)
public class LaserDestructionClient
extends ClientDestructionEvent {
    public static final ShaderTimer laserLength = new ShaderTimer();
    public static final ShaderTimer cracksTime = new ShaderTimer();
    public static final ShaderTimer flashTimer = new ShaderTimer();
    private static class_1109 laserLoop;
    private static class_1109 crackingLoop;
    private static class_1109 laserEnding;

    public LaserDestructionClient() {
        super(DestructionType.ORBITAL_LASER, PostShaders.CRACKS, 2500);
    }

    @Override
    public void resetEvent() {
        laserLength.reset();
        cracksTime.reset();
        flashTimer.reset();
        class_1144 soundManager = class_310.method_1551().method_1483();
        if (laserLoop != null) {
            soundManager.method_4870((class_1113)laserLoop);
        }
        if (crackingLoop != null) {
            soundManager.method_4870((class_1113)crackingLoop);
        }
        if (laserEnding != null) {
            soundManager.method_4870((class_1113)laserEnding);
        }
        super.resetEvent();
    }

    @Override
    public void setUniforms(ShaderProgram shaderProgram, float tickDelta) {
        class_638 world = class_310.method_1551().field_1687;
        if (world != null) {
            WorldDestructionEventsComponent component = (WorldDestructionEventsComponent)InitializeComponents.EVENTS.get((Object)world);
            Vector2f pos = new Vector2f((float)component.getDestructionEventPosition().field_1352, (float)component.getDestructionEventPosition().field_1350);
            BetterUniforms.setVector2f(shaderProgram, "CENTER_POS", pos);
        }
        BetterUniforms.setFloat(shaderProgram, "laserLength", laserLength.getTimer(tickDelta));
        BetterUniforms.setFloat(shaderProgram, "cracksTime", cracksTime.getTimer(tickDelta));
        BetterUniforms.setFloat(shaderProgram, "flashTimer", flashTimer.getTimer(tickDelta));
    }

    @Override
    protected KeyframeAnimation initAnimations(class_1937 world) {
        class_1144 soundManager = class_310.method_1551().method_1483();
        return new KeyframeAnimation.KeyframeAnimationBuilder(this.duration, new Keyframe(0.0), new Keyframe(400.0 / (double)this.duration, () -> soundManager.method_4873((class_1113)class_1109.method_4757((class_3414)ModSounds.LASER_LANDING, (float)1.0f, (float)1.0f))), new Keyframe(478.0 / (double)this.duration, () -> {}, (globalTime, localTime) -> laserLength.setTimer((float)localTime)), new Keyframe(484.0 / (double)this.duration, () -> {
            laserLength.setTimer(1.0f);
            laserLoop = new class_1109(ModSounds.LASER_LOOP.method_14833(), class_3419.field_15256, 1.0f, 1.0f, class_1113.method_43221(), true, 0, class_1113.class_1114.field_5476, 0.0, 0.0, 0.0, true);
            soundManager.method_4873((class_1113)laserLoop);
            CameraShakeInstance cameraShakeInstance = new CameraShakeInstance(0.8f, 0.0f, 100, Easing.LINEAR);
            CameraShakeManager.addCameraShake(cameraShakeInstance);
        }), new Keyframe(540.0 / (double)this.duration, () -> {
            CameraShakeInstance cameraShakeInstance = new CameraShakeInstance(1.2f, 0.0f, 100, Easing.LINEAR);
            CameraShakeManager.addCameraShake(cameraShakeInstance);
        }), new Keyframe(700.0 / (double)this.duration, () -> {
            soundManager.method_4873((class_1113)class_1109.method_4757((class_3414)ModSounds.LASER_CRACKING_INITIAL, (float)1.0f, (float)1.0f));
            crackingLoop = new class_1109(ModSounds.LASER_CRACKING_LOOP.method_14833(), class_3419.field_15256, 1.0f, 1.0f, class_1113.method_43221(), true, 0, class_1113.class_1114.field_5476, 0.0, 0.0, 0.0, true);
            soundManager.method_4873((class_1113)crackingLoop);
            CameraShakeInstance cameraShakeInstance = new CameraShakeInstance(0.6f, 0.0f, 100, Easing.LINEAR);
            CameraShakeManager.addCameraShake(cameraShakeInstance);
        }, (globalTime, localTime) -> cracksTime.setTimer((float)localTime)), new Keyframe(2100.0 / (double)this.duration, () -> {
            laserEnding = class_1109.method_4757((class_3414)ModSounds.LASER_END, (float)1.0f, (float)1.0f);
            soundManager.method_4873((class_1113)laserEnding);
        }, (globalTime, localTime) -> flashTimer.setTimer((float)localTime)), new Keyframe(2500.0 / (double)this.duration)).build();
    }
}

