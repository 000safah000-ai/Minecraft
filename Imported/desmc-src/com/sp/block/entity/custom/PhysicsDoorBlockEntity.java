/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1113
 *  net.minecraft.class_1657
 *  net.minecraft.class_1937
 *  net.minecraft.class_2338
 *  net.minecraft.class_2350
 *  net.minecraft.class_243
 *  net.minecraft.class_2487
 *  net.minecraft.class_2512
 *  net.minecraft.class_2586
 *  net.minecraft.class_2596
 *  net.minecraft.class_2602
 *  net.minecraft.class_2622
 *  net.minecraft.class_2680
 *  net.minecraft.class_310
 *  net.minecraft.class_3419
 *  net.minecraft.class_437
 *  net.minecraft.class_7225$class_7874
 *  org.jetbrains.annotations.Nullable
 */
package com.sp.block.entity.custom;

import com.sp.block.entity.ModBlockEntities;
import com.sp.entity.custom.BlockPhysicsEntity;
import com.sp.render.gui.screen.PhysicsDoorBlockScreen;
import com.sp.sounds.ModSounds;
import com.sp.sounds.instances.DoorOpeningLoopSoundInstance;
import net.minecraft.class_1113;
import net.minecraft.class_1657;
import net.minecraft.class_1937;
import net.minecraft.class_2338;
import net.minecraft.class_2350;
import net.minecraft.class_243;
import net.minecraft.class_2487;
import net.minecraft.class_2512;
import net.minecraft.class_2586;
import net.minecraft.class_2596;
import net.minecraft.class_2602;
import net.minecraft.class_2622;
import net.minecraft.class_2680;
import net.minecraft.class_310;
import net.minecraft.class_3419;
import net.minecraft.class_437;
import net.minecraft.class_7225;
import org.jetbrains.annotations.Nullable;

