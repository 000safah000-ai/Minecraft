/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_2246
 *  net.minecraft.class_243
 *  net.minecraft.class_2487
 *  net.minecraft.class_2512
 *  net.minecraft.class_2520
 *  net.minecraft.class_2680
 *  net.minecraft.class_3532
 *  net.minecraft.class_5819
 *  net.minecraft.class_7225$class_7874
 *  net.minecraft.class_7871
 *  net.minecraft.class_7924
 *  org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent
 *  org.ladysnake.cca.api.v3.component.tick.ClientTickingComponent
 */
package com.sp.cca.custom.entity;

import com.sp.cca.InitializeComponents;
import com.sp.entity.client.renderer.BlockType;
import com.sp.entity.custom.SpinningBlockEntity;
import java.util.List;
import net.minecraft.class_2246;
import net.minecraft.class_243;
import net.minecraft.class_2487;
import net.minecraft.class_2512;
import net.minecraft.class_2520;
import net.minecraft.class_2680;
import net.minecraft.class_3532;
import net.minecraft.class_5819;
import net.minecraft.class_7225;
import net.minecraft.class_7871;
import net.minecraft.class_7924;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import org.ladysnake.cca.api.v3.component.tick.ClientTickingComponent;

public class SpinningBlockComponent
implements AutoSyncedComponent,
ClientTickingComponent {
    private final SpinningBlockEntity spinningBlockEntity;
    private float pitchIncrement;
    private float yawIncrement;
    private float pitch;
    private float yaw;
    private float prevPitch;
    private float prevYaw;
    private class_243 acceleration;
    private boolean applyGravity;
    private int lifeTime;
    private float accelerationFactor;
    private float scale;
    private final BlockType blockType;
    private class_2680 blockState;
    private static final List<class_2680> randomBlocks = List.of(class_2246.field_10566.method_9564(), class_2246.field_10340.method_9564(), class_2246.field_10255.method_9564(), class_2246.field_10503.method_9564(), class_2246.field_10431.method_9564(), class_2246.field_10219.method_9564());

    public SpinningBlockComponent(SpinningBlockEntity spinningBlock) {
        this.spinningBlockEntity = spinningBlock;
        class_5819 random = spinningBlock.method_59922();
        this.blockState = randomBlocks.get(random.method_39332(0, randomBlocks.size() - 1));
        this.pitchIncrement = random.method_43057() * 20.0f;
        this.yawIncrement = random.method_43057() * 20.0f;
        this.accelerationFactor = random.method_43057() * 0.05f + 0.1f;
        this.scale = 1.0f;
        this.blockType = BlockType.SINGLE;
        this.acceleration = class_243.field_1353;
        this.applyGravity = true;
        this.lifeTime = 100;
    }

    public void setBlockState(class_2680 blockState) {
        this.blockState = blockState;
    }

    public class_2680 getBlockState() {
        return this.blockState;
    }

    public float getYaw(float tickDelta) {
        return class_3532.method_16439((float)tickDelta, (float)this.prevYaw, (float)this.yaw);
    }

    public float getPitch(float tickDelta) {
        return class_3532.method_16439((float)tickDelta, (float)this.prevPitch, (float)this.pitch);
    }

    public float getAccelerationFactor() {
        return this.accelerationFactor;
    }

    public float getScale() {
        return this.scale;
    }

    public BlockType getBlockType() {
        return this.blockType;
    }

    public boolean shouldApplyGravity() {
        return this.applyGravity;
    }

    public void setApplyGravity(boolean applyGravity) {
        this.applyGravity = applyGravity;
    }

    public int getLifeTime() {
        return this.lifeTime;
    }

    public void setLifeTime(int lifeTime) {
        this.lifeTime = lifeTime;
    }

    public class_243 getAcceleration() {
        return this.acceleration;
    }

    public void setAcceleration(class_243 acceleration) {
        this.acceleration = acceleration;
    }

    public void setAcceleration(float xAcceleration, float yAcceleration, float zAcceleration) {
        this.acceleration = new class_243((double)xAcceleration, (double)yAcceleration, (double)zAcceleration);
    }

    public void sync() {
        InitializeComponents.SPINNING_BLOCK.sync((Object)this.spinningBlockEntity);
    }

    public void readFromNbt(class_2487 nbtCompound, class_7225.class_7874 wrapperLookup) {
        this.blockState = class_2512.method_10681((class_7871)wrapperLookup.method_46762(class_7924.field_41254), (class_2487)nbtCompound.method_10562("blockState"));
        this.pitchIncrement = nbtCompound.method_10583("pitchIncrement");
        this.yawIncrement = nbtCompound.method_10583("yawIncrement");
        this.accelerationFactor = nbtCompound.method_10583("accelerationFactor");
        this.scale = nbtCompound.method_10583("scale");
        this.acceleration = new class_243((double)nbtCompound.method_10583("accelerationX"), (double)nbtCompound.method_10583("accelerationY"), (double)nbtCompound.method_10583("accelerationZ"));
        this.applyGravity = nbtCompound.method_10577("applyGravity");
    }

    public void writeToNbt(class_2487 nbtCompound, class_7225.class_7874 wrapperLookup) {
        nbtCompound.method_10566("blockState", (class_2520)class_2512.method_10686((class_2680)this.blockState));
        nbtCompound.method_10548("pitchIncrement", this.pitchIncrement);
        nbtCompound.method_10548("yawIncrement", this.yawIncrement);
        nbtCompound.method_10548("accelerationFactor", this.accelerationFactor);
        nbtCompound.method_10548("scale", this.scale);
        nbtCompound.method_10548("accelerationX", (float)this.acceleration.field_1352);
        nbtCompound.method_10548("accelerationY", (float)this.acceleration.field_1351);
        nbtCompound.method_10548("accelerationZ", (float)this.acceleration.field_1350);
        nbtCompound.method_10556("applyGravity", this.applyGravity);
    }

    public void clientTick() {
        this.prevPitch = this.pitch;
        this.prevYaw = this.yaw;
        if (!this.spinningBlockEntity.method_24828()) {
            this.pitch += this.pitchIncrement;
            this.yaw += this.yawIncrement;
        }
    }
}

