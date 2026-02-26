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
 *  net.minecraft.class_2277
 *  net.minecraft.class_2338
 *  net.minecraft.class_243
 *  net.minecraft.class_2561
 *  net.minecraft.class_7157
 */
package com.sp.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.sp.world.playzone.PlayZone;
import com.sp.world.playzone.PlayZoneManager;
import net.minecraft.class_1937;
import net.minecraft.class_2168;
import net.minecraft.class_2170;
import net.minecraft.class_2262;
import net.minecraft.class_2277;
import net.minecraft.class_2338;
import net.minecraft.class_243;
import net.minecraft.class_2561;
import net.minecraft.class_7157;

public class AddPlayZoneCommand {
    public static void register(CommandDispatcher<class_2168> serverCommandSourceCommandDispatcher, class_7157 commandRegistryAccess, class_2170.class_5364 registrationEnvironment) {
        serverCommandSourceCommandDispatcher.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)class_2170.method_9247((String)"playzone").requires(source -> source.method_9259(2))).then(class_2170.method_9247((String)"add").then(class_2170.method_9244((String)"position1", (ArgumentType)class_2262.method_9698()).then(class_2170.method_9244((String)"position2", (ArgumentType)class_2262.method_9698()).executes(context -> AddPlayZoneCommand.createPlayZone((CommandContext<class_2168>)context, class_2262.method_48299((CommandContext)context, (String)"position1"), class_2262.method_48299((CommandContext)context, (String)"position2"))))))).then(class_2170.method_9247((String)"remove").then(class_2170.method_9244((String)"position", (ArgumentType)class_2277.method_9737()).executes(context -> AddPlayZoneCommand.removePlayZone((CommandContext<class_2168>)context, class_2277.method_9736((CommandContext)context, (String)"position"))))));
    }

    private static int createPlayZone(CommandContext<class_2168> context, class_2338 position1, class_2338 position2) {
        PlayZone playZone = new PlayZone(position1, position2);
        PlayZoneManager.addPlayZone((class_1937)((class_2168)context.getSource()).method_9225(), playZone);
        return 1;
    }

    private static int removePlayZone(CommandContext<class_2168> context, class_243 position) {
        int numOfPlayZones = PlayZoneManager.removeAllPlayZonesAtPos(position, (class_1937)((class_2168)context.getSource()).method_9225());
        ((class_2168)context.getSource()).method_9226(() -> class_2561.method_43470((String)("Removed " + numOfPlayZones + " play zone" + (numOfPlayZones > 1 ? "s" : ""))), true);
        return numOfPlayZones;
    }
}

