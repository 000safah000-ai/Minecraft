/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.doubles.Double2ObjectArrayMap
 *  it.unimi.dsi.fastutil.doubles.Double2ObjectMap
 *  net.minecraft.class_1657
 *  net.minecraft.class_1937
 *  net.minecraft.class_3414
 *  net.minecraft.class_3532
 */
package com.sp.destruction.server.custom;

import com.sp.cca.InitializeComponents;
import com.sp.cca.custom.world.WorldDestructionEventsComponent;
import com.sp.destruction.DestructionType;
import com.sp.destruction.server.ServerDestructionEvent;
import com.sp.entity.custom.BlockPhysicsEntity;
import com.sp.networking.ServerPacketManager;
import com.sp.sounds.ModSounds;
import com.sp.util.keyframes.Keyframe;
import com.sp.util.keyframes.KeyframeAnimation;
import com.sp.world.destructionevent.custom.BlackHoleDestruction;
import it.unimi.dsi.fastutil.doubles.Double2ObjectArrayMap;
import it.unimi.dsi.fastutil.doubles.Double2ObjectMap;
import net.minecraft.class_1657;
import net.minecraft.class_1937;
import net.minecraft.class_3414;
import net.minecraft.class_3532;

public class BlackHoleDestructionServerPart2
extends ServerDestructionEvent {
    private static BlockPhysicsEntity entity;
    private static double prevGravityLerp;
    private static final Double2ObjectMap<class_3414> BRAAMS;

    public BlackHoleDestructionServerPart2() {
        super(DestructionType.BLACK_HOLE, 3400);
    }

    @Override
    public void resetEvent() {
        if (entity != null) {
            entity.method_31472();
            entity = null;
        }
        super.resetEvent();
    }

    @Override
    protected KeyframeAnimation initAnimations(class_1937 world) {
        WorldDestructionEventsComponent component = (WorldDestructionEventsComponent)InitializeComponents.EVENTS.get((Object)world);
        return new KeyframeAnimation.KeyframeAnimationBuilder(this.duration, new Keyframe(0.0), new Keyframe(155.0 / (double)this.duration, () -> BlackHoleDestruction.setStartDestruction(true))).globalAction((globalTime, localTime) -> {
            double clampedGlobalTime = Math.floor(globalTime * 10.0) * 0.1;
            if (clampedGlobalTime != prevGravityLerp) {
                component.setGravityLerp(clampedGlobalTime);
                component.syncLight();
                prevGravityLerp = clampedGlobalTime;
                BRAAMS.forEach((aDouble, soundEvent) -> {
                    if (class_3532.method_20390((double)clampedGlobalTime, (double)aDouble)) {
                        for (class_1657 player : world.method_18456()) {
                            ServerPacketManager.sendBraamPacket(player, soundEvent);
                        }
                    }
                });
            }
        }).endAction(() -> {
            prevGravityLerp = 0.0;
            component.setGravityLerp(1.2);
            component.syncLight();
            for (class_1657 player : world.method_18456()) {
                ServerPacketManager.sendBraamPacket(player, ModSounds.BLACK_HOLE_BRAAM_FINAL);
            }
        }).build();
    }

    static {
        BRAAMS = new Double2ObjectArrayMap<class_3414>(){
            {
                this.put(0.2, ModSounds.BLACK_HOLE_BRAAM1);
                this.put(0.4, ModSounds.BLACK_HOLE_BRAAM2);
                this.put(0.6, ModSounds.BLACK_HOLE_BRAAM3);
                this.put(0.8, ModSounds.BLACK_HOLE_BRAAM1);
            }
        };
    }
}

