/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1297
 *  net.minecraft.class_1299
 *  net.minecraft.class_1937
 *  net.minecraft.class_2487
 *  net.minecraft.class_2940
 *  net.minecraft.class_2941
 *  net.minecraft.class_2943
 *  net.minecraft.class_2945
 *  net.minecraft.class_2945$class_9222
 */
package com.sp.entity.custom;

import net.minecraft.class_1297;
import net.minecraft.class_1299;
import net.minecraft.class_1937;
import net.minecraft.class_2487;
import net.minecraft.class_2940;
import net.minecraft.class_2941;
import net.minecraft.class_2943;
import net.minecraft.class_2945;

public class StarPiercerEntity
extends class_1297 {
    private static final class_2940<Boolean> STARTUP = class_2945.method_12791(StarPiercerEntity.class, (class_2941)class_2943.field_13323);
    private static final class_2940<Boolean> POWER_DOWN = class_2945.method_12791(StarPiercerEntity.class, (class_2941)class_2943.field_13323);

    public StarPiercerEntity(class_1299<?> type, class_1937 world) {
        super(type, world);
    }

    protected void method_5693(class_2945.class_9222 builder) {
        builder.method_56912(STARTUP, (Object)false);
        builder.method_56912(POWER_DOWN, (Object)false);
    }

    public boolean isStartingUp() {
        return (Boolean)this.field_6011.method_12789(STARTUP);
    }

    public boolean isPoweringDown() {
        return (Boolean)this.field_6011.method_12789(POWER_DOWN);
    }

    public void startup() {
        this.setData(true, false);
    }

    public void powerDown() {
        this.setData(false, true);
    }

    public void reset() {
        this.setData(false, false);
    }

    private void setData(boolean startup, boolean powerDown) {
        this.field_6011.method_12778(STARTUP, (Object)startup);
        this.field_6011.method_12778(POWER_DOWN, (Object)powerDown);
    }

    public void method_5711(byte status) {
        super.method_5711(status);
    }

    protected void method_5749(class_2487 nbt) {
    }

    protected void method_5652(class_2487 nbt) {
    }
}

