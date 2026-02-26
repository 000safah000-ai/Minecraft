/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1657
 *  net.minecraft.class_1937
 *  net.minecraft.class_3218
 */
package com.sp.destruction.server.custom;

import com.sp.cca.InitializeComponents;
import com.sp.cca.custom.world.WorldDestructionEventsComponent;
import com.sp.destruction.DestructionType;
import com.sp.destruction.server.ServerDestructionEvent;
import com.sp.networking.ServerPacketManager;
import com.sp.util.keyframes.Keyframe;
import com.sp.util.keyframes.KeyframeAnimation;
import com.sp.world.spinningblockexplosion.custom.DirectionalSBE;
import net.minecraft.class_1657;
import net.minecraft.class_1937;
import net.minecraft.class_3218;

public class SupernovaDestructionServer
extends ServerDestructionEvent {
    public SupernovaDestructionServer() {
        super(DestructionType.SUPERNOVA, 2930);
    }

    @Override
    protected KeyframeAnimation initAnimations(class_1937 world) {
        WorldDestructionEventsComponent component = (WorldDestructionEventsComponent)InitializeComponents.EVENTS.get((Object)world);
        return new KeyframeAnimation.KeyframeAnimationBuilder(this.duration, new Keyframe(0.0), new Keyframe(2880.0 / (double)this.duration, () -> {
            DirectionalSBE explosion = new DirectionalSBE(50, 80, -90.0f, 0.2f, component.getDestructionEventPosition());
            explosion.beginExplosion((class_3218)world);
        })).endAction(() -> {
            for (class_1657 player : world.method_18456()) {
                ServerPacketManager.sendWaitingRoomPacket(player, true);
            }
            this.setActive(false, -1L);
            this.resetEvent();
        }).build();
    }
}

