/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  foundry.veil.api.client.util.Easing
 *  net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking$Context
 *  net.minecraft.class_243
 *  net.minecraft.class_2960
 */
package com.sp.networking.S2C;

import com.sp.DestroyingMinecraft;
import com.sp.networking.CustomPayloads;
import com.sp.render.camerashake.CameraShakeManager;
import com.sp.render.camerashake.custom.PointCameraShake;
import com.sp.util.MathUtil;
import foundry.veil.api.client.util.Easing;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.class_243;
import net.minecraft.class_2960;

public class PointSBEPacket {
    private static final class_2960 SMOKE = DestroyingMinecraft.idOf("smoke");

    public static void receive(CustomPayloads.SBEPayload payload, ClientPlayNetworking.Context context) {
        context.client().execute(() -> {
            class_243 pos = MathUtil.toVec3d(payload.position());
            PointCameraShake cameraShake = new PointCameraShake(pos, (float)payload.radius(), 40, Easing.LINEAR);
            CameraShakeManager.addCameraShake(cameraShake);
        });
    }
}

