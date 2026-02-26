/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.brigadier.CommandDispatcher
 *  com.mojang.brigadier.arguments.ArgumentType
 *  com.mojang.brigadier.builder.LiteralArgumentBuilder
 *  com.mojang.brigadier.context.CommandContext
 *  net.minecraft.class_1657
 *  net.minecraft.class_2168
 *  net.minecraft.class_2170
 *  net.minecraft.class_2170$class_5364
 *  net.minecraft.class_2186
 *  net.minecraft.class_3222
 *  net.minecraft.class_7157
 *  net.minecraft.class_7485
 */
package com.sp.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.sp.cca.InitializeComponents;
import com.sp.cca.custom.entity.PlayerComponent;
import com.sp.networking.ServerPacketManager;
import com.sp.render.ShaderType;
import java.util.Collection;
import net.minecraft.class_1657;
import net.minecraft.class_2168;
import net.minecraft.class_2170;
import net.minecraft.class_2186;
import net.minecraft.class_3222;
import net.minecraft.class_7157;
import net.minecraft.class_7485;

public class PlayersCommand {
    public static void register(CommandDispatcher<class_2168> serverCommandSourceCommandDispatcher, class_7157 commandRegistryAccess, class_2170.class_5364 registrationEnvironment) {
        serverCommandSourceCommandDispatcher.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)class_2170.method_9247((String)"players").requires(source -> source.method_9259(2))).then(class_2170.method_9247((String)"changeshaders").then(class_2170.method_9244((String)"shader", ShaderTypeArgumentType.shaderType()).executes(context -> PlayersCommand.executeChangeShaders((CommandContext<class_2168>)context, ShaderTypeArgumentType.getShaderType((CommandContext<class_2168>)context, "shader")))))).then(class_2170.method_9247((String)"reset").then(class_2170.method_9244((String)"targets", (ArgumentType)class_2186.method_9308()).executes(context -> PlayersCommand.executeReset((CommandContext<class_2168>)context, class_2186.method_9312((CommandContext)context, (String)"targets"))))));
    }

    private static int executeChangeShaders(CommandContext<class_2168> context, ShaderType shader) {
        for (class_3222 player : ((class_2168)context.getSource()).method_9225().method_18456()) {
            ServerPacketManager.sendShaderChangePacket((class_1657)player, shader);
        }
        return 1;
    }

    private static int executeSetInWaitingRoom(Collection<class_3222> targets, boolean setInWaitingRoom) {
        if (targets.isEmpty()) {
            return -1;
        }
        for (class_3222 player : targets) {
            ServerPacketManager.sendWaitingRoomPacket((class_1657)player, setInWaitingRoom);
        }
        return targets.size();
    }

    private static int executeReset(CommandContext<class_2168> context, Collection<class_3222> targets) {
        for (class_3222 player : targets) {
            PlayerComponent component = (PlayerComponent)InitializeComponents.PLAYERS.get((Object)player);
            component.resetPlayer();
            ServerPacketManager.sendWaitingRoomPacket((class_1657)player, false);
        }
        return targets.size();
    }

    public static class ShaderTypeArgumentType
    extends class_7485<ShaderType> {
        protected ShaderTypeArgumentType() {
            super(ShaderType.CODEC, ShaderType::values);
        }

        public static class_7485<ShaderType> shaderType() {
            return new ShaderTypeArgumentType();
        }

        public static ShaderType getShaderType(CommandContext<class_2168> context, String id) {
            return (ShaderType)((Object)context.getArgument(id, ShaderType.class));
        }
    }
}

