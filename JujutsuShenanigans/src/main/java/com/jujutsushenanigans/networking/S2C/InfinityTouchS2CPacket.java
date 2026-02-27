package com.jujutsushenanigans.networking.S2C;

import com.jujutsushenanigans.JujutsuShenanigans;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.math.Vec3d;

public record InfinityTouchS2CPacket(Vec3d position) implements CustomPayload {
    public static final CustomPayload.Id<InfinityTouchS2CPacket> ID = new CustomPayload.Id<>(JujutsuShenanigans.id("infinity_touch_sync"));

    // Custom codec for Vec3d - ValueFirstEncoder requires (value, buf) order
    public static final PacketCodec<RegistryByteBuf, InfinityTouchS2CPacket> CUSTOM_CODEC = PacketCodec.of(
        (InfinityTouchS2CPacket value, RegistryByteBuf buf) -> {
            buf.writeDouble(value.position().x);
            buf.writeDouble(value.position().y);
            buf.writeDouble(value.position().z);
        },
        (RegistryByteBuf buf) -> new InfinityTouchS2CPacket(new Vec3d(buf.readDouble(), buf.readDouble(), buf.readDouble()))
    );

    @Override
    public CustomPayload.Id<? extends CustomPayload> getId() {
        return ID;
    }
}
