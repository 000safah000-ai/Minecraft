/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  foundry.veil.api.client.render.shader.program.ShaderProgram
 *  foundry.veil.api.client.util.Easing
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.minecraft.class_1937
 *  net.minecraft.class_310
 *  net.minecraft.class_638
 */
package com.sp.destruction.client.custom;

import com.sp.cca.InitializeComponents;
import com.sp.cca.custom.world.WorldDestructionEventsComponent;
import com.sp.destruction.DestructionType;
import com.sp.destruction.client.ClientDestructionEvent;
import com.sp.render.postshaders.PostShaders;
import com.sp.util.BetterUniforms;
import com.sp.util.timer.ShaderTimer;
import foundry.veil.api.client.render.shader.program.ShaderProgram;
import foundry.veil.api.client.util.Easing;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1937;
import net.minecraft.class_310;
import net.minecraft.class_638;

@Environment(value=EnvType.CLIENT)
public class NukeDestructionClient
extends ClientDestructionEvent {
    private static final ShaderTimer smokeRiseTimer = new ShaderTimer();
    private static final ShaderTimer flashTimer = new ShaderTimer();
    private long startTime = -1L;
    private float floatProgress;

    public NukeDestructionClient() {
        super(DestructionType.NUKE, PostShaders.NUKE, 100);
    }

    @Override
    public void tick(class_1937 world) {
        if (this.active) {
            if (this.startTime == -1L) {
                this.startTime = world.method_8510();
            }
            if (world.method_8510() < this.startTime) {
                this.resetEvent();
            }
            this.floatProgress = (float)Math.min((double)(world.method_8510() - this.startTime) / (double)this.duration, 1.0);
            if (this.floatProgress <= (float)this.duration) {
                smokeRiseTimer.setTimer(Easing.EASE_OUT_SINE.ease(this.floatProgress));
                flashTimer.setTimer(Easing.EASE_OUT_SINE.ease(Math.min(this.floatProgress * 2.75f, 1.0f)));
            }
        } else {
            this.resetEvent();
        }
    }

    @Override
    public void resetEvent() {
        smokeRiseTimer.reset();
        flashTimer.reset();
        this.startTime = -1L;
        super.resetEvent();
    }

    @Override
    public void setUniforms(ShaderProgram shaderProgram, float tickDelta) {
        class_638 world = class_310.method_1551().field_1687;
        if (world != null) {
            WorldDestructionEventsComponent component = (WorldDestructionEventsComponent)InitializeComponents.EVENTS.get((Object)world);
            BetterUniforms.setVector3f(shaderProgram, "NUKE_POS", component.getDestructionEventPosition().method_46409());
        }
        BetterUniforms.setFloat(shaderProgram, "smokeRiseTimer", smokeRiseTimer.getTimer(tickDelta));
        BetterUniforms.setFloat(shaderProgram, "flashTimer", flashTimer.getTimer(tickDelta));
    }
}

