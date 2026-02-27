package com.jujutsushenanigans.networking.S2C;

import com.jujutsushenanigans.JujutsuShenanigans;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;

public record SixEyesSyncS2CPacket(boolean isActive) implements CustomPayload {
    public static final CustomPayload.Id<SixEyesSyncS2CPacket> ID = new CustomPayload.Id<>(JujutsuShenanigans.id("six_eyes_sync"));
    public static final PacketCodec<RegistryByteBuf, SixEyesSyncS2CPacket> CODEC = PacketCodec.tuple(
            PacketCodecs.BOOL, SixEyesSyncS2CPacket::isActive,
            SixEyesSyncS2CPacket::new
    );

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
