/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  foundry.veil.api.client.util.Easing
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.minecraft.class_1657
 *  net.minecraft.class_2338
 *  net.minecraft.class_243
 *  net.minecraft.class_310
 */
package com.sp.render.camerashake.custom;

import com.sp.render.camerashake.AbstractCameraShakeInstance;
import foundry.veil.api.client.util.Easing;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1657;
import net.minecraft.class_2338;
import net.minecraft.class_243;
import net.minecraft.class_310;

@Environment(value=EnvType.CLIENT)
public class PointCameraShake
extends AbstractCameraShakeInstance {
    private final class_1657 player;
    private final class_243 position;
    private final float strength;
    private final Easing easing;

    public PointCameraShake(class_2338 position, float strength, int duration, Easing easing) {
        this(position.method_46558(), strength, duration, easing);
    }

    public PointCameraShake(class_243 position, float strength, int duration, Easing easing) {
        super(duration);
        this.position = position;
        this.strength = strength;
        this.easing = easing;
        this.player = class_310.method_1551().field_1724;
    }

    @Override
    public void tick() {
        super.tick();
        if (this.player != null) {
            float distToCenter = 1.0f - (float)Math.sqrt(this.player.method_19538().method_1025(this.position)) / this.strength;
            float temp = this.strength * (1.0f - this.easing.ease((float)this.progress / (float)this.duration));
            this.trauma = Math.max(temp * distToCenter, 0.0f);
        }
    }
}

