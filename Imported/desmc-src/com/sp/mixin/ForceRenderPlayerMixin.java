/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.authlib.GameProfile
 *  net.minecraft.class_1297
 *  net.minecraft.class_1299
 *  net.minecraft.class_1657
 *  net.minecraft.class_1937
 *  net.minecraft.class_2338
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 */
package com.sp.mixin;

import com.mojang.authlib.GameProfile;
import net.minecraft.class_1297;
import net.minecraft.class_1299;
import net.minecraft.class_1657;
import net.minecraft.class_1937;
import net.minecraft.class_2338;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={class_1657.class})
public abstract class ForceRenderPlayerMixin
extends class_1297 {
    public ForceRenderPlayerMixin(class_1299<?> type, class_1937 world) {
        super(type, world);
    }

    @Inject(method={"<init>"}, at={@At(value="TAIL")})
    private void forceRender(class_1937 world, class_2338 pos, float yaw, GameProfile gameProfile, CallbackInfo ci) {
        this.field_5985 = true;
    }
}

