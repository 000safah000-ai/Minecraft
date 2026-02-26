/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking$Context
 *  net.minecraft.class_310
 */
package com.sp.networking.S2C;

import com.sp.cca.InitializeComponents;
import com.sp.cca.custom.entity.PlayerComponent;
import com.sp.destruction.DestructionEvent;
import com.sp.destruction.client.ClientDestructionEvent;
import com.sp.networking.CustomPayloads;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.class_310;

public class WaitingRoomPacket {
    public static void receive(CustomPayloads.WaitingRoomPacketPayload payload, ClientPlayNetworking.Context context) {
        context.client().execute(() -> {
            if (payload.setInWaitingRoom()) {
                context.client().method_1483().method_4882();
            } else {
                context.client().method_1483().method_38566();
            }
            PlayerComponent component = (PlayerComponent)InitializeComponents.PLAYERS.get((Object)context.player());
            component.setInWaitingRoom(true);
            class_310.method_1551().field_1690.field_1842 = payload.setInWaitingRoom();
            for (DestructionEvent event : ClientDestructionEvent.getAllClientInstances()) {
                event.setActive(false, -1L);
                event.resetEvent();
            }
        });
    }
}

