/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  foundry.veil.api.client.util.Easing
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 */
package com.sp.render.camerashake.custom;

import com.sp.render.camerashake.AbstractCameraShakeInstance;
import foundry.veil.api.client.util.Easing;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(value=EnvType.CLIENT)
public class SustainedCameraShakeInstance
extends AbstractCameraShakeInstance {
    private final float strength;
    private final int falloffDuration;
    private final Easing falloffEasing;

    public SustainedCameraShakeInstance(float strength, int sustainedDuration, int falloffDuration, Easing falloffEasing) {
        super(sustainedDuration);
        this.strength = strength;
        this.falloffDuration = falloffDuration;
        this.falloffEasing = falloffEasing;
    }

    @Override
    public void tick() {
        super.tick();
        if (this.progress <= this.duration) {
            this.trauma = this.strength;
        } else {
            float temp = this.strength * (1.0f - this.falloffEasing.ease((float)(this.progress - this.duration) / (float)this.falloffDuration));
            this.trauma = Math.max(temp, 0.0f);
        }
    }

    @Override
    public boolean isFinished() {
        return this.progress >= this.duration + this.falloffDuration;
    }
}

