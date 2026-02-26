/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_2709
 *  net.minecraft.class_3218
 *  net.minecraft.class_3222
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.At$Shift
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable
 */
package com.sp.mixin.layingdown;

import com.sp.mixininterfaces.LayingDownPlayerEntity;
import java.util.Set;
import net.minecraft.class_2709;
import net.minecraft.class_3218;
import net.minecraft.class_3222;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value={class_3222.class})
public class WakeUpPlayerMixin {
    @Inject(method={"onDisconnect"}, at={@At(value="TAIL")})
    private void getUpOnDisconnect(CallbackInfo ci) {
        LayingDownPlayerEntity layingDownPlayerEntity;
        class_3222 class_32222 = (class_3222)this;
        if (class_32222 instanceof LayingDownPlayerEntity && (layingDownPlayerEntity = (LayingDownPlayerEntity)class_32222).isLayingDown()) {
            layingDownPlayerEntity.getUp();
        }
    }

    @Inject(method={"teleport(Lnet/minecraft/server/world/ServerWorld;DDDLjava/util/Set;FF)Z"}, at={@At(value="INVOKE", target="Lnet/minecraft/server/network/ServerPlayerEntity;stopRiding()V", shift=At.Shift.AFTER)})
    private void getUpOnTeleport(class_3218 world, double destX, double destY, double destZ, Set<class_2709> flags, float yaw, float pitch, CallbackInfoReturnable<Boolean> cir) {
        LayingDownPlayerEntity layingDownPlayerEntity;
        class_3222 class_32222 = (class_3222)this;
        if (class_32222 instanceof LayingDownPlayerEntity && (layingDownPlayerEntity = (LayingDownPlayerEntity)class_32222).isLayingDown()) {
            layingDownPlayerEntity.getUp();
        }
    }
}

