/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_2487
 *  net.minecraft.class_2520
 *  net.minecraft.class_7225$class_7874
 *  org.joml.Quaternionf
 *  org.joml.Quaternionfc
 *  org.joml.Vector3f
 *  org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent
 */
package com.sp.cca.custom.entity;

import com.sp.cca.InitializeComponents;
import com.sp.entity.custom.BlockPhysicsEntity;
import net.minecraft.class_2487;
import net.minecraft.class_2520;
import net.minecraft.class_7225;
import org.joml.Quaternionf;
import org.joml.Quaternionfc;
import org.joml.Vector3f;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;

public class PhysicsBlockComponent
implements AutoSyncedComponent {
    private final BlockPhysicsEntity blockPhysicsEntity;
    private Quaternionf rotation = new Quaternionf();
    private float xRotationSpeed;
    private float yRotationSpeed;
    private float zRotationSpeed;
    private boolean isMeteorLike;
    private Quaternionf prevRotation = new Quaternionf();

    public PhysicsBlockComponent(BlockPhysicsEntity spinningBlock) {
        this.blockPhysicsEntity = spinningBlock;
    }

    public void setRotation(Quaternionf rotation) {
        this.rotation = new Quaternionf((Quaternionfc)rotation);
        this.sync();
    }

    public Quaternionf getRotation() {
        return this.rotation;
    }

    public Vector3f getRotationSpeed() {
        return new Vector3f(this.xRotationSpeed, this.yRotationSpeed, this.zRotationSpeed);
    }

    public void setRotationSpeed(float xRotationSpeed, float yRotationSpeed, float zRotationSpeed) {
        this.xRotationSpeed = xRotationSpeed;
        this.yRotationSpeed = yRotationSpeed;
        this.zRotationSpeed = zRotationSpeed;
    }

    public boolean isMeteorLike() {
        return this.isMeteorLike;
    }

    public void setMeteorLike(boolean meteorLike) {
        this.isMeteorLike = meteorLike;
    }

    public Quaternionf getLerpedRotation(float tickDelta) {
        Quaternionf lerpedQuaternion = new Quaternionf();
        this.prevRotation.slerp((Quaternionfc)this.rotation, tickDelta, lerpedQuaternion);
        return lerpedQuaternion;
    }

    public void sync() {
        InitializeComponents.PHYSICS_BLOCK.sync((Object)this.blockPhysicsEntity);
    }

    public void readFromNbt(class_2487 nbtCompound, class_7225.class_7874 wrapperLookup) {
        boolean shouldSync = false;
        if (nbtCompound.method_10545("rotation")) {
            class_2487 rotationNbt = nbtCompound.method_10562("rotation");
            this.prevRotation = new Quaternionf((Quaternionfc)this.rotation);
            this.rotation.set(rotationNbt.method_10583("x"), rotationNbt.method_10583("y"), rotationNbt.method_10583("z"), rotationNbt.method_10583("w"));
            shouldSync = true;
        }
        if (nbtCompound.method_10545("rotationSpeed")) {
            class_2487 rotationSpeedNbt = nbtCompound.method_10562("rotationSpeed");
            this.xRotationSpeed = rotationSpeedNbt.method_10583("x");
            this.yRotationSpeed = rotationSpeedNbt.method_10583("y");
            this.zRotationSpeed = rotationSpeedNbt.method_10583("z");
            shouldSync = true;
        }
        if (shouldSync) {
            this.sync();
        }
    }

    public void writeToNbt(class_2487 nbtCompound, class_7225.class_7874 wrapperLookup) {
        class_2487 rotationNbt = new class_2487();
        rotationNbt.method_10548("x", this.rotation.x);
        rotationNbt.method_10548("y", this.rotation.y);
        rotationNbt.method_10548("z", this.rotation.z);
        rotationNbt.method_10548("w", this.rotation.w);
        nbtCompound.method_10566("rotation", (class_2520)rotationNbt);
        class_2487 rotationSpeedNbt = new class_2487();
        rotationSpeedNbt.method_10548("x", this.xRotationSpeed);
        rotationSpeedNbt.method_10548("y", this.yRotationSpeed);
        rotationSpeedNbt.method_10548("z", this.zRotationSpeed);
        nbtCompound.method_10566("rotationSpeed", (class_2520)rotationSpeedNbt);
    }

    public static enum Type {
        DOOR,
        METEOR,
        DESTRUCTION;

    }
}

