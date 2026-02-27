package com.jujutsushenanigans.mixin;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin implements com.jujutsushenanigans.InfinityState {
    @Unique
    private boolean infinityActive = false;

    @Override
    public boolean isInfinityActive() {
        return this.infinityActive;
    }

    @Override
    public void setInfinityActive(boolean active) {
        this.infinityActive = active;
    }

    @Inject(method = "writeCustomDataToNbt", at = @At("TAIL"))
    private void writeInfinityData(NbtCompound nbt, CallbackInfo ci) {
        nbt.putBoolean("InfinityActive", this.infinityActive);
    }

    @Inject(method = "readCustomDataFromNbt", at = @At("TAIL"))
    private void readInfinityData(NbtCompound nbt, CallbackInfo ci) {
        if (nbt.contains("InfinityActive")) {
            this.infinityActive = nbt.getBoolean("InfinityActive");
        }
    }
}
