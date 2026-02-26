/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking
 *  net.minecraft.class_1657
 *  net.minecraft.class_238
 *  net.minecraft.class_243
 *  net.minecraft.class_3222
 *  net.minecraft.class_3414
 *  net.minecraft.class_8710
 */
package com.sp.networking;

import com.sp.networking.CustomPayloads;
import com.sp.render.ShaderType;
import com.sp.world.playzone.PlayZone;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.class_1657;
import net.minecraft.class_238;
import net.minecraft.class_243;
import net.minecraft.class_3222;
import net.minecraft.class_3414;
import net.minecraft.class_8710;

public class ServerPacketManager {
    public static void sendPointSBEPacket(class_1657 player, class_243 position, int radius) {
        ServerPlayNetworking.send((class_3222)((class_3222)player), (class_8710)new CustomPayloads.SBEPayload(position.method_46409(), radius));
    }

    public static void sendBraamPacket(class_1657 player, class_3414 soundEvent) {
        ServerPlayNetworking.send((class_3222)((class_3222)player), (class_8710)new CustomPayloads.BraamPayload(soundEvent));
    }

    public static void sendUpdatePlayZonePacket(class_1657 player, PlayZone playZone, boolean remove) {
        class_238 playZoneBounds = playZone.getBoundingBox();
        ServerPlayNetworking.send((class_3222)((class_3222)player), (class_8710)new CustomPayloads.UpdatePlayZonePayload(playZoneBounds.field_1323, playZoneBounds.field_1320, playZoneBounds.field_1322, playZoneBounds.field_1325, playZoneBounds.field_1321, playZoneBounds.field_1324, playZone.getId(), remove));
    }

    public static void sendWaitingRoomPacket(class_1657 player, boolean setInWaitingRoom) {
        ServerPlayNetworking.send((class_3222)((class_3222)player), (class_8710)new CustomPayloads.WaitingRoomPacketPayload(setInWaitingRoom));
    }

    public static void sendShaderChangePacket(class_1657 player, ShaderType shaderType) {
        ServerPlayNetworking.send((class_3222)((class_3222)player), (class_8710)new CustomPayloads.ShaderChangePacketPayload(shaderType.method_15434()));
    }

    public static void sendLavaSpewPacket(class_1657 player, class_243 pos) {
        ServerPlayNetworking.send((class_3222)((class_3222)player), (class_8710)new CustomPayloads.LavaSpewPacketPayload(pos.method_46409()));
    }
}

