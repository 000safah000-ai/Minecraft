package com.jujutsushenanigans.item.custom;

import com.jujutsushenanigans.item.ModItems;
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
            // Trigger the hallucination effect on the server
            // We will cast the player to our custom interface to start the timer
            if (user instanceof SixEyesState state) {
                if (!state.isSixEyesActive()) {
                    state.startSixEyesEffect();
                    // Optional: consume the item or add cooldown
                    // itemStack.decrement(1);
                    user.getItemCooldownManager().set(this, 300); // 15 seconds cooldown
                }
            }
        }

        return TypedActionResult.success(itemStack, world.isClient());
    }

    /**
     * Interface to be implemented by PlayerEntity via Mixin
     */
    public interface SixEyesState {
        boolean isSixEyesActive();
        void startSixEyesEffect();
        int getSixEyesTimer();
    }
}
