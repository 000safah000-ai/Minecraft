/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.brigadier.CommandDispatcher
 *  com.mojang.brigadier.LiteralMessage
 *  com.mojang.brigadier.Message
 *  com.mojang.brigadier.arguments.ArgumentType
 *  com.mojang.brigadier.arguments.FloatArgumentType
 *  com.mojang.brigadier.arguments.IntegerArgumentType
 *  com.mojang.brigadier.builder.LiteralArgumentBuilder
 *  com.mojang.brigadier.context.CommandContext
 *  com.mojang.brigadier.exceptions.CommandSyntaxException
 *  com.mojang.brigadier.exceptions.SimpleCommandExceptionType
 *  net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking
 *  net.minecraft.class_1297
 *  net.minecraft.class_1937
 *  net.minecraft.class_2168
 *  net.minecraft.class_2170
 *  net.minecraft.class_2170$class_5364
 *  net.minecraft.class_2277
 *  net.minecraft.class_2338
 *  net.minecraft.class_2374
 *  net.minecraft.class_238
 *  net.minecraft.class_243
 *  net.minecraft.class_2561
 *  net.minecraft.class_3218
 *  net.minecraft.class_3222
 *  net.minecraft.class_7157
 *  net.minecraft.class_7485
 *  net.minecraft.class_8710
 *  org.joml.Vector3f
 */
package com.sp.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.LiteralMessage;
import com.mojang.brigadier.Message;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.sp.cca.InitializeComponents;
import com.sp.cca.custom.world.WorldDestructionEventsComponent;
import com.sp.destruction.DestructionEvent;
import com.sp.destruction.DestructionType;
import com.sp.destruction.server.ServerDestructionEvent;
import com.sp.entity.custom.BlockPhysicsEntity;
import com.sp.entity.custom.StarPiercerEntity;
import com.sp.networking.CustomPayloads;
import com.sp.world.destructionevent.custom.BlackHoleDestruction;
import com.sp.world.spinningblockexplosion.custom.DirectionalSBE;
import com.sp.world.spinningblockexplosion.custom.PointSBE;
import java.util.List;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.class_1297;
import net.minecraft.class_1937;
import net.minecraft.class_2168;
import net.minecraft.class_2170;
import net.minecraft.class_2277;
import net.minecraft.class_2338;
import net.minecraft.class_2374;
import net.minecraft.class_238;
import net.minecraft.class_243;
import net.minecraft.class_2561;
import net.minecraft.class_3218;
import net.minecraft.class_3222;
import net.minecraft.class_7157;
import net.minecraft.class_7485;
import net.minecraft.class_8710;
import org.joml.Vector3f;

public class DestructionCommand {
    private static final SimpleCommandExceptionType NO_STAR_PIERCERS_EXCEPTION = new SimpleCommandExceptionType((Message)new LiteralMessage("No Star Piercers exist nearby"));

