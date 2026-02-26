/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking$Context
 *  net.minecraft.class_1937
 *  net.minecraft.class_238
 */
package com.sp.networking.S2C;

import com.sp.networking.CustomPayloads;
import com.sp.world.playzone.PlayZone;
import com.sp.world.playzone.PlayZoneManager;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.class_1937;
import net.minecraft.class_238;

public class UpdatePlayZonePacket {
    public static void receive(CustomPayloads.UpdatePlayZonePayload payload, ClientPlayNetworking.Context context) {
        context.client().execute(() -> {
            if (payload.remove()) {
                PlayZoneManager.removePlayZone(payload.playZoneID());
            } else {
                PlayZone playZone = new PlayZone(new class_238(payload.minX(), payload.minY(), payload.minZ(), payload.maxX(), payload.maxY(), payload.maxZ()), payload.playZoneID());
                PlayZoneManager.addPlayZone((class_1937)context.client().field_1687, playZone);
            }
        });
    }
}

