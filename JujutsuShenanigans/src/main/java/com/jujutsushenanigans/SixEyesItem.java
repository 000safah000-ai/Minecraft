package com.jujutsushenanigans;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class SixEyesItem extends Item {
    public SixEyesItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        
        if (!world.isClient) {
            // Apply effect for 15 seconds (300 ticks)
            user.addStatusEffect(new net.minecraft.entity.effect.StatusEffectInstance(JujutsuShenanigans.SIX_EYES_EFFECT, 300, 0, false, false, true));
        }

        return TypedActionResult.success(itemStack, world.isClient());
    }
}
