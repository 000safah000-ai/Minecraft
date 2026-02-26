/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_3532
 */
package com.sp.util.timer;

import net.minecraft.class_3532;

public class ShaderTimer {
    private float timer;
    private float prevTimer;

    public void setTimer(float timer) {
        this.prevTimer = this.timer;
        this.timer = timer;
    }

    public void maxTimer() {
        this.prevTimer = 1.0f;
        this.timer = 1.0f;
    }

    public void reset() {
        this.prevTimer = 0.0f;
        this.timer = 0.0f;
    }

    public float getTimer(float tickDelta) {
        return class_3532.method_16439((float)tickDelta, (float)this.prevTimer, (float)this.timer);
    }
}

