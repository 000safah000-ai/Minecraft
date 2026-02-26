/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1297
 *  net.minecraft.class_1299
 *  net.minecraft.class_1313
 *  net.minecraft.class_1665
 *  net.minecraft.class_1799
 *  net.minecraft.class_1802
 *  net.minecraft.class_1935
 *  net.minecraft.class_1937
 *  net.minecraft.class_243
 *  net.minecraft.class_3218
 *  net.minecraft.class_3419
 *  net.minecraft.class_3726
 *  net.minecraft.class_3959
 *  net.minecraft.class_3959$class_242
 *  net.minecraft.class_3959$class_3960
 *  net.minecraft.class_3965
 */
package com.sp.entity.custom;

import com.sp.sounds.ModSounds;
import com.sp.util.MathUtil;
import com.sp.world.spinningblockexplosion.custom.PointSBE;
import net.minecraft.class_1297;
import net.minecraft.class_1299;
import net.minecraft.class_1313;
import net.minecraft.class_1665;
import net.minecraft.class_1799;
import net.minecraft.class_1802;
import net.minecraft.class_1935;
import net.minecraft.class_1937;
import net.minecraft.class_243;
import net.minecraft.class_3218;
import net.minecraft.class_3419;
import net.minecraft.class_3726;
import net.minecraft.class_3959;
import net.minecraft.class_3965;

public class MeteorEntity
extends class_1665 {
    public MeteorEntity(class_1299<? extends class_1665> entityType, class_1937 world) {
        super(entityType, world);
    }

    public void method_5773() {
        super.method_5773();
        if (!this.field_7588 && !this.method_24828()) {
            this.method_18799(new class_243(-1.5, -1.5, 0.0));
            this.method_5784(class_1313.field_6308, this.method_18798());
        }
        if (!this.method_37908().field_9236) {
            class_3965 hitResult = this.method_37908().method_17742(new class_3959(this.method_19538(), this.method_19538().method_1031(0.0, -100.0, 0.0), class_3959.class_3960.field_17558, class_3959.class_242.field_1348, class_3726.method_16194()));
            float distanceToGround = (float)hitResult.method_17777().method_46558().method_1025(this.method_19538());
            if (this.field_6012 > 200 || distanceToGround <= 4.0f) {
                this.method_24920(hitResult);
            }
        } else if (this.field_6012 == 1) {
            this.method_37908().method_55116((class_1297)this, ModSounds.METEOR_WHISTLE, class_3419.field_15256, 10.0f, MathUtil.nextBetween(0.6f, 1.2f));
        }
    }

    public void method_36209() {
        super.method_36209();
    }

    protected void method_24920(class_3965 blockHitResult) {
        this.method_5783(ModSounds.METEOR_IMPACT, 100.0f, 1.2f / (this.field_5974.method_43057() * 0.2f + 0.9f));
        if (!this.method_37908().field_9236) {
            PointSBE explosion = new PointSBE(this.field_5974.method_39332(4, 7), 0.2f, this.method_19538());
            explosion.beginExplosion((class_3218)this.method_37908());
            this.method_31472();
        }
    }

    protected class_1799 method_57314() {
        return new class_1799((class_1935)class_1802.field_8814);
    }
}

