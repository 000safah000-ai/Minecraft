/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.ints.Int2IntArrayMap
 *  net.minecraft.class_1937
 */
package com.sp.util.keyframes;

import com.sp.util.keyframes.Keyframe;
import it.unimi.dsi.fastutil.ints.Int2IntArrayMap;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import net.minecraft.class_1937;

public class KeyframeAnimation {
    private final List<Keyframe> keyframeList;
    private final Keyframe.KeyframeAction globalAction;
    private final Runnable endAction;
    private final int duration;
    public long startTime = -1L;
    private int progress;
    private boolean shouldSkipKeyframe;
    private Runnable keyframeSkippedCallback = () -> {};
    public int skippedTime;
    private final Int2IntArrayMap skippingCache = new Int2IntArrayMap();
    private int currentKeyframeIndex;
    private boolean endActionRan;

    private KeyframeAnimation(int duration, Keyframe.KeyframeAction globalAction, Runnable endAction, Keyframe ... keyframes) {
        if (keyframes.length == 0) {
            throw new RuntimeException("Cannot make a keyframe animation with zero keyframes");
        }
        this.keyframeList = Arrays.stream(keyframes).sorted((o1, o2) -> {
            int comp = Double.compare(o1.getKeyframeTime(), o2.getKeyframeTime());
            if (comp == 0) {
                throw new RuntimeException("Keyframes cannot have the same time value");
            }
            return comp;
        }).toList();
        this.duration = duration;
        this.globalAction = globalAction;
        this.endAction = endAction;
    }

    public void run(class_1937 world) {
        if (this.startTime == -1L) {
            this.startTime = world.method_8510();
        }
        this.progress = Math.toIntExact(world.method_8510() - this.startTime) + this.skippedTime;
        if (this.shouldSkipKeyframe) {
            int nextKeyframeTime = (int)Math.floor((double)this.duration * this.getNextKeyframeTime());
            this.skippedTime += nextKeyframeTime - this.progress;
            this.skippingCache.put(Math.toIntExact(world.method_8510()), this.skippedTime);
            this.shouldSkipKeyframe = false;
            this.keyframeSkippedCallback.run();
        }
        this.updateKeyframeAnimation((double)this.progress / (double)this.duration);
    }

    public void updateKeyframeAnimation(double time) {
        if (time >= 1.0) {
            if (!this.endActionRan) {
                this.endAction.run();
                this.endActionRan = true;
            }
            return;
        }
        Keyframe currentKeyframe = this.getCurrentKeyframe();
        Keyframe nextKeyframe = this.getNextKeyframe();
        if (nextKeyframe != null && nextKeyframe.getKeyframeTime() <= time) {
            currentKeyframe = nextKeyframe;
            ++this.currentKeyframeIndex;
            Keyframe keyframe = nextKeyframe = this.currentKeyframeIndex + 1 < this.keyframeList.size() - 1 ? this.keyframeList.get(this.currentKeyframeIndex + 1) : null;
        }
        if (!currentKeyframe.isInitialized()) {
            currentKeyframe.getInitAction().run();
            currentKeyframe.setInitialized(true);
        }
        double currentKeyframeTime = currentKeyframe.getKeyframeTime();
        double nextKeyframeTime = nextKeyframe != null ? nextKeyframe.getKeyframeTime() : 1.0;
        double localTime = (time - currentKeyframeTime) / (nextKeyframeTime - currentKeyframeTime);
        currentKeyframe.getAction().run(time, localTime);
        this.globalAction.run(time, time);
    }

    public void resetToCurrentTime(class_1937 world) {
        int worldTime = Math.toIntExact(world.method_8510());
        if (world.method_8510() < this.startTime) {
            this.resetAnimation();
            return;
        }
        this.skippedTime = 0;
        Map.Entry maxEntry = null;
        for (Map.Entry entry : this.skippingCache.int2IntEntrySet()) {
            if ((Integer)entry.getKey() > worldTime || maxEntry != null && (Integer)maxEntry.getKey() >= (Integer)entry.getKey()) continue;
            maxEntry = entry;
        }
        if (maxEntry != null) {
            this.skippedTime = (Integer)maxEntry.getValue();
        }
        this.progress = Math.toIntExact(world.method_8510() - this.startTime) + this.skippedTime;
        float time = (float)this.progress / (float)this.duration;
        for (int i = 0; i < this.keyframeList.size(); ++i) {
            Keyframe keyframe = this.keyframeList.get(i);
            if (i + 1 >= this.keyframeList.size()) {
                this.currentKeyframeIndex = i;
                break;
            }
            Keyframe nextKeyframe = this.keyframeList.get(i + 1);
            if (!(keyframe.getKeyframeTime() < (double)time) || !(nextKeyframe.getKeyframeTime() > (double)time)) continue;
            this.currentKeyframeIndex = i;
            break;
        }
    }

    public void keyframeSkippedCallback(Runnable callback) {
        this.keyframeSkippedCallback = callback;
    }

    public double getNextKeyframeTime() {
        Keyframe nextKeyframe = this.getNextKeyframe();
        if (nextKeyframe == null) {
            return 1.0;
        }
        return nextKeyframe.getKeyframeTime();
    }

    public void skipKeyframe() {
        this.shouldSkipKeyframe = true;
    }

    public int getProgress() {
        return this.progress;
    }

    private Keyframe getCurrentKeyframe() {
        return this.keyframeList.get(this.currentKeyframeIndex);
    }

    private Keyframe getNextKeyframe() {
        return this.currentKeyframeIndex + 1 <= this.keyframeList.size() - 1 ? this.keyframeList.get(this.currentKeyframeIndex + 1) : null;
    }

    public void resetAnimation() {
        this.progress = 0;
        this.startTime = -1L;
        this.shouldSkipKeyframe = false;
        this.currentKeyframeIndex = 0;
        this.endActionRan = false;
        this.skippingCache.clear();
        this.skippedTime = 0;
        for (Keyframe keyframe : this.keyframeList) {
            keyframe.setInitialized(false);
        }
    }

    public static class KeyframeAnimationBuilder {
        private final int duration;
        private final Keyframe[] keyframeList;
        private Keyframe.KeyframeAction globalAction = (globalTime, localTime) -> {};
        private Runnable endAction = () -> {};

        public KeyframeAnimationBuilder(int duration, Keyframe ... keyframes) {
            this.duration = duration;
            this.keyframeList = keyframes;
        }

        public KeyframeAnimationBuilder globalAction(Keyframe.KeyframeAction globalAction) {
            this.globalAction = globalAction;
            return this;
        }

        public KeyframeAnimationBuilder endAction(Runnable endAction) {
            this.endAction = endAction;
            return this;
        }

        public KeyframeAnimation build() {
            return new KeyframeAnimation(this.duration, this.globalAction, this.endAction, this.keyframeList);
        }
    }
}

