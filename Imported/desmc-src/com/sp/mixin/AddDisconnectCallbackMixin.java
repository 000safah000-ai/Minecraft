/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_310
 *  net.minecraft.class_435
 *  net.minecraft.class_437
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 */
package com.sp.mixin;

import com.sp.networking.callbacks.ClientConnectionEvents;
import net.minecraft.class_310;
import net.minecraft.class_435;
import net.minecraft.class_437;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={class_310.class})
public class AddDisconnectCallbackMixin {
    @Inject(method={"disconnect(Lnet/minecraft/client/gui/screen/Screen;)V"}, at={@At(value="HEAD")})
    private void onDisconnect(class_437 screen, CallbackInfo ci) {
        if (!(screen instanceof class_435)) {
            ((ClientConnectionEvents.Disconnect)ClientConnectionEvents.DISCONNECT.invoker()).onLoginDisconnect((class_310)this);
        }
    }
}

