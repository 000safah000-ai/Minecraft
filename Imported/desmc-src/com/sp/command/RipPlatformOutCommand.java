/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.brigadier.CommandDispatcher
 *  com.mojang.brigadier.arguments.ArgumentType
 *  com.mojang.brigadier.builder.LiteralArgumentBuilder
 *  com.mojang.brigadier.context.CommandContext
 *  net.minecraft.class_1937
 *  net.minecraft.class_2168
 *  net.minecraft.class_2170
 *  net.minecraft.class_2170$class_5364
 *  net.minecraft.class_2262
 *  net.minecraft.class_2338
 *  net.minecraft.class_7157
 */
package com.sp.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.sp.entity.custom.BlockPhysicsEntity;
import net.minecraft.class_1937;
import net.minecraft.class_2168;
import net.minecraft.class_2170;
import net.minecraft.class_2262;
import net.minecraft.class_2338;
import net.minecraft.class_7157;

public class RipPlatformOutCommand {
    public static void register(CommandDispatcher<class_2168> serverCommandSourceCommandDispatcher, class_7157 commandRegistryAccess, class_2170.class_5364 registrationEnvironment) {
        serverCommandSourceCommandDispatcher.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)class_2170.method_9247((String)"makeblockphysics").requires(source -> source.method_9259(2))).then(class_2170.method_9244((String)"position1", (ArgumentType)class_2262.method_9698()).then(class_2170.method_9244((String)"position2", (ArgumentType)class_2262.method_9698()).executes(context -> RipPlatformOutCommand.createPBE((CommandContext<class_2168>)context, class_2262.method_48299((CommandContext)context, (String)"position1"), class_2262.method_48299((CommandContext)context, (String)"position2"))))));
    }

    private static int createPBE(CommandContext<class_2168> context, class_2338 position1, class_2338 position2) {
        BlockPhysicsEntity.ofBlocks((class_1937)((class_2168)context.getSource()).method_9225(), position1, position2);
        return 1;
    }
}

