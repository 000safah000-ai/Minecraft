/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.caffeinemc.mods.sodium.client.render.chunk.RenderSection
 *  net.caffeinemc.mods.sodium.client.render.chunk.occlusion.OcclusionCuller
 *  net.caffeinemc.mods.sodium.client.render.viewport.Viewport
 *  net.minecraft.class_310
 *  net.minecraft.class_746
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable
 */
package com.sp.mixin.compat.sodium;

import com.sp.DestroyingMinecraftClient;
import net.caffeinemc.mods.sodium.client.render.chunk.RenderSection;
import net.caffeinemc.mods.sodium.client.render.chunk.occlusion.OcclusionCuller;
import net.caffeinemc.mods.sodium.client.render.viewport.Viewport;
import net.minecraft.class_310;
import net.minecraft.class_746;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value={OcclusionCuller.class}, remap=false)
public class ChunkDestroyingMixin {
    @Inject(method={"isSectionVisible"}, at={@At(value="RETURN")}, cancellable=true)
    private static void test(RenderSection section, Viewport viewport, float maxDistance, CallbackInfoReturnable<Boolean> cir) {
        class_746 player = class_310.method_1551().field_1724;
        if (player != null) {
            int distToPlayer = section.getCenterX() - player.method_31477();
            boolean bl = distToPlayer <= DestroyingMinecraftClient.destructionDistance;
            cir.setReturnValue((Object)((Boolean)cir.getReturnValue() != false && bl ? 1 : 0));
        }
    }
}

