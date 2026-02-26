/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  foundry.veil.api.client.render.post.PostProcessingManager
 *  foundry.veil.api.client.render.post.PostProcessingManager$ProfileEntry
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.gen.Accessor
 */
package com.sp.mixin;

import foundry.veil.api.client.render.post.PostProcessingManager;
import java.util.List;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value={PostProcessingManager.class}, remap=false)
public interface PostProcessingManagerAccessor {
    @Accessor(value="activePipelines")
    public List<PostProcessingManager.ProfileEntry> getActuallyActivePipelines();
}

