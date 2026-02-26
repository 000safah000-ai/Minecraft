/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1297
 *  net.minecraft.class_1299
 *  net.minecraft.class_1313
 *  net.minecraft.class_1937
 *  net.minecraft.class_2338
 *  net.minecraft.class_243
 *  net.minecraft.class_2487
 *  net.minecraft.class_2680
 *  net.minecraft.class_2945$class_9222
 */
package com.sp.entity.custom;

import com.sp.cca.InitializeComponents;
import com.sp.cca.custom.entity.SpinningBlockComponent;
import com.sp.cca.custom.world.WorldDestructionEventsComponent;
import com.sp.entity.ModEntities;
import net.minecraft.class_1297;
import net.minecraft.class_1299;
import net.minecraft.class_1313;
import net.minecraft.class_1937;
import net.minecraft.class_2338;
import net.minecraft.class_243;
import net.minecraft.class_2487;
import net.minecraft.class_2680;
import net.minecraft.class_2945;

public class SpinningBlockEntity
extends class_1297 {
    private final SpinningBlockComponent component = (SpinningBlockComponent)InitializeComponents.SPINNING_BLOCK.get((Object)this);

    public SpinningBlockEntity(class_1299<?> entityType, class_1937 world) {
        super(entityType, world);
    }

    private SpinningBlockEntity(class_1937 world, class_2680 state) {
        this(ModEntities.SPINNING_BLOCK, world);
        this.component.setBlockState(state);
    }

    public static SpinningBlockEntity spawnFromBlock(class_1937 world, class_2338 pos, class_2680 state) {
        SpinningBlockEntity entity = new SpinningBlockEntity(world, state);
        entity.method_33574(pos.method_46558());
        return entity;
    }

    protected void method_5693(class_2945.class_9222 builder) {
    }

    public void method_5773() {
        super.method_5773();
        if (!this.method_24828()) {
            class_243 acceleration = this.component.getAcceleration();
            if (!acceleration.equals((Object)class_243.field_1353)) {
                this.method_60491(acceleration);
            }
            if (this.component.shouldApplyGravity()) {
                WorldDestructionEventsComponent component = (WorldDestructionEventsComponent)InitializeComponents.EVENTS.get((Object)this.method_37908());
                class_243 gravityDir = WorldDestructionEventsComponent.gravityDir;
                class_243 velocity = new class_243(0.0, -0.07, 0.0).method_35590(new class_243(0.0, -0.07 + gravityDir.field_1351, gravityDir.field_1350), component.getGravityLerp());
                this.method_60491(velocity);
            }
            this.method_5784(class_1313.field_6308, this.method_18798());
        }
        if (!this.method_37908().field_9236 && this.field_6012 > this.component.getLifeTime()) {
            this.method_31472();
        }
    }

    public SpinningBlockComponent getComponent() {
        return this.component;
    }

    protected double method_7490() {
        return 0.1;
    }

    public boolean method_5640(double distance) {
        return distance < 99999.0;
    }

    protected void method_5749(class_2487 nbt) {
    }

    protected void method_5652(class_2487 nbt) {
    }
}

