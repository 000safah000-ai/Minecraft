/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1657
 *  net.minecraft.class_1937
 *  net.minecraft.class_243
 */
package com.sp.world.playzone;

import com.sp.cca.InitializeComponents;
import com.sp.cca.custom.world.WorldDestructionEventsComponent;
import com.sp.networking.ServerPacketManager;
import com.sp.world.playzone.PlayZone;
import java.util.Vector;
import net.minecraft.class_1657;
import net.minecraft.class_1937;
import net.minecraft.class_243;

public class PlayZoneManager {
    private static final Vector<PlayZone> activePlayZones = new Vector();

    public static void addPlayZone(class_1937 world, PlayZone playZone) {
        if (activePlayZones.stream().noneMatch(playZone1 -> playZone1.getId() == playZone.getId())) {
            activePlayZones.add(playZone);
            if (!world.field_9236) {
                for (class_1657 player : world.method_18456()) {
                    ServerPacketManager.sendUpdatePlayZonePacket(player, playZone, false);
                }
                ((WorldDestructionEventsComponent)InitializeComponents.EVENTS.get((Object)world)).sync();
            }
        }
    }

    public static int removeAllPlayZonesAtPos(class_243 pos, class_1937 world) {
        int playZonesRemoved = 0;
        for (PlayZone playZone : PlayZoneManager.getActivePlayZones()) {
            if (!playZone.isPositionInsideZone(pos)) continue;
            activePlayZones.remove(playZone);
            ++playZonesRemoved;
            for (class_1657 player : world.method_18456()) {
                ServerPacketManager.sendUpdatePlayZonePacket(player, playZone, true);
            }
        }
        ((WorldDestructionEventsComponent)InitializeComponents.EVENTS.get((Object)world)).sync();
        return playZonesRemoved;
    }

    public static void removePlayZone(int id) {
        activePlayZones.removeIf(playZone -> playZone.getId() == id);
    }

    public static void clearAllPlayZones() {
        activePlayZones.clear();
    }

    public static boolean isInsideAPlayZone(class_243 pos) {
        return activePlayZones.isEmpty() || activePlayZones.stream().anyMatch(playZone -> playZone.isPositionInsideZone(pos));
    }

    public static Vector<PlayZone> getActivePlayZones() {
        return (Vector)activePlayZones.clone();
    }
}

