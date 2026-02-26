/*
 * Decompiled with CFR 0.152.
 */
package com.sp.util.timer;

public class MsTimer {
    private long pauseTime;
    private long startTime = -1L;
    private long startPauseTime = -1L;
    private long tempPauseTime;

    public void start() {
        if (this.startTime == -1L) {
            this.startTime = System.currentTimeMillis();
        }
    }

    public void pause() {
        if (this.startTime != -1L && this.startPauseTime == -1L) {
            this.startPauseTime = System.currentTimeMillis();
        }
        this.tempPauseTime = System.currentTimeMillis() - this.startPauseTime;
    }

    public void resume() {
        if (this.startPauseTime != -1L) {
            this.startPauseTime = -1L;
            this.pauseTime += this.tempPauseTime;
            this.tempPauseTime = 0L;
        }
    }

    public long getTime() {
        return System.currentTimeMillis() - this.startTime - this.pauseTime - this.tempPauseTime;
    }

    public void stop() {
        this.startTime = -1L;
        this.startPauseTime = -1L;
        this.pauseTime = 0L;
        this.tempPauseTime = 0L;
    }
}

