/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.moulberry.flashback.playback.ReplayServer
 *  net.minecraft.class_1937
 *  net.minecraft.class_310
 *  net.minecraft.class_638
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 */
package com.sp.mixin.compat.flashback;

import com.moulberry.flashback.playback.ReplayServer;
import com.sp.cca.InitializeComponents;
import com.sp.cca.custom.world.WorldDestructionEventsComponent;
import net.minecraft.class_1937;
import net.minecraft.class_310;
import net.minecraft.class_638;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={ReplayServer.class})
public class ReplayServerMixin {
    @Inject(method={"handleActions"}, at={@At(value="INVOKE", target="Lcom/moulberry/flashback/playback/ReplayServer;clearDataForPlayingSnapshot()V", ordinal=0)})
    private void test(CallbackInfo ci) {
        WorldDestructionEventsComponent component2;
        class_638 clientWorld = class_310.method_1551().field_1687;
        if (clientWorld != null && (component2 = (WorldDestructionEventsComponent)InitializeComponents.EVENTS.get((Object)clientWorld)).getCurrentDestructionEvent() != null) {
            component2.getCurrentDestructionEvent().resetAnimationToCurrentTime((class_1937)clientWorld);
        }
    }
}

