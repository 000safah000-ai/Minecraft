/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents
 *  net.minecraft.class_1937
 */
package com.sp.util.tickinstances.client;

import com.sp.util.tickinstances.client.EndClientTickInstances;
import com.sp.util.tickinstances.client.EndClientWorldTickInstances;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.class_1937;

@Environment(value=EnvType.CLIENT)
public abstract class ClientTickInstances {
    public static void registerAllClientTickInstances() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            for (EndClientTickInstances instances : EndClientTickInstances.getInstances()) {
                instances.tickClient(client);
            }
        });
        ClientTickEvents.END_WORLD_TICK.register(world -> {
            for (EndClientWorldTickInstances instances : EndClientWorldTickInstances.getInstances()) {
                instances.tickClientWorld((class_1937)world);
            }
        });
    }
}

