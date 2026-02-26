/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1297
 *  net.minecraft.class_1937
 *  net.minecraft.class_238
 *  net.minecraft.class_243
 *  net.minecraft.class_2487
 *  net.minecraft.class_2520
 *  net.minecraft.class_3222
 *  net.minecraft.class_7225$class_7874
 *  net.minecraft.class_9129
 *  org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent
 *  org.ladysnake.cca.api.v3.component.tick.CommonTickingComponent
 */
package com.sp.cca.custom.world;

import com.sp.cca.InitializeComponents;
import com.sp.destruction.DestructionEvent;
import com.sp.destruction.DestructionType;
import com.sp.entity.custom.BlockPhysicsEntity;
import com.sp.world.destructionevent.custom.BlackHoleDestruction;
import net.minecraft.class_1297;
import net.minecraft.class_1937;
import net.minecraft.class_238;
import net.minecraft.class_243;
import net.minecraft.class_2487;
import net.minecraft.class_2520;
import net.minecraft.class_3222;
import net.minecraft.class_7225;
import net.minecraft.class_9129;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import org.ladysnake.cca.api.v3.component.tick.CommonTickingComponent;

public class WorldDestructionEventsComponent
implements AutoSyncedComponent,
CommonTickingComponent {
    private final class_1937 world;
    private boolean syncLight;
    private DestructionEvent currentDestructionEvent;
    private class_243 destructionEventPosition;
    private double gravityLerp;
    public static final class_243 gravityDir = new class_243(0.0, 0.07, -0.03);

    public WorldDestructionEventsComponent(class_1937 world) {
        this.world = world;
        this.gravityLerp = 0.0;
        this.destructionEventPosition = class_243.field_1353;
    }

    public double getGravityLerp() {
        return this.gravityLerp;
    }

    public void setGravityLerp(double gravityLerp) {
        this.gravityLerp = gravityLerp;
    }

    public DestructionEvent getCurrentDestructionEvent() {
        return this.currentDestructionEvent;
    }

    public void setAndStartCurrentDestructionEvent(DestructionEvent currentDestructionEvent, long startTime) {
        if (this.currentDestructionEvent != null) {
            this.currentDestructionEvent.setActive(false, -1L);
            this.currentDestructionEvent.resetEvent();
            if (this.currentDestructionEvent.getDestructionType().equals((Object)DestructionType.BLACK_HOLE)) {
                BlackHoleDestruction.setStartDestruction(false);
                this.world.method_8390(BlockPhysicsEntity.class, class_238.method_30048((class_243)this.destructionEventPosition, (double)1000.0, (double)1000.0, (double)1000.0), blockPhysicsEntity -> true).forEach(class_1297::method_31472);
                this.setGravityLerp(0.0);
                this.syncLight();
            }
        }
        if (currentDestructionEvent == null) {
            this.currentDestructionEvent = null;
            return;
        }
        if (currentDestructionEvent.isClient() != this.world.field_9236) {
            throw new RuntimeException("Tried to add a " + (currentDestructionEvent.isClient() ? "client" : "server") + " event on a " + (this.world.field_9236 ? "client" : "server") + " world");
        }
        this.currentDestructionEvent = currentDestructionEvent;
        this.currentDestructionEvent.setActive(true, startTime);
    }

    public class_243 getDestructionEventPosition() {
        return this.destructionEventPosition;
    }

    public void setDestructionEventPosition(class_243 position) {
        this.destructionEventPosition = position;
    }

    public void writeSyncPacket(class_9129 buf, class_3222 recipient) {
        if (this.syncLight) {
            class_2487 nbtCompound = new class_2487();
            nbtCompound.method_10549("gravityLerp", this.gravityLerp);
            nbtCompound.method_10549("currentDestructionEventProgress", this.currentDestructionEvent != null ? (double)this.currentDestructionEvent.getProgress() : -1.0);
            buf.method_10794((class_2520)nbtCompound);
            this.syncLight = false;
        } else {
            super.writeSyncPacket(buf, recipient);
        }
    }

    public void readFromNbt(class_2487 nbtCompound, class_7225.class_7874 wrapperLookup) {
        this.gravityLerp = nbtCompound.method_10574("gravityLerp");
        if (this.currentDestructionEvent != null) {
            this.currentDestructionEvent.setProgress(nbtCompound.method_10550("currentDestructionEventProgress"));
        }
    }

    public void writeToNbt(class_2487 nbtCompound, class_7225.class_7874 wrapperLookup) {
        nbtCompound.method_10549("gravityLerp", this.gravityLerp);
        nbtCompound.method_10549("currentDestructionEventProgress", this.currentDestructionEvent != null ? (double)this.currentDestructionEvent.getProgress() : -1.0);
    }

    public void syncLight() {
        this.syncLight = true;
        this.sync();
    }

    public void sync() {
        InitializeComponents.EVENTS.sync((Object)this.world);
    }

    public void tick() {
        if (this.world.method_27983() == class_1937.field_25179 && this.currentDestructionEvent != null) {
            this.currentDestructionEvent.tick(this.world);
        }
    }
}

