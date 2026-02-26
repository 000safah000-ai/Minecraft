/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_2338
 *  net.minecraft.class_238
 *  net.minecraft.class_243
 */
package com.sp.world.playzone;

import java.util.concurrent.atomic.AtomicInteger;
import net.minecraft.class_2338;
import net.minecraft.class_238;
import net.minecraft.class_243;

public class PlayZone {
    private final class_238 boundingBox;
    private static final AtomicInteger CURRENT_ID = new AtomicInteger();
    private final int id;

    public PlayZone(class_2338 position1, class_2338 position2) {
        this(class_238.method_54784((class_2338)position1, (class_2338)position2));
    }

    public PlayZone(class_238 box, int id) {
        this.boundingBox = box;
        this.id = id;
    }

    public PlayZone(class_238 box) {
        this.boundingBox = box;
        this.id = CURRENT_ID.incrementAndGet();
    }

    public boolean isPositionInsideZone(class_243 pos) {
        return this.boundingBox.method_1006(pos);
    }

    public int getId() {
        return this.id;
    }

    public class_238 getBoundingBox() {
        return this.boundingBox;
    }
}

