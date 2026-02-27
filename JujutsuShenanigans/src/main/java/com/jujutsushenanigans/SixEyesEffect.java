package com.jujutsushenanigans;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;

public class SixEyesEffect extends StatusEffect {
    protected SixEyesEffect() {
        super(StatusEffectCategory.HARMFUL, 0x0000FF); // Blue color
    }

    @Override
    public boolean applyUpdateEffect(LivingEntity entity, int amplifier) {
        if (entity.hasStatusEffect(JujutsuShenanigans.SIX_EYES_EFFECT)) {
            int duration = entity.getStatusEffect(JujutsuShenanigans.SIX_EYES_EFFECT).getDuration();
            
            // 15 seconds = 300 ticks
            // Lose 1 heart (2 HP) per second (every 20 ticks)
            if (duration % 20 == 0) {
                entity.damage(entity.getDamageSources().magic(), 2.0f);
            }
            
            // After 15 seconds (duration == 1), instant death
            if (duration <= 1) {
                entity.kill();
            }
            
            // Apply slowness
            entity.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 2, 4, false, false, false));
        }
        
        return true;
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true; // Apply every tick
    }
}
