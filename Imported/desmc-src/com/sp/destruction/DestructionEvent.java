/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1937
 */
package com.sp.destruction;

import com.sp.cca.InitializeComponents;
import com.sp.cca.custom.world.WorldDestructionEventsComponent;
import com.sp.destruction.DestructionType;
import com.sp.util.keyframes.KeyframeAnimation;
import net.minecraft.class_1937;

public abstract class DestructionEvent {
    private final DestructionType destructionType;
    private final boolean isClient;
    protected boolean active;
    protected boolean initAnimations;
    protected KeyframeAnimation animation;
    protected final int duration;

    public DestructionEvent(DestructionType destructionType, int duration, boolean isClient) {
        this.destructionType = destructionType;
        this.duration = duration;
        this.isClient = isClient;
    }

    public void tick(class_1937 world) {
        if (!this.initAnimations) {
            this.animation = this.initAnimations(world);
            this.animation.keyframeSkippedCallback(() -> ((WorldDestructionEventsComponent)InitializeComponents.EVENTS.get((Object)world)).syncLight());
            this.initAnimations = true;
        }
        if (this.animation != null) {
            if (this.isActive()) {
                this.animation.run(world);
                if (this.animation.getProgress() % 200 == 0 && !world.field_9236) {
                    ((WorldDestructionEventsComponent)InitializeComponents.EVENTS.get((Object)world)).syncLight();
                }
            } else {
                this.resetEvent();
            }
        }
    }

    public void resetAnimationToCurrentTime(class_1937 world) {
        if (this.animation != null) {
            this.animation.resetToCurrentTime(world);
        }
    }

    protected void skipKeyframe() {
        this.animation.skipKeyframe();
    }

    protected KeyframeAnimation initAnimations(class_1937 world) {
        return null;
    }

    public int getProgress() {
        return this.animation.skippedTime;
    }

    public void setProgress(int progress) {
        if (this.isClient() && this.animation != null) {
            this.animation.skippedTime = progress;
        }
    }

    public void resetAnimations() {
        this.initAnimations = false;
        this.animation = null;
    }

    public void resetEvent() {
        this.active = false;
        if (this.animation != null) {
            this.animation.resetAnimation();
        }
    }

    public boolean isClient() {
        return this.isClient;
    }

    public boolean isActive() {
        return this.active;
    }

    public DestructionType getDestructionType() {
        return this.destructionType;
    }

    public void setActive(boolean active, long startTime) {
        if (active && this.animation != null) {
            this.animation.startTime = startTime;
        }
        this.active = active;
    }
}

