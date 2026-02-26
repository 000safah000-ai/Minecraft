/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1101
 *  net.minecraft.class_1113
 *  net.minecraft.class_1117
 *  net.minecraft.class_3414
 *  net.minecraft.class_3419
 */
package com.sp.sounds.instances;

import net.minecraft.class_1101;
import net.minecraft.class_1113;
import net.minecraft.class_1117;
import net.minecraft.class_3414;
import net.minecraft.class_3419;

public class FadingSoundInstance
extends class_1101
implements class_1117 {
    private final int fadeTime;
    private final float targetVolume;
    private int currentTime;
    private boolean shouldFadeOut;

    public static FadingSoundInstance ambient(class_3414 soundEvent, int fadeTime, boolean repeat, int repeatDelay, float volume, float pitch) {
        return new FadingSoundInstance(soundEvent, class_3419.field_15256, 0.0, 0.0, 0.0, fadeTime, repeat, repeatDelay, true, volume, pitch);
    }

    public FadingSoundInstance(class_3414 soundEvent, class_3419 soundCategory, double x, double y, double z, int fadeTime, boolean repeat, int repeatDelay, boolean relative, float volume, float pitch) {
        super(soundEvent, soundCategory, class_1113.method_43221());
        this.field_5439 = x;
        this.field_5450 = y;
        this.field_5449 = z;
        this.fadeTime = fadeTime;
        this.field_5446 = repeat;
        this.field_5451 = repeatDelay;
        this.field_18936 = relative;
        this.targetVolume = volume;
        this.field_5442 = 0.1f;
        this.field_5441 = pitch;
    }

    public void fadeOut() {
        this.shouldFadeOut = true;
    }

    public void method_16896() {
        double fade;
        if (!this.shouldFadeOut) {
            ++this.currentTime;
            fade = Math.min((double)this.currentTime / (double)this.fadeTime, (double)this.targetVolume);
        } else {
            --this.currentTime;
            fade = Math.max((double)this.currentTime / (double)this.fadeTime, 0.0);
        }
        this.currentTime = Math.clamp((long)this.currentTime, 0, this.fadeTime);
        this.field_5442 = (float)fade;
        if ((double)this.field_5442 <= 0.0) {
            this.method_24876();
        }
    }
}

