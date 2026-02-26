/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1657
 *  net.minecraft.class_1937
 *  net.minecraft.class_243
 *  net.minecraft.class_3419
 *  net.minecraft.class_5819
 */
package com.sp.destruction.server.custom;

import com.sp.cca.InitializeComponents;
import com.sp.cca.custom.world.WorldDestructionEventsComponent;
import com.sp.destruction.DestructionType;
import com.sp.destruction.server.ServerDestructionEvent;
import com.sp.networking.ServerPacketManager;
import com.sp.sounds.ModSounds;
import com.sp.util.MathUtil;
import com.sp.util.keyframes.Keyframe;
import com.sp.util.keyframes.KeyframeAnimation;
import java.util.List;
import net.minecraft.class_1657;
import net.minecraft.class_1937;
import net.minecraft.class_243;
import net.minecraft.class_3419;
import net.minecraft.class_5819;

public class LaserDestructionServer
extends ServerDestructionEvent {
    private static final class_5819 random = class_5819.method_43047();
    private static final float radius = 23.0f;
    private static int lavaSpewDelay = 200;
    public static float laserLength;
    public static float crackingTime;

    public LaserDestructionServer() {
        super(DestructionType.ORBITAL_LASER, 2500);
    }

    @Override
    public void resetEvent() {
        crackingTime = 0.0f;
        laserLength = 0.0f;
        super.resetEvent();
    }

    @Override
    protected KeyframeAnimation initAnimations(class_1937 world) {
        WorldDestructionEventsComponent component = (WorldDestructionEventsComponent)InitializeComponents.EVENTS.get((Object)world);
        return new KeyframeAnimation.KeyframeAnimationBuilder(this.duration, new Keyframe(0.0), new Keyframe(484.0 / (double)this.duration, () -> {
            laserLength = 1.0f;
        }), new Keyframe(700.0 / (double)this.duration, (globalTime, localTime) -> {
            crackingTime = (float)localTime;
            if (lavaSpewDelay <= 0) {
                float maxRadius = crackingTime * 23.0f;
                class_243 offset = new class_243((double)MathUtil.nextBetween(-maxRadius, maxRadius), 0.0, (double)MathUtil.nextBetween(-maxRadius, maxRadius));
                offset = offset.method_1019(component.getDestructionEventPosition());
                for (class_1657 player : world.method_18456()) {
                    ServerPacketManager.sendLavaSpewPacket(player, offset);
                }
                world.method_43128(null, offset.field_1352, offset.field_1351, offset.field_1350, ModSounds.LAVA_SPEW, class_3419.field_15256, 10.0f, MathUtil.nextBetween(0.8f, 1.2f));
                lavaSpewDelay = random.method_39332(30, 100);
            } else {
                --lavaSpewDelay;
            }
            int playerCount = 0;
            List playerList = world.method_18456();
            if (playerList.size() > 1) {
                for (class_1657 player : world.method_18456()) {
                    if (player.method_7337() || player.method_7325()) continue;
                    ++playerCount;
                }
                if (playerCount <= 1) {
                    this.skipKeyframe();
                }
            }
        }), new Keyframe(2100.0 / (double)this.duration)).endAction(() -> {
            for (class_1657 player : world.method_18456()) {
                ServerPacketManager.sendWaitingRoomPacket(player, true);
            }
        }).build();
    }
}

