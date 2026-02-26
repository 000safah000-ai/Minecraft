/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking$Context
 *  net.minecraft.class_243
 */
package com.sp.networking.S2C;

import com.sp.cca.InitializeComponents;
import com.sp.cca.custom.world.WorldDestructionEventsComponent;
import com.sp.config.DestroyingMinecraftConfig;
import com.sp.destruction.DestructionEvent;
import com.sp.destruction.DestructionType;
import com.sp.destruction.client.ClientDestructionEvent;
import com.sp.networking.CustomPayloads;
import com.sp.render.ShaderType;
import com.sp.render.gui.hud.DestructionTitleRenderCallback;
import java.util.Optional;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.class_243;

public class InvokeDestructionPacket {
    public static void receive(CustomPayloads.DestructionPayload payload, ClientPlayNetworking.Context context) {
        context.client().execute(() -> {
            WorldDestructionEventsComponent worldComponent = (WorldDestructionEventsComponent)InitializeComponents.EVENTS.get((Object)context.client().field_1687);
            if (payload.name().equals("reset")) {
                for (DestructionEvent event : ClientDestructionEvent.getAllClientInstances()) {
                    event.setActive(false, -1L);
                    event.resetEvent();
                }
                return;
            }
            Optional<DestructionType> type = DestructionType.getFromName(payload.name());
            type.ifPresent(destructionType -> {
                for (DestructionEvent event : ClientDestructionEvent.getAllClientInstances()) {
                    if (!event.getDestructionType().equals(destructionType)) continue;
                    worldComponent.setAndStartCurrentDestructionEvent(event, payload.startTime());
                    worldComponent.setDestructionEventPosition(new class_243(payload.position()));
                    ShaderType shaderType = ShaderType.getFromDestructionType(destructionType);
                    if (shaderType == DestroyingMinecraftConfig.shaderType) break;
                    DestroyingMinecraftConfig.shaderType = shaderType;
                    DestroyingMinecraftConfig.write((String)"destroying-minecraft");
                    break;
                }
                DestructionTitleRenderCallback.setDestructionTitle(destructionType);
            });
        });
    }
}

