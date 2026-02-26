package com.jujutsushenanigans.mixin;

import com.jujutsushenanigans.item.custom.SixEyesItem;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity implements SixEyesItem.SixEyesState {

    @Unique
    private int sixEyesTimer = 0;
    @Unique
    private boolean isSixEyesActive = false;

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public boolean isSixEyesActive() {
        return this.isSixEyesActive;
    }

    @Override
    public void startSixEyesEffect() {
        this.isSixEyesActive = true;
        this.sixEyesTimer = 0;
    }

    @Override
    public int getSixEyesTimer() {
        return this.sixEyesTimer;
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private void onTick(CallbackInfo ci) {
        if (this.isSixEyesActive) {
            this.sixEyesTimer++;

            // 15 seconds = 300 ticks
            if (this.sixEyesTimer >= 300) {
                // Instant death
                if (!this.getWorld().isClient) {
                    this.kill();
                }
                this.isSixEyesActive = false;
                this.sixEyesTimer = 0;
            } else {
                // Every second (20 ticks), take 1 HP (0.5 hearts) damage
                if (this.sixEyesTimer % 20 == 0 && !this.getWorld().isClient) {
                    // Using generic damage source for now, can be customized
                    DamageSource source = this.getDamageSources().magic();
                    this.damage(source, 1.0f);
                }

                // Apply slowness effect
                if (!this.getWorld().isClient && this.sixEyesTimer % 40 == 0) {
                    this.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 60, 2, false, false, true));
                }
            }
        }
    }
}
