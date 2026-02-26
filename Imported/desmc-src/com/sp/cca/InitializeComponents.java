/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_2960
 *  org.ladysnake.cca.api.v3.component.ComponentKey
 *  org.ladysnake.cca.api.v3.component.ComponentRegistry
 *  org.ladysnake.cca.api.v3.entity.EntityComponentFactoryRegistry
 *  org.ladysnake.cca.api.v3.entity.EntityComponentInitializer
 *  org.ladysnake.cca.api.v3.entity.RespawnCopyStrategy
 *  org.ladysnake.cca.api.v3.world.WorldComponentFactoryRegistry
 *  org.ladysnake.cca.api.v3.world.WorldComponentInitializer
 */
package com.sp.cca;

import com.sp.DestroyingMinecraft;
import com.sp.cca.custom.entity.PhysicsBlockComponent;
import com.sp.cca.custom.entity.PlayerComponent;
import com.sp.cca.custom.entity.SpinningBlockComponent;
import com.sp.cca.custom.world.WorldDestructionEventsComponent;
import com.sp.entity.custom.BlockPhysicsEntity;
import com.sp.entity.custom.SpinningBlockEntity;
import net.minecraft.class_2960;
import org.ladysnake.cca.api.v3.component.ComponentKey;
import org.ladysnake.cca.api.v3.component.ComponentRegistry;
import org.ladysnake.cca.api.v3.entity.EntityComponentFactoryRegistry;
import org.ladysnake.cca.api.v3.entity.EntityComponentInitializer;
import org.ladysnake.cca.api.v3.entity.RespawnCopyStrategy;
import org.ladysnake.cca.api.v3.world.WorldComponentFactoryRegistry;
import org.ladysnake.cca.api.v3.world.WorldComponentInitializer;

public class InitializeComponents
implements EntityComponentInitializer,
WorldComponentInitializer {
    public static final ComponentKey<SpinningBlockComponent> SPINNING_BLOCK = ComponentRegistry.getOrCreate((class_2960)DestroyingMinecraft.idOf("spin_block"), SpinningBlockComponent.class);
    public static final ComponentKey<PhysicsBlockComponent> PHYSICS_BLOCK = ComponentRegistry.getOrCreate((class_2960)DestroyingMinecraft.idOf("phys_block"), PhysicsBlockComponent.class);
    public static final ComponentKey<WorldDestructionEventsComponent> EVENTS = ComponentRegistry.getOrCreate((class_2960)DestroyingMinecraft.idOf("events"), WorldDestructionEventsComponent.class);
    public static final ComponentKey<PlayerComponent> PLAYERS = ComponentRegistry.getOrCreate((class_2960)DestroyingMinecraft.idOf("players"), PlayerComponent.class);

    public void registerEntityComponentFactories(EntityComponentFactoryRegistry entityComponentFactoryRegistry) {
        entityComponentFactoryRegistry.registerFor(SpinningBlockEntity.class, SPINNING_BLOCK, SpinningBlockComponent::new);
        entityComponentFactoryRegistry.registerFor(BlockPhysicsEntity.class, PHYSICS_BLOCK, PhysicsBlockComponent::new);
        entityComponentFactoryRegistry.registerForPlayers(PLAYERS, PlayerComponent::new, RespawnCopyStrategy.ALWAYS_COPY);
    }

    public void registerWorldComponentFactories(WorldComponentFactoryRegistry worldComponentFactoryRegistry) {
        worldComponentFactoryRegistry.register(EVENTS, WorldDestructionEventsComponent::new);
    }
}

