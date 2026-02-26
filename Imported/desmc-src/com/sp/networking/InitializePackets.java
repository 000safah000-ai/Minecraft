/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking
 *  net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry
 *  net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking
 */
package com.sp.networking;

import com.sp.networking.C2S.UpdatePhysicsDoorPacket;
import com.sp.networking.CustomPayloads;
import com.sp.networking.S2C.BraamPacket;
import com.sp.networking.S2C.InvokeDestructionPacket;
import com.sp.networking.S2C.LavaSpewPacket;
import com.sp.networking.S2C.PointSBEPacket;
import com.sp.networking.S2C.ShaderChangePacket;
import com.sp.networking.S2C.UpdatePlayZonePacket;
import com.sp.networking.S2C.WaitingRoomPacket;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;

public class InitializePackets {
    public static void registerServerNetworking() {
        PayloadTypeRegistry.playC2S().register(CustomPayloads.UpdatePhysicsDoorBlock.ID, CustomPayloads.UpdatePhysicsDoorBlock.CODEC);
        ServerPlayNetworking.registerGlobalReceiver(CustomPayloads.UpdatePhysicsDoorBlock.ID, UpdatePhysicsDoorPacket::recieve);
        PayloadTypeRegistry.playS2C().register(CustomPayloads.DestructionPayload.ID, CustomPayloads.DestructionPayload.CODEC);
        PayloadTypeRegistry.playS2C().register(CustomPayloads.SBEPayload.ID, CustomPayloads.SBEPayload.CODEC);
        PayloadTypeRegistry.playS2C().register(CustomPayloads.BraamPayload.ID, CustomPayloads.BraamPayload.CODEC);
        PayloadTypeRegistry.playS2C().register(CustomPayloads.UpdatePlayZonePayload.ID, CustomPayloads.UpdatePlayZonePayload.CODEC);
        PayloadTypeRegistry.playS2C().register(CustomPayloads.WaitingRoomPacketPayload.ID, CustomPayloads.WaitingRoomPacketPayload.CODEC);
        PayloadTypeRegistry.playS2C().register(CustomPayloads.ShaderChangePacketPayload.ID, CustomPayloads.ShaderChangePacketPayload.CODEC);
        PayloadTypeRegistry.playS2C().register(CustomPayloads.LavaSpewPacketPayload.ID, CustomPayloads.LavaSpewPacketPayload.CODEC);
    }

    public static void registerClientNetworking() {
        ClientPlayNetworking.registerGlobalReceiver(CustomPayloads.DestructionPayload.ID, InvokeDestructionPacket::receive);
        ClientPlayNetworking.registerGlobalReceiver(CustomPayloads.SBEPayload.ID, PointSBEPacket::receive);
        ClientPlayNetworking.registerGlobalReceiver(CustomPayloads.BraamPayload.ID, BraamPacket::receive);
        ClientPlayNetworking.registerGlobalReceiver(CustomPayloads.UpdatePlayZonePayload.ID, UpdatePlayZonePacket::receive);
        ClientPlayNetworking.registerGlobalReceiver(CustomPayloads.WaitingRoomPacketPayload.ID, WaitingRoomPacket::receive);
        ClientPlayNetworking.registerGlobalReceiver(CustomPayloads.ShaderChangePacketPayload.ID, ShaderChangePacket::receive);
        ClientPlayNetworking.registerGlobalReceiver(CustomPayloads.LavaSpewPacketPayload.ID, LavaSpewPacket::receive);
    }
}

