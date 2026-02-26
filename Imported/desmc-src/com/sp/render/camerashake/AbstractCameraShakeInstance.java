/*
 * Decompiled with CFR 0.152.
 */
package com.sp.render.camerashake;

public abstract class AbstractCameraShakeInstance {
    protected float trauma;
    protected int progress;
    protected final int duration;

    protected AbstractCameraShakeInstance(int duration) {
        this.duration = duration;
        this.progress = 0;
    }

    public void tick() {
        ++this.progress;
    }

    public float getTrauma() {
        return this.trauma;
    }

    public boolean isFinished() {
        return this.progress >= this.duration;
    }
}

