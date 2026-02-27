package com.jujutsushenanigans;

import com.jujutsushenanigans.networking.ModPackets;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class JujutsuShenanigans implements ModInitializer {
    public static final String MOD_ID = "jujutsushenanigans";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    // Item
    public static final Item SIX_EYES = new SixEyesItem(new Item.Settings().maxCount(1));

    // Item Group
    public static final RegistryKey<ItemGroup> ITEM_GROUP = RegistryKey.of(RegistryKeys.ITEM_GROUP, Identifier.of(MOD_ID, "items"));

    // Effect (RegistryEntry for 1.21.1)
    public static RegistryEntry<StatusEffect> SIX_EYES_EFFECT;

    public static Identifier id(String path) {
        return Identifier.of(MOD_ID, path);
    }

    @Override
    public void onInitialize() {
        LOGGER.info("Initializing Jujutsu Shenanigans...");

        // Register Packets
        ModPackets.registerServerPackets();

        // Register Item
        Registry.register(Registries.ITEM, Identifier.of(MOD_ID, "six_eyes"), SIX_EYES);

        // Register Effect
        SIX_EYES_EFFECT = Registry.registerReference(Registries.STATUS_EFFECT, Identifier.of(MOD_ID, "six_eyes_effect"), new SixEyesEffect());

        // Register Item Group
        Registry.register(Registries.ITEM_GROUP, ITEM_GROUP, FabricItemGroup.builder()
                .icon(() -> new ItemStack(SIX_EYES))
                .displayName(Text.translatable("itemGroup.jujutsushenanigans.items"))
                .entries((context, entries) -> {
                    entries.add(SIX_EYES);
                })
                .build());

        // Infinity Physics Logic
        ServerTickEvents.END_SERVER_TICK.register(server -> {
            for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
                if (player instanceof InfinityState state && state.isInfinityActive()) {
                    double radius = 3.0;
                    Box box = player.getBoundingBox().expand(radius);
                    List<Entity> entities = player.getWorld().getOtherEntities(player, box, entity -> 
                        (entity instanceof LivingEntity || entity instanceof ProjectileEntity) && !entity.isSpectator()
                    );

                    for (Entity entity : entities) {
                        Vec3d playerPos = player.getPos();
                        Vec3d entityPos = entity.getPos();
                        double distance = playerPos.distanceTo(entityPos);

                        if (distance < radius) {
                            // Calculate push vector
                            Vec3d pushDir = entityPos.subtract(playerPos).normalize();
                            
                            // The closer they are, the stronger the push
                            double pushStrength = (radius - distance) * 0.5;
                            
                            // Apply velocity
                            entity.addVelocity(pushDir.x * pushStrength, pushDir.y * pushStrength, pushDir.z * pushStrength);
                            entity.velocityModified = true;

                            // If it's a projectile, we might want to reverse its velocity completely or stop it
                            if (entity instanceof ProjectileEntity projectile) {
                                projectile.setVelocity(pushDir.multiply(0.5));
                            }
                            
                            // Spawn touch effect on client side via packet or just let client handle it if we sync positions
                            // For simplicity, we can send a particle packet or use a custom S2C packet for the flare
                            // But since we want to use Veil's Flare API, we need to tell the client where the touch happened.
                            // We'll add a new packet for this.
                            com.jujutsushenanigans.networking.S2C.InfinityTouchS2CPacket touchPacket = 
                                new com.jujutsushenanigans.networking.S2C.InfinityTouchS2CPacket(entityPos);
                            
                            for (ServerPlayerEntity tracker : server.getPlayerManager().getPlayerList()) {
                                if (tracker.getWorld() == player.getWorld() && tracker.squaredDistanceTo(entityPos) < 10000) {
                                    net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking.send(tracker, touchPacket);
                                }
                            }
                        }
                    }
                }
            }
        });
    }
}
