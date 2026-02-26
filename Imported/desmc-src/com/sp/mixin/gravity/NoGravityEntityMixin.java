/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1297
 *  net.minecraft.class_1937
 *  net.minecraft.class_243
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.Shadow
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Redirect
 */
package com.sp.mixin.gravity;

import com.sp.cca.InitializeComponents;
import com.sp.cca.custom.world.WorldDestructionEventsComponent;
import net.minecraft.class_1297;
import net.minecraft.class_1937;
import net.minecraft.class_243;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value={class_1297.class})
public abstract class NoGravityEntityMixin {
    @Shadow
    public abstract class_1937 method_37908();

    @Redirect(method={"applyGravity"}, at=@At(value="INVOKE", target="Lnet/minecraft/util/math/Vec3d;add(DDD)Lnet/minecraft/util/math/Vec3d;"))
    private class_243 reduceGravity(class_243 instance, double x, double y, double z) {
        WorldDestructionEventsComponent component = (WorldDestructionEventsComponent)InitializeComponents.EVENTS.get((Object)this.method_37908());
        class_243 gravityDir = WorldDestructionEventsComponent.gravityDir;
        class_243 velocity = new class_243(x, y, z).method_35590(new class_243(x, y + gravityDir.field_1351, gravityDir.field_1350), component.getGravityLerp());
        return instance.method_1019(velocity);
    }
}

