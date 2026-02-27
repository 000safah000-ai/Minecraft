package com.jujutsushenanigans.networking.S2C;

import com.jujutsushenanigans.JujutsuShenanigans;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;

public record InfinitySyncS2CPacket(int entityId, boolean isActive) implements CustomPayload {
    public static final CustomPayload.Id<InfinitySyncS2CPacket> ID = new CustomPayload.Id<>(JujutsuShenanigans.id("infinity_sync"));
    public static final PacketCodec<RegistryByteBuf, InfinitySyncS2CPacket> CODEC = PacketCodec.tuple(
            PacketCodecs.INTEGER, InfinitySyncS2CPacket::entityId,
            PacketCodecs.BOOL, InfinitySyncS2CPacket::isActive,
            InfinitySyncS2CPacket::new
    );

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
