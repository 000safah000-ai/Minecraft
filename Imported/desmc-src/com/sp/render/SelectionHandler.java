/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.systems.RenderSystem
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.minecraft.class_1268
 *  net.minecraft.class_1937
 *  net.minecraft.class_2338
 *  net.minecraft.class_239$class_240
 *  net.minecraft.class_243
 *  net.minecraft.class_310
 *  net.minecraft.class_3414
 *  net.minecraft.class_3417
 *  net.minecraft.class_3965
 *  net.minecraft.class_4184
 *  net.minecraft.class_4587
 *  net.minecraft.class_4597
 *  net.minecraft.class_9779
 */
package com.sp.render;

import com.mojang.blaze3d.systems.RenderSystem;
import com.sp.util.RenderUtil;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1268;
import net.minecraft.class_1937;
import net.minecraft.class_2338;
import net.minecraft.class_239;
import net.minecraft.class_243;
import net.minecraft.class_310;
import net.minecraft.class_3414;
import net.minecraft.class_3417;
import net.minecraft.class_3965;
import net.minecraft.class_4184;
import net.minecraft.class_4587;
import net.minecraft.class_4597;
import net.minecraft.class_9779;

@Environment(value=EnvType.CLIENT)
public class SelectionHandler {
    private static boolean renderingSelection;
    private static SelectionFunction selectionFunction;
    private static Runnable cancelFunction;
    private static class_2338 corner1;
    private static class_2338 corner2;
    private static class_3965 targetBlock;
    private static int delayTime;

    public static void startSelection(SelectionFunction function, Runnable cancelFunction1) {
        renderingSelection = true;
        selectionFunction = function;
        cancelFunction = cancelFunction1;
    }

    public static void tickClientWorld(class_1937 world) {
        class_310 client = class_310.method_1551();
        if (!renderingSelection || client.field_1724 == null) {
            return;
        }
        client.field_1773.method_35769(false);
        if (delayTime > 0) {
            --delayTime;
            return;
        }
        if (client.field_1690.field_1886.method_1434()) {
            client.field_1724.method_5783((class_3414)class_3417.field_18311.comp_349(), 1.0f, 0.1f);
            SelectionHandler.end();
            client.field_1773.method_35769(true);
            cancelFunction.run();
            return;
        }
        if (corner1 == null && targetBlock != null && client.field_1690.field_1904.method_1434()) {
            client.field_1724.method_6104(class_1268.field_5808);
            corner1 = targetBlock.method_17777();
            client.field_1724.method_5783((class_3414)class_3417.field_14793.comp_349(), 1.0f, 0.1f);
            delayTime = 10;
            return;
        }
        if (corner1 != null && corner2 == null && client.field_1690.field_1904.method_1434()) {
            client.field_1724.method_6104(class_1268.field_5808);
            corner2 = targetBlock.method_17777();
            client.field_1724.method_5783((class_3414)class_3417.field_14793.comp_349(), 1.0f, 1.0f);
        }
        if (corner1 != null && corner2 != null) {
            SelectionHandler.executeSelectionFunction();
            SelectionHandler.end();
            client.field_1773.method_35769(true);
        }
    }

    public static void renderSelection(class_4587 matrices, class_4597 vertexConsumers, class_9779 renderTickCounter, class_4184 camera) {
        class_310 client = class_310.method_1551();
        if (!renderingSelection || client.field_1724 == null) {
            return;
        }
        targetBlock = (class_3965)client.field_1724.method_5745(50.0, renderTickCounter.method_60637(true), false);
        if (targetBlock.method_17783() != class_239.class_240.field_1332) {
            return;
        }
        int alpha = (int)((Math.sin(RenderSystem.getShaderGameTime() * 2000.0f) * 0.5 + 0.5) * 100.0) + 50;
        if (corner1 == null) {
            class_243 blockPos = targetBlock.method_17777().method_46558();
            RenderUtil.drawBox(matrices, vertexConsumers, blockPos.method_1020(camera.method_19326()), 1.0, 100, 255, 100, alpha, true, false);
        } else if (corner2 == null) {
            RenderUtil.drawBlocksFromCorners(matrices, vertexConsumers, camera, corner1, targetBlock.method_17777(), 100, 255, 100, alpha, true, false);
        }
    }

    private static void executeSelectionFunction() {
        selectionFunction.runFunction(corner1, corner2);
    }

    private static void end() {
        corner1 = null;
        corner2 = null;
        renderingSelection = false;
        selectionFunction = null;
    }

    public boolean isRenderingSelection() {
        return renderingSelection;
    }

    static {
        delayTime = 10;
    }

    @FunctionalInterface
    public static interface SelectionFunction {
        public void runFunction(class_2338 var1, class_2338 var2);
    }
}

