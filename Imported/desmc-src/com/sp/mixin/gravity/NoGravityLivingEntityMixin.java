/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1297
 *  net.minecraft.class_1299
 *  net.minecraft.class_1309
 *  net.minecraft.class_1937
 *  net.minecraft.class_243
 *  net.minecraft.class_3532
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.Shadow
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.ModifyVariable
 *  org.spongepowered.asm.mixin.injection.Redirect
 */
package com.sp.mixin.gravity;

import com.sp.cca.InitializeComponents;
import com.sp.cca.custom.world.WorldDestructionEventsComponent;
import net.minecraft.class_1297;
import net.minecraft.class_1299;
import net.minecraft.class_1309;
import net.minecraft.class_1937;
import net.minecraft.class_243;
import net.minecraft.class_3532;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value={class_1309.class})
public abstract class NoGravityLivingEntityMixin
extends class_1297 {
    @Shadow
    public abstract boolean method_56992();

    public NoGravityLivingEntityMixin(class_1299<?> type, class_1937 world) {
        super(type, world);
    }

    @ModifyVariable(method={"computeFallDamage"}, at=@At(value="HEAD"), ordinal=0, argsOnly=true)
    private float reduceGravity(float value) {
        WorldDestructionEventsComponent component = (WorldDestructionEventsComponent)InitializeComponents.EVENTS.get((Object)this.method_37908());
        return (float)class_3532.method_16436((double)(component.getGravityLerp() * 1.25), (double)value, (double)0.0);
    }

    @Redirect(method={"travel"}, at=@At(value="INVOKE", target="Lnet/minecraft/entity/LivingEntity;setVelocity(DDD)V", ordinal=2))
    private void reduceGravity(class_1309 instance, double x, double y, double z) {
        if (!this.method_56992() && !this.method_7325()) {
            WorldDestructionEventsComponent component = (WorldDestructionEventsComponent)InitializeComponents.EVENTS.get((Object)this.method_37908());
            class_243 gravityDir = WorldDestructionEventsComponent.gravityDir;
            class_243 velocity = new class_243(x, y, z).method_35590(new class_243(x, y + gravityDir.field_1351, z + gravityDir.field_1350), component.getGravityLerp());
            instance.method_18799(velocity);
        } else {
            instance.method_18800(x, y, z);
        }
    }

    @Redirect(method={"travel"}, at=@At(value="INVOKE", target="Lnet/minecraft/entity/LivingEntity;setVelocity(DDD)V", ordinal=3))
    private void reduceGravityFlutter(class_1309 instance, double x, double y, double z) {
        if (!this.method_56992() && !this.method_7325()) {
            WorldDestructionEventsComponent component = (WorldDestructionEventsComponent)InitializeComponents.EVENTS.get((Object)this.method_37908());
            class_243 gravityDir = WorldDestructionEventsComponent.gravityDir;
            class_243 velocity = new class_243(x, y, z).method_35590(new class_243(x, y + gravityDir.field_1351, z + gravityDir.field_1350), component.getGravityLerp());
            instance.method_18799(velocity);
        } else {
            instance.method_18800(x, y, z);
        }
    }
}

