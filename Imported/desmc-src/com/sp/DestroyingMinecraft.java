/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  eu.midnightdust.lib.config.MidnightConfig
 *  net.fabricmc.api.ModInitializer
 *  net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback
 *  net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents
 *  net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents
 *  net.minecraft.class_1937
 *  net.minecraft.class_2960
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package com.sp;

import com.sp.block.ModBlocks;
import com.sp.block.entity.ModBlockEntities;
import com.sp.cca.InitializeComponents;
import com.sp.cca.custom.world.WorldDestructionEventsComponent;
import com.sp.command.DestructionCommand;
import com.sp.command.ModArgumentTypes;
import com.sp.command.PlayersCommand;
import com.sp.command.RipPlatformOutCommand;
import com.sp.component.ModDataComponentTypes;
import com.sp.config.DestroyingMinecraftConfig;
import com.sp.destruction.DestructionEvent;
import com.sp.destruction.server.ServerDestructionEvent;
import com.sp.destruction.server.ServerDestructionEvents;
import com.sp.entity.ModEntities;
import com.sp.item.ModItemGroups;
import com.sp.item.ModItems;
import com.sp.networking.InitializePackets;
import com.sp.sounds.ModSounds;
import com.sp.world.ModGameRules;
import com.sp.world.destructionevent.custom.BlackHoleDestruction;
import com.sp.world.playzone.PlayZoneManager;
import com.sp.world.spinningblockexplosion.SpinningBlockExplosion;
import eu.midnightdust.lib.config.MidnightConfig;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.class_1937;
import net.minecraft.class_2960;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DestroyingMinecraft
implements ModInitializer {
    public static final String MOD_ID = "destroying-minecraft";
    public static final Logger LOGGER = LoggerFactory.getLogger((String)"destroying-minecraft");

    public void onInitialize() {
        ModItems.registerModItems();
        ModBlocks.init();
        ModItemGroups.registerItemGroups();
        InitializePackets.registerServerNetworking();
        ModEntities.registerEntities();
        ModBlockEntities.registerBlockEntities();
        ModSounds.registerSounds();
        ModGameRules.registerGameRules();
        ModDataComponentTypes.registerDataComponentTypes();
        ModArgumentTypes.registerModArgumentTypes();
        ServerDestructionEvents.registerServerEvents();
        MidnightConfig.init((String)MOD_ID, DestroyingMinecraftConfig.class);
        CommandRegistrationCallback.EVENT.register(DestructionCommand::register);
        CommandRegistrationCallback.EVENT.register(RipPlatformOutCommand::register);
        CommandRegistrationCallback.EVENT.register(PlayersCommand::register);
        LOGGER.info("\"It's nukein' time\" -He said as he loaded the fork into the microwave");
        ServerTickEvents.END_WORLD_TICK.register(serverWorld -> {
            for (SpinningBlockExplosion explosion : SpinningBlockExplosion.getExplosions()) {
                if (!serverWorld.equals(explosion.getWorld())) continue;
                explosion.explode();
            }
            if (serverWorld.method_27983() == class_1937.field_25179) {
                BlackHoleDestruction.tick((class_1937)serverWorld);
            }
        });
        ServerLifecycleEvents.SERVER_STOPPED.register(server -> {
            PlayZoneManager.clearAllPlayZones();
            for (DestructionEvent event : ServerDestructionEvent.getAllServerInstances()) {
                event.resetAnimations();
            }
            for (class_1937 world : server.method_3738()) {
                WorldDestructionEventsComponent component = (WorldDestructionEventsComponent)InitializeComponents.EVENTS.get((Object)world);
                if (component.getCurrentDestructionEvent() != null) {
                    component.getCurrentDestructionEvent().resetEvent();
                }
                component.setAndStartCurrentDestructionEvent(null, -1L);
            }
        });
    }

    public static class_2960 idOf(String path) {
        return class_2960.method_60655((String)MOD_ID, (String)path);
    }
}

