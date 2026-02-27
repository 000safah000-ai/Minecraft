package com.jujutsushenanigans.networking;

import com.jujutsushenanigans.JujutsuShenanigans;
import com.jujutsushenanigans.networking.C2S.ToggleInfinityC2SPacket;
import com.jujutsushenanigans.networking.S2C.InfinitySyncS2CPacket;
import com.jujutsushenanigans.networking.S2C.InfinityTouchS2CPacket;
import com.jujutsushenanigans.networking.S2C.SixEyesSyncS2CPacket;
import com.jujutsushenanigans.InfinityState;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.network.ServerPlayerEntity;

public class ModPackets {
    public static void registerServerPackets() {
        JujutsuShenanigans.LOGGER.debug("Registering server packets...");

        // C2S
        PayloadTypeRegistry.playC2S().register(ToggleInfinityC2SPacket.ID, ToggleInfinityC2SPacket.CODEC);
        ServerPlayNetworking.registerGlobalReceiver(ToggleInfinityC2SPacket.ID, (payload, context) -> {
            ServerPlayerEntity player = context.player();
            context.server().execute(() -> {
                if (player instanceof InfinityState state) {
                    boolean newState = !state.isInfinityActive();
                    state.setInfinityActive(newState);
                    
                    // Sync to all tracking clients
                    InfinitySyncS2CPacket syncPacket = new InfinitySyncS2CPacket(player.getId(), newState);
                    for (ServerPlayerEntity tracker : player.server.getPlayerManager().getPlayerList()) {
                        if (tracker.getWorld() == player.getWorld() && tracker.squaredDistanceTo(player) < 10000) {
                            ServerPlayNetworking.send(tracker, syncPacket);
                        }
                    }
                }
            });
        });

        // S2C
        PayloadTypeRegistry.playS2C().register(SixEyesSyncS2CPacket.ID, SixEyesSyncS2CPacket.CODEC);
        PayloadTypeRegistry.playS2C().register(InfinitySyncS2CPacket.ID, InfinitySyncS2CPacket.CODEC);
        PayloadTypeRegistry.playS2C().register(InfinityTouchS2CPacket.ID, InfinityTouchS2CPacket.CUSTOM_CODEC);
    }
}
