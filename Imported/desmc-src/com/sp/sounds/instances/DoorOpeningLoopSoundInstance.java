/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1101
 *  net.minecraft.class_1113
 *  net.minecraft.class_243
 *  net.minecraft.class_3419
 */
package com.sp.sounds.instances;

import com.sp.sounds.ModSounds;
import net.minecraft.class_1101;
import net.minecraft.class_1113;
import net.minecraft.class_243;
import net.minecraft.class_3419;

public class DoorOpeningLoopSoundInstance
extends class_1101 {
    private boolean fadeOut;

    public DoorOpeningLoopSoundInstance(class_243 pos) {
        super(ModSounds.DOOR_OPENING_LOOP, class_3419.field_15256, class_1113.method_43221());
        this.field_5439 = pos.field_1352;
        this.field_5450 = pos.field_1351;
        this.field_5449 = pos.field_1350;
        this.field_5446 = true;
        this.field_5451 = 0;
        this.field_5442 = 0.01f;
    }

    public void method_16896() {
        this.field_5442 = this.fadeOut ? (this.field_5442 -= 0.05f) : Math.min(this.field_5442 + 0.02f, 1.0f);
        if (this.field_5442 <= 0.0f) {
            this.method_24876();
        }
    }

    public void startFadeOut() {
        this.fadeOut = true;
    }
}

