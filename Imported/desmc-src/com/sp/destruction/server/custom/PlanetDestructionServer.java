/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1297
 *  net.minecraft.class_1657
 *  net.minecraft.class_1937
 *  net.minecraft.class_243
 *  net.minecraft.class_3218
 *  net.minecraft.class_5819
 */
package com.sp.destruction.server.custom;

import com.sp.cca.InitializeComponents;
import com.sp.cca.custom.world.WorldDestructionEventsComponent;
import com.sp.destruction.DestructionType;
import com.sp.destruction.server.ServerDestructionEvent;
import com.sp.entity.ModEntities;
import com.sp.entity.custom.MeteorEntity;
import com.sp.networking.ServerPacketManager;
import com.sp.util.keyframes.Keyframe;
import com.sp.util.keyframes.KeyframeAnimation;
import com.sp.world.spinningblockexplosion.custom.DirectionalSBE;
import java.util.List;
import net.minecraft.class_1297;
import net.minecraft.class_1657;
import net.minecraft.class_1937;
import net.minecraft.class_243;
import net.minecraft.class_3218;
import net.minecraft.class_5819;

public class PlanetDestructionServer
extends ServerDestructionEvent {
    private int meteorCooldown;
    private int trackingMeteorCooldown = 300;
    private final class_5819 random = class_5819.method_43047();

    public PlanetDestructionServer() {
        super(DestructionType.PLANET, 2400);
    }

    @Override
    protected KeyframeAnimation initAnimations(class_1937 world) {
        WorldDestructionEventsComponent component = (WorldDestructionEventsComponent)InitializeComponents.EVENTS.get((Object)world);
        return new KeyframeAnimation.KeyframeAnimationBuilder(this.duration, new Keyframe(0.0), new Keyframe(320.0 / (double)this.duration, (globalTime, localTime) -> {
            --this.trackingMeteorCooldown;
            if (this.meteorCooldown-- >= 0) {
                return;
            }
            double randX = (this.random.method_43058() * 2.0 - 1.0) * 100.0;
            double randZ = (this.random.method_43058() * 2.0 - 1.0) * 100.0;
            MeteorEntity meteor = (MeteorEntity)ModEntities.METEOR_ENTITY.method_5883(world);
            if (meteor != null) {
                class_243 center = component.getDestructionEventPosition();
                meteor.method_33574(center.method_1031(randX + 120.0, 120.0, randZ));
                world.method_8649((class_1297)meteor);
            }
            if (this.trackingMeteorCooldown <= 0) {
                List playerList = world.method_18456();
                for (class_1657 player : playerList) {
                    MeteorEntity trackingMeteor;
                    if (!player.method_33190() || (trackingMeteor = (MeteorEntity)ModEntities.METEOR_ENTITY.method_5883(world)) == null) continue;
                    trackingMeteor.method_33574(player.method_19538().method_1031(120.0, 120.0, 0.0));
                    world.method_8649((class_1297)trackingMeteor);
                }
                this.trackingMeteorCooldown = this.random.method_39332(200, 300);
            }
            this.meteorCooldown = this.random.method_39332(2, 5);
        }), new Keyframe(1800.0 / (double)this.duration), new Keyframe(2350.0 / (double)this.duration, () -> {
            class_243 averagePlayerPos = class_243.field_1353;
            List players = world.method_18456();
            int numOfTargetedPlayers = 0;
            for (class_1657 player : players) {
                if (player.method_7337() || player.method_7325()) continue;
                averagePlayerPos = averagePlayerPos.method_1019(player.method_19538());
                ++numOfTargetedPlayers;
            }
            averagePlayerPos = averagePlayerPos.method_1021((double)(1.0f / (float)numOfTargetedPlayers));
            DirectionalSBE directionalSBE = new DirectionalSBE(50, 50, -90.0f, 0.2f, new class_243(averagePlayerPos.field_1352, 67.0, averagePlayerPos.field_1350));
            directionalSBE.beginExplosion((class_3218)world);
        })).endAction(() -> {
            for (class_1657 player : world.method_18456()) {
                ServerPacketManager.sendWaitingRoomPacket(player, true);
            }
        }).build();
    }
}