public class PhysicsDoorBlockEntity
extends class_2586 {
    private class_2338 corner1 = class_2338.field_10980;
    private class_2338 corner2 = class_2338.field_10980;
    private class_2350 movementDirection = class_2350.field_11036;
    private int numOfBlocks = 0;
    private int speed = 1;
    private boolean showSelection;
    private boolean useSound = true;
    private boolean settingSelection;
    private boolean open;
    private boolean doorMoving;
    private class_243 startingPos;
    private BlockPhysicsEntity currentDoor;
    private DoorOpeningLoopSoundInstance doorOpeningSoundInstance;

    public PhysicsDoorBlockEntity(class_2338 pos, class_2680 state) {
        super(ModBlockEntities.PHYSICS_DOOR_BE, pos, state);
    }

    protected void method_11007(class_2487 nbt, class_7225.class_7874 registryLookup) {
        nbt.method_10566("corner1", class_2512.method_10692((class_2338)this.corner1));
        nbt.method_10566("corner2", class_2512.method_10692((class_2338)this.corner2));
        nbt.method_10569("direction", this.movementDirection.method_10146());
        nbt.method_10569("numOfBlocks", this.numOfBlocks);
        nbt.method_10569("speed", this.speed);
        nbt.method_10556("showSelection", this.showSelection);
        nbt.method_10556("useSound", this.useSound);
        nbt.method_10556("doorMoving", this.doorMoving);
    }

    protected void method_11014(class_2487 nbt, class_7225.class_7874 registryLookup) {
        this.corner1 = class_2512.method_10691((class_2487)nbt, (String)"corner1").orElse(null);
        this.corner2 = class_2512.method_10691((class_2487)nbt, (String)"corner2").orElse(null);
        this.movementDirection = class_2350.method_10143((int)nbt.method_10550("direction"));
        this.numOfBlocks = nbt.method_10550("numOfBlocks");
        this.speed = nbt.method_10550("speed");
        this.showSelection = nbt.method_10577("showSelection");
        this.useSound = nbt.method_10577("useSound");
        this.doorMoving = nbt.method_10577("doorMoving");
    }

    @Nullable
    public class_2596<class_2602> method_38235() {
        return class_2622.method_38585((class_2586)this);
    }

    public class_2487 method_16887(class_7225.class_7874 registryLookup) {
        return this.method_58692(registryLookup);
    }

    public boolean openScreen(class_1657 player) {
        if (!player.method_7338()) {
            return false;
        }
        if (player.method_5770().field_9236) {
            class_310.method_1551().method_1507((class_437)new PhysicsDoorBlockScreen(this));
        }
        return true;
    }

    public void moveDoor(class_1937 world) {
        if (this.numOfBlocks <= 0) {
            return;
        }
        class_2338 corner1 = this.corner1;
        class_2338 corner2 = this.corner2;
        this.currentDoor = BlockPhysicsEntity.ofBlocks(world, corner1, corner2);
        this.startingPos = this.currentDoor.method_19538();
        class_243 velocity = new class_243((double)this.movementDirection.method_10148(), (double)this.movementDirection.method_10164(), (double)this.movementDirection.method_10165()).method_1021((double)this.speed / 400.0);
        this.currentDoor.method_18799(velocity);
        this.currentDoor.field_6007 = true;
        this.currentDoor.field_6037 = true;
        this.doorMoving = true;
        this.method_5431();
        world.method_8413(this.method_11016(), world.method_8320(this.method_11016()), world.method_8320(this.method_11016()), 3);
        if (this.useSound) {
            world.method_8396(null, this.currentDoor.method_24515(), ModSounds.DOOR_OPEN, class_3419.field_15256, 10.0f, 1.0f);
        }
    }

    public void tick(class_1937 world, class_2338 pos, class_2680 state) {
        if (!world.field_9236 && this.doorMoving && this.currentDoor != null) {
            class_2350 opposite = this.movementDirection.method_10153();
            class_243 offset = new class_243((double)opposite.method_10148(), (double)opposite.method_10164(), (double)opposite.method_10165());
            if (this.currentDoor.method_19538().method_1019(offset.method_1021((double)this.numOfBlocks)).method_1022(this.startingPos) <= 0.05) {
                this.currentDoor.setDown();
                this.currentDoor.markForDiscard();
                this.doorMoving = false;
                boolean bl = this.open = !this.open;
                if (this.useSound) {
                    world.method_8396(null, this.currentDoor.method_24515(), ModSounds.DOOR_CLOSE, class_3419.field_15256, 10.0f, 1.0f);
                }
                this.currentDoor = null;
                this.corner1 = this.corner1.method_10079(this.movementDirection, this.numOfBlocks);
                this.corner2 = this.corner2.method_10079(this.movementDirection, this.numOfBlocks);
                this.movementDirection = opposite;
                this.method_5431();
                world.method_8413(this.method_11016(), world.method_8320(this.method_11016()), world.method_8320(this.method_11016()), 3);
            }
        }
        if (world.field_9236 && this.shouldPlaySound()) {
            if (this.doorMoving && this.doorOpeningSoundInstance == null) {
                this.doorOpeningSoundInstance = new DoorOpeningLoopSoundInstance(this.method_11016().method_46558());
                class_310.method_1551().method_1483().method_4873((class_1113)this.doorOpeningSoundInstance);
            } else if (!this.doorMoving && this.doorOpeningSoundInstance != null) {
                this.doorOpeningSoundInstance.startFadeOut();
                this.doorOpeningSoundInstance = null;
            }
        }
    }

    public class_2338 getCorner1() {
        return this.corner1;
    }

    public void setCorner1(class_2338 corner1) {
        this.corner1 = corner1;
    }

    public class_2338 getCorner2() {
        return this.corner2;
    }

    public void setCorner2(class_2338 pos2) {
        this.corner2 = pos2;
    }

    public class_2350 getMovementDirection() {
        return this.movementDirection;
    }

    public void setMovementDirection(class_2350 movementDirection) {
        this.movementDirection = movementDirection;
    }

    public int getNumOfBlocks() {
        return this.numOfBlocks;
    }

    public void setNumOfBlocks(int numOfBlocks) {
        this.numOfBlocks = numOfBlocks;
    }

    public int getSpeed() {
        return this.speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public boolean isSettingSelection() {
        return this.settingSelection;
    }

    public void setSettingSelection(boolean settingSelection) {
        this.settingSelection = settingSelection;
    }

    public boolean shouldShowSelection() {
        return this.showSelection;
    }

    public void setShowSelection(boolean showSelection) {
        this.showSelection = showSelection;
    }

    public boolean shouldPlaySound() {
        return this.useSound;
    }

    public void setPlaySound(boolean useSound) {
        this.useSound = useSound;
    }

    public boolean isDoorMoving() {
        return this.doorMoving;
    }
}

