/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.fabricmc.fabric.api.event.Event
 *  net.fabricmc.fabric.api.event.EventFactory
 *  net.minecraft.class_310
 */
package com.sp.networking.callbacks;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.class_310;

public class ClientConnectionEvents {
    public static final Event<Disconnect> DISCONNECT = EventFactory.createArrayBacked(Disconnect.class, listeners -> client -> {
        for (Disconnect listener : listeners) {
            listener.onLoginDisconnect(client);
        }
    });

    @FunctionalInterface
    public static interface Disconnect {
        public void onLoginDisconnect(class_310 var1);
    }
}

