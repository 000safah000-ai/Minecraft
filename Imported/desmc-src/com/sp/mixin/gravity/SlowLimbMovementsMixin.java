/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.llamalad7.mixinextras.sugar.Local
 *  net.minecraft.class_1309
 *  net.minecraft.class_3532
 *  net.minecraft.class_8080
 *  net.minecraft.class_922
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Redirect
 */
package com.sp.mixin.gravity;

import com.llamalad7.mixinextras.sugar.Local;
import com.sp.cca.InitializeComponents;
import com.sp.cca.custom.world.WorldDestructionEventsComponent;
import net.minecraft.class_1309;
import net.minecraft.class_3532;
import net.minecraft.class_8080;
import net.minecraft.class_922;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value={class_922.class})
public class SlowLimbMovementsMixin {
    private static final String RENDER = "render(Lnet/minecraft/entity/LivingEntity;FFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V";

    @Redirect(method={"render(Lnet/minecraft/entity/LivingEntity;FFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V"}, at=@At(value="INVOKE", target="Lnet/minecraft/entity/LimbAnimator;getPos(F)F"))
    private float slowerSpeed(class_8080 instance, float tickDelta, @Local(argsOnly=true) class_1309 livingEntity) {
        if (!(livingEntity.method_24828() || livingEntity.method_56992() || livingEntity.method_7325())) {
            WorldDestructionEventsComponent component = (WorldDestructionEventsComponent)InitializeComponents.EVENTS.get((Object)livingEntity.method_37908());
            return (float)class_3532.method_16436((double)component.getGravityLerp(), (double)instance.method_48572(tickDelta), (double)0.01);
        }
        return instance.method_48572(tickDelta);
    }
}

