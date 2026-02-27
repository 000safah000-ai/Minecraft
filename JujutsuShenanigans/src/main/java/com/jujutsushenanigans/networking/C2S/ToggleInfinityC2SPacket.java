package com.jujutsushenanigans.networking.C2S;

import com.jujutsushenanigans.JujutsuShenanigans;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;

public record ToggleInfinityC2SPacket() implements CustomPayload {
    public static final CustomPayload.Id<ToggleInfinityC2SPacket> ID = new CustomPayload.Id<>(JujutsuShenanigans.id("toggle_infinity"));
    public static final PacketCodec<RegistryByteBuf, ToggleInfinityC2SPacket> CODEC = PacketCodec.unit(new ToggleInfinityC2SPacket());

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
