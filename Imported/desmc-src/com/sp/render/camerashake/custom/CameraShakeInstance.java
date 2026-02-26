/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  foundry.veil.api.client.util.Easing
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.minecraft.class_3532
 */
package com.sp.render.camerashake.custom;

import com.sp.render.camerashake.AbstractCameraShakeInstance;
import foundry.veil.api.client.util.Easing;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_3532;

@Environment(value=EnvType.CLIENT)
public class CameraShakeInstance
extends AbstractCameraShakeInstance {
    private final float startStrength;
    private final float endStrength;
    private final Easing easing;

    public CameraShakeInstance(float startStrength, float endStrength, int duration, Easing easing) {
        super(duration);
        this.startStrength = startStrength;
        this.endStrength = endStrength;
        this.easing = easing;
    }

    @Override
    public void tick() {
        super.tick();
        float time = this.easing.ease((float)this.progress / (float)this.duration);
        float finalTrauma = class_3532.method_16439((float)time, (float)this.startStrength, (float)this.endStrength);
        this.trauma = Math.max(finalTrauma, 0.0f);
    }
}