    public static void register(CommandDispatcher<class_2168> serverCommandSourceCommandDispatcher, class_7157 commandRegistryAccess, class_2170.class_5364 registrationEnvironment) {
        serverCommandSourceCommandDispatcher.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)class_2170.method_9247((String)"destruction").requires(source -> source.method_9259(2))).then(class_2170.method_9244((String)"type", DestructionArgumentTypes.destructionType()).then(class_2170.method_9244((String)"position", (ArgumentType)class_2277.method_9737()).executes(context -> DestructionCommand.execute((CommandContext<class_2168>)context, DestructionArgumentTypes.getDestructionType((CommandContext<class_2168>)context, "type"), class_2277.method_9736((CommandContext)context, (String)"position")))))).then(((LiteralArgumentBuilder)class_2170.method_9247((String)"explosion").then(class_2170.method_9247((String)"directional").then(class_2170.method_9244((String)"length", (ArgumentType)IntegerArgumentType.integer((int)0)).then(class_2170.method_9244((String)"width", (ArgumentType)IntegerArgumentType.integer((int)0)).then(class_2170.method_9244((String)"angle", (ArgumentType)FloatArgumentType.floatArg()).then(class_2170.method_9244((String)"density", (ArgumentType)FloatArgumentType.floatArg((float)0.0f)).then(class_2170.method_9244((String)"location", (ArgumentType)class_2277.method_9737()).executes(commandContext -> DestructionCommand.directionalSpinningBlockExplosion(((class_2168)commandContext.getSource()).method_9225(), IntegerArgumentType.getInteger((CommandContext)commandContext, (String)"length"), IntegerArgumentType.getInteger((CommandContext)commandContext, (String)"width"), FloatArgumentType.getFloat((CommandContext)commandContext, (String)"angle"), FloatArgumentType.getFloat((CommandContext)commandContext, (String)"density"), class_2277.method_9736((CommandContext)commandContext, (String)"location")))))))))).then(class_2170.method_9247((String)"point").then(class_2170.method_9244((String)"radius", (ArgumentType)IntegerArgumentType.integer((int)0)).then(class_2170.method_9244((String)"density", (ArgumentType)FloatArgumentType.floatArg((float)0.0f)).then(class_2170.method_9244((String)"location", (ArgumentType)class_2277.method_9737()).executes(commandContext -> DestructionCommand.pointSpinningBlockExplosion(((class_2168)commandContext.getSource()).method_9225(), IntegerArgumentType.getInteger((CommandContext)commandContext, (String)"radius"), FloatArgumentType.getFloat((CommandContext)commandContext, (String)"density"), class_2277.method_9736((CommandContext)commandContext, (String)"location"))))))))).then(class_2170.method_9247((String)"reset").executes(DestructionCommand::reset)));
    }

    private static int execute(CommandContext<class_2168> context, DestructionType type, class_243 position) throws CommandSyntaxException {
        class_238 box;
        class_3218 world;
        Object starPiercerEntities;
        List playerList = ((class_2168)context.getSource()).method_9225().method_18456();
        WorldDestructionEventsComponent worldComponent = (WorldDestructionEventsComponent)InitializeComponents.EVENTS.get((Object)((class_2168)context.getSource()).method_9225());
        if (type == DestructionType.SUPERNOVA && (starPiercerEntities = (world = ((class_2168)context.getSource()).method_9225()).method_8390(StarPiercerEntity.class, box = class_238.method_30048((class_243)((class_2168)context.getSource()).method_9222(), (double)500.0, (double)300.0, (double)500.0), starPiercerEntity -> true)).isEmpty()) {
            throw NO_STAR_PIERCERS_EXCEPTION.create();
        }
        long startTime = ((class_2168)context.getSource()).method_9225().method_8510();
        for (class_3222 player : playerList) {
            ServerPlayNetworking.send((class_3222)player, (class_8710)new CustomPayloads.DestructionPayload(type.getName(), position.method_46409(), startTime));
            player.method_26284(((class_2168)context.getSource()).method_9225().method_27983(), class_2338.method_49638((class_2374)position), 0.0f, true, false);
        }
        if (type.equals((Object)DestructionType.BLACK_HOLE)) {
            int i = BlackHoleDestruction.selectSurfaceBlocks(class_2338.method_49638((class_2374)position), (class_1937)((class_2168)context.getSource()).method_9225());
            ((class_2168)context.getSource()).method_9226(() -> class_2561.method_43470((String)("Successfully selected " + i + " blocks for destruction")), true);
        }
        for (DestructionEvent event : ServerDestructionEvent.getAllServerInstances()) {
            if (!event.getDestructionType().equals((Object)type)) continue;
            worldComponent.setAndStartCurrentDestructionEvent(event, startTime);
            worldComponent.setDestructionEventPosition(position);
            break;
        }
        return 1;
    }

    private static int reset(CommandContext<class_2168> context) {
        WorldDestructionEventsComponent worldComponent = (WorldDestructionEventsComponent)InitializeComponents.EVENTS.get((Object)((class_2168)context.getSource()).method_9225());
        for (DestructionEvent event : ServerDestructionEvent.getAllServerInstances()) {
            event.setActive(false, -1L);
            event.resetEvent();
        }
        BlackHoleDestruction.setStartDestruction(false);
        ((class_2168)context.getSource()).method_9225().method_8390(BlockPhysicsEntity.class, class_238.method_30048((class_243)worldComponent.getDestructionEventPosition(), (double)1000.0, (double)1000.0, (double)1000.0), blockPhysicsEntity -> true).forEach(class_1297::method_31472);
        worldComponent.setGravityLerp(0.0);
        worldComponent.syncLight();
        for (class_3222 player : ((class_2168)context.getSource()).method_9225().method_18456()) {
            ServerPlayNetworking.send((class_3222)player, (class_8710)new CustomPayloads.DestructionPayload("reset", new Vector3f(), -1L));
        }
        return 1;
    }

    private static int directionalSpinningBlockExplosion(class_3218 world, int length, int width, float angle, float density, class_243 position) {
        DirectionalSBE explosion = new DirectionalSBE(length, width, angle, density, position);
        explosion.beginExplosion(world);
        return 1;
    }

    private static int pointSpinningBlockExplosion(class_3218 world, int radius, float density, class_243 position) {
        PointSBE explosion = new PointSBE(radius, density, position);
        explosion.beginExplosion(world);
        return 1;
    }

    public static class DestructionArgumentTypes
    extends class_7485<DestructionType> {
        protected DestructionArgumentTypes() {
            super(DestructionType.CODEC, DestructionType::values);
        }

        public static class_7485<DestructionType> destructionType() {
            return new DestructionArgumentTypes();
        }

        public static DestructionType getDestructionType(CommandContext<class_2168> context, String id) {
            return (DestructionType)((Object)context.getArgument(id, DestructionType.class));
        }
    }
}

