/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking
 *  net.minecraft.class_2338
 *  net.minecraft.class_2350
 *  net.minecraft.class_2561
 *  net.minecraft.class_332
 *  net.minecraft.class_342
 *  net.minecraft.class_3532
 *  net.minecraft.class_357
 *  net.minecraft.class_364
 *  net.minecraft.class_4185
 *  net.minecraft.class_437
 *  net.minecraft.class_5244
 *  net.minecraft.class_5348
 *  net.minecraft.class_5676
 *  net.minecraft.class_8710
 */
package com.sp.render.gui.screen;

import com.sp.block.entity.custom.PhysicsDoorBlockEntity;
import com.sp.networking.CustomPayloads;
import com.sp.render.SelectionHandler;
import com.sp.util.RenderUtil;
import java.util.Objects;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.class_2338;
import net.minecraft.class_2350;
import net.minecraft.class_2561;
import net.minecraft.class_332;
import net.minecraft.class_342;
import net.minecraft.class_3532;
import net.minecraft.class_357;
import net.minecraft.class_364;
import net.minecraft.class_4185;
import net.minecraft.class_437;
import net.minecraft.class_5244;
import net.minecraft.class_5348;
import net.minecraft.class_5676;
import net.minecraft.class_8710;

public class PhysicsDoorBlockScreen
extends class_437 {
    private final PhysicsDoorBlockEntity physicsDoorBlockEntity;
    private static final class_2561 CORNER1_TEXT = class_2561.method_43470((String)"Corner 1:");
    private static final class_2561 CORNER2_TEXT = class_2561.method_43470((String)"Corner 2:");
    private static final class_2561 DIRECTION_TEXT = class_2561.method_43470((String)"Direction:");
    private static final class_2561 NUM_OF_BLOCKS_TEXT = class_2561.method_43470((String)"Number Of Blocks:");
    private static final class_2561 SHOW_SELECTION_TEXT = class_2561.method_43470((String)"Selection:");
    private static final class_2561 PLAY_SOUND_TEXT = class_2561.method_43470((String)"Play Sound:");
    private static final class_2561 X_TEXT = class_2561.method_43470((String)"X: ");
    private static final class_2561 Y_TEXT = class_2561.method_43470((String)"Y: ");
    private static final class_2561 Z_TEXT = class_2561.method_43470((String)"Z: ");
    private int centerWidth;
    private int centerHeight;
    private class_342 corner1XInput;
    private class_342 corner1YInput;
    private class_342 corner1ZInput;
    private class_342 corner2XInput;
    private class_342 corner2YInput;
    private class_342 corner2ZInput;
    private class_342 numOfBlocksInput;
    private class_2350 prevDirection;
    private boolean prevShowSelection;
    private boolean prevPlaySound;
    private int prevSpeed;

    public PhysicsDoorBlockScreen(PhysicsDoorBlockEntity physicsDoorBlockEntity) {
        super((class_2561)class_2561.method_43470((String)"Physics Door Block"));
        this.physicsDoorBlockEntity = physicsDoorBlockEntity;
    }

    protected void method_25426() {
        this.centerWidth = this.field_22789 / 2;
        this.centerHeight = this.field_22790 / 2;
        this.prevDirection = this.physicsDoorBlockEntity.getMovementDirection();
        this.prevShowSelection = this.physicsDoorBlockEntity.shouldShowSelection();
        this.prevPlaySound = this.physicsDoorBlockEntity.shouldPlaySound();
        this.prevSpeed = this.physicsDoorBlockEntity.getSpeed();
        this.method_37063((class_364)class_4185.method_46430((class_2561)class_2561.method_43470((String)"Save"), button -> this.done()).method_46434(this.centerWidth - 150, this.centerHeight + 60, 80, 20).method_46431());
        this.method_37063((class_364)class_4185.method_46430((class_2561)class_2561.method_43470((String)"Cancel"), button -> this.cancel()).method_46434(this.centerWidth - 50, this.centerHeight + 60, 80, 20).method_46431());
        this.method_37063((class_364)class_4185.method_46430((class_2561)class_2561.method_43470((String)"Select"), button -> this.select()).method_46434(this.centerWidth + 50, this.centerHeight + 60, 80, 20).method_46431());
        this.method_37063((class_364)class_5676.method_32606(direction -> class_2561.method_43470((String)direction.method_10151().toUpperCase())).method_32624((Object[])class_2350.values()).method_32619((Object)this.physicsDoorBlockEntity.getMovementDirection()).method_32616().method_32617(this.centerWidth - 150, this.centerHeight, 50, 20, (class_2561)class_2561.method_43470((String)""), (button, value) -> this.physicsDoorBlockEntity.setMovementDirection((class_2350)value)));
        this.method_37063((class_364)class_5676.method_32613((boolean)this.physicsDoorBlockEntity.shouldShowSelection()).method_32616().method_32617(this.centerWidth + 90, this.centerHeight, 40, 20, SHOW_SELECTION_TEXT, (button, showSelection) -> this.physicsDoorBlockEntity.setShowSelection((boolean)showSelection)));
        this.method_37063((class_364)class_5676.method_32613((boolean)this.physicsDoorBlockEntity.shouldPlaySound()).method_32616().method_32617(this.centerWidth + 90, this.centerHeight + 30, 40, 20, PLAY_SOUND_TEXT, (button, playSound) -> this.physicsDoorBlockEntity.setPlaySound((boolean)playSound)));
        this.method_37063((class_364)new class_357(this.centerWidth - 150, this.centerHeight + 30, 100, 20, class_5244.field_39003, 1.0){
            {
                this.field_22753 = (double)PhysicsDoorBlockScreen.this.physicsDoorBlockEntity.getSpeed() / 20.0;
                this.method_25346();
            }

            protected void method_25346() {
                this.method_25355((class_2561)class_2561.method_43470((String)("Speed: " + PhysicsDoorBlockScreen.this.physicsDoorBlockEntity.getSpeed())));
            }

            protected void method_25344() {
                int speed = class_3532.method_15357((double)class_3532.method_15390((double)1.0, (double)20.0, (double)this.field_22753));
                PhysicsDoorBlockScreen.this.physicsDoorBlockEntity.setSpeed(speed);
            }
        });
        class_2338 corner1 = this.physicsDoorBlockEntity.getCorner1();
        this.corner1XInput = new class_342(this.field_22793, this.centerWidth - 150, this.centerHeight - 80, 80, 20, (class_2561)class_2561.method_43470((String)"Corner 1 X"));
        this.corner1XInput.method_1852(Integer.toString(corner1.method_10263()));
        this.method_25429((class_364)this.corner1XInput);
        this.corner1YInput = new class_342(this.field_22793, this.centerWidth - 40, this.centerHeight - 80, 80, 20, (class_2561)class_2561.method_43470((String)"Corner 1 Y"));
        this.corner1YInput.method_1852(Integer.toString(corner1.method_10264()));
        this.method_25429((class_364)this.corner1YInput);
        this.corner1ZInput = new class_342(this.field_22793, this.centerWidth + 70, this.centerHeight - 80, 80, 20, (class_2561)class_2561.method_43470((String)"Corner 1 Z"));
        this.corner1ZInput.method_1852(Integer.toString(corner1.method_10260()));
        this.method_25429((class_364)this.corner1ZInput);
        class_2338 corner2 = this.physicsDoorBlockEntity.getCorner2();
        this.corner2XInput = new class_342(this.field_22793, this.centerWidth - 150, this.field_22790 / 2 - 40, 80, 20, (class_2561)class_2561.method_43470((String)"Corner 2 X"));
        this.corner2XInput.method_1852(Integer.toString(corner2.method_10263()));
        this.method_25429((class_364)this.corner2XInput);
        this.corner2YInput = new class_342(this.field_22793, this.centerWidth - 40, this.centerHeight - 40, 80, 20, (class_2561)class_2561.method_43470((String)"Corner 2 Y"));
        this.corner2YInput.method_1852(Integer.toString(corner2.method_10264()));
        this.method_25429((class_364)this.corner2YInput);
        this.corner2ZInput = new class_342(this.field_22793, this.centerWidth + 70, this.centerHeight - 40, 80, 20, (class_2561)class_2561.method_43470((String)"Corner 2 Z"));
        this.corner2ZInput.method_1852(Integer.toString(corner2.method_10260()));
        this.method_25429((class_364)this.corner2ZInput);
        this.numOfBlocksInput = new class_342(this.field_22793, this.centerWidth - 80, this.centerHeight, 80, 20, (class_2561)class_2561.method_43470((String)"Number of Blocks"));
        this.numOfBlocksInput.method_1852(Integer.toString(this.physicsDoorBlockEntity.getNumOfBlocks()));
        this.method_25429((class_364)this.numOfBlocksInput);
    }

    public void method_25393() {
    }

    private void updatePhysicsBlock() {
        class_2338 corner1 = new class_2338(this.parseInt(this.corner1XInput.method_1882()), this.parseInt(this.corner1YInput.method_1882()), this.parseInt(this.corner1ZInput.method_1882()));
        class_2338 corner2 = new class_2338(this.parseInt(this.corner2XInput.method_1882()), this.parseInt(this.corner2YInput.method_1882()), this.parseInt(this.corner2ZInput.method_1882()));
        ClientPlayNetworking.send((class_8710)new CustomPayloads.UpdatePhysicsDoorBlock(this.physicsDoorBlockEntity.method_11016(), corner1, corner2, this.physicsDoorBlockEntity.getMovementDirection(), this.parseInt(this.numOfBlocksInput.method_1882()), this.physicsDoorBlockEntity.getSpeed(), this.physicsDoorBlockEntity.shouldShowSelection(), this.physicsDoorBlockEntity.shouldPlaySound()));
    }

    private int parseInt(String integer) {
        try {
            return Integer.parseInt(integer);
        }
        catch (NumberFormatException var3) {
            return 0;
        }
    }

    public void method_25394(class_332 context, int mouseX, int mouseY, float delta) {
        super.method_25394(context, mouseX, mouseY, delta);
        context.method_27534(this.field_22793, this.field_22785, this.centerWidth, 10, 0xFFFFFF);
        Objects.requireNonNull(this.field_22793);
        context.method_27535(this.field_22793, CORNER1_TEXT, this.centerWidth - 150, this.centerHeight - 80 - 9, 0xA0A0A0);
        this.drawLetters(context);
        this.corner1XInput.method_25394(context, mouseX, mouseY, delta);
        this.corner1YInput.method_25394(context, mouseX, mouseY, delta);
        this.corner1ZInput.method_25394(context, mouseX, mouseY, delta);
        Objects.requireNonNull(this.field_22793);
        context.method_27535(this.field_22793, CORNER2_TEXT, this.centerWidth - 150, this.centerHeight - 40 - 9, 0xA0A0A0);
        this.corner2XInput.method_25394(context, mouseX, mouseY, delta);
        this.corner2YInput.method_25394(context, mouseX, mouseY, delta);
        this.corner2ZInput.method_25394(context, mouseX, mouseY, delta);
        Objects.requireNonNull(this.field_22793);
        context.method_27535(this.field_22793, DIRECTION_TEXT, this.centerWidth - 150, this.centerHeight - 9, 0xA0A0A0);
        Objects.requireNonNull(this.field_22793);
        context.method_27535(this.field_22793, NUM_OF_BLOCKS_TEXT, this.centerWidth - 80, this.centerHeight - 9, 0xA0A0A0);
        this.numOfBlocksInput.method_25394(context, mouseX, mouseY, delta);
        Objects.requireNonNull(this.field_22793);
        context.method_27535(this.field_22793, SHOW_SELECTION_TEXT, this.centerWidth + 90, this.centerHeight - 9, 0xA0A0A0);
        Objects.requireNonNull(this.field_22793);
        context.method_27535(this.field_22793, PLAY_SOUND_TEXT, this.centerWidth + 90, this.centerHeight + 30 - 9, 0xA0A0A0);
    }

    private void drawLetters(class_332 context) {
        for (int i = 0; i <= 1; ++i) {
            int n = this.centerWidth - 150 - this.field_22793.method_27525((class_5348)X_TEXT);
            Objects.requireNonNull(this.field_22793);
            context.method_27535(this.field_22793, X_TEXT, n, this.centerHeight - 70 + i * 40 - 9 / 2, RenderUtil.getRgb(255, 0, 0));
            int n2 = this.centerWidth - 40 - this.field_22793.method_27525((class_5348)Y_TEXT);
            Objects.requireNonNull(this.field_22793);
            context.method_27535(this.field_22793, Y_TEXT, n2, this.centerHeight - 70 + i * 40 - 9 / 2, RenderUtil.getRgb(0, 255, 0));
            int n3 = this.centerWidth + 70 - this.field_22793.method_27525((class_5348)Z_TEXT);
            Objects.requireNonNull(this.field_22793);
            context.method_27535(this.field_22793, Z_TEXT, n3, this.centerHeight - 70 + i * 40 - 9 / 2, RenderUtil.getRgb(0, 0, 255));
        }
    }

    public void method_25420(class_332 context, int mouseX, int mouseY, float delta) {
        this.method_52752(context);
    }

    private void done() {
        if (this.field_22787 != null) {
            this.updatePhysicsBlock();
            this.field_22787.method_1507(null);
        }
    }

    private void cancel() {
        if (this.field_22787 != null) {
            this.physicsDoorBlockEntity.setMovementDirection(this.prevDirection);
            this.physicsDoorBlockEntity.setShowSelection(this.prevShowSelection);
            this.physicsDoorBlockEntity.setPlaySound(this.prevPlaySound);
            this.physicsDoorBlockEntity.setSpeed(this.prevSpeed);
            this.field_22787.method_1507(null);
        }
    }

    private void select() {
        if (this.field_22787 == null) {
            return;
        }
        SelectionHandler.startSelection((corner1, corner2) -> {
            this.corner1XInput.method_1852(Integer.toString(corner1.method_10263()));
            this.corner1YInput.method_1852(Integer.toString(corner1.method_10264()));
            this.corner1ZInput.method_1852(Integer.toString(corner1.method_10260()));
            this.corner2XInput.method_1852(Integer.toString(corner2.method_10263()));
            this.corner2YInput.method_1852(Integer.toString(corner2.method_10264()));
            this.corner2ZInput.method_1852(Integer.toString(corner2.method_10260()));
            this.updatePhysicsBlock();
            this.physicsDoorBlockEntity.setSettingSelection(false);
        }, () -> this.physicsDoorBlockEntity.setSettingSelection(false));
        this.physicsDoorBlockEntity.setSettingSelection(true);
        this.field_22787.method_1507(null);
    }

    public void method_25419() {
        super.method_25419();
        this.cancel();
    }

    public boolean method_25421() {
        return false;
    }
}

