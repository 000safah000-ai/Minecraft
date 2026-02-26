/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  foundry.veil.api.client.util.Easing
 *  net.minecraft.class_1268
 *  net.minecraft.class_1271
 *  net.minecraft.class_1657
 *  net.minecraft.class_1792
 *  net.minecraft.class_1792$class_1793
 *  net.minecraft.class_1799
 *  net.minecraft.class_1937
 */
package com.sp.item.custom;

import com.sp.render.camerashake.CameraShakeManager;
import com.sp.render.camerashake.custom.CameraShakeInstance;
import foundry.veil.api.client.util.Easing;
import net.minecraft.class_1268;
import net.minecraft.class_1271;
import net.minecraft.class_1657;
import net.minecraft.class_1792;
import net.minecraft.class_1799;
import net.minecraft.class_1937;

public class CameraShakeStick
extends class_1792 {
    public CameraShakeStick(class_1792.class_1793 settings) {
        super(settings);
    }

    public class_1271<class_1799> method_7836(class_1937 world, class_1657 user, class_1268 hand) {
        if (world.field_9236) {
            CameraShakeInstance cameraShakeInstance = new CameraShakeInstance(1.0f, 0.0f, 100, Easing.LINEAR);
            CameraShakeManager.addCameraShake(cameraShakeInstance);
        }
        return super.method_7836(world, user, hand);
    }
}

