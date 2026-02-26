/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  foundry.veil.api.client.util.Easing
 *  net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking$Context
 *  net.minecraft.class_1109
 *  net.minecraft.class_1113
 *  net.minecraft.class_3414
 */
package com.sp.networking.S2C;

import com.sp.networking.CustomPayloads;
import com.sp.render.camerashake.CameraShakeManager;
import com.sp.render.camerashake.custom.CameraShakeInstance;
import foundry.veil.api.client.util.Easing;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.class_1109;
import net.minecraft.class_1113;
import net.minecraft.class_3414;

public class BraamPacket {
    public static void receive(CustomPayloads.BraamPayload payload, ClientPlayNetworking.Context context) {
        context.client().execute(() -> {
            context.client().method_1483().method_4873((class_1113)class_1109.method_24877((class_3414)payload.soundEvent(), (float)1.0f, (float)1.0f));
            CameraShakeInstance cameraShake = new CameraShakeInstance(1.0f, 0.0f, 60, Easing.EASE_IN_EXPO);
            CameraShakeManager.addCameraShake(cameraShake);
        });
    }
}

