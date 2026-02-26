/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.fabricmc.fabric.api.command.v2.ArgumentTypeRegistry
 *  net.minecraft.class_2314
 *  net.minecraft.class_2319
 *  net.minecraft.class_2960
 */
package com.sp.command;

import com.sp.DestroyingMinecraft;
import com.sp.command.DestructionCommand;
import com.sp.command.PlayersCommand;
import net.fabricmc.fabric.api.command.v2.ArgumentTypeRegistry;
import net.minecraft.class_2314;
import net.minecraft.class_2319;
import net.minecraft.class_2960;

public class ModArgumentTypes {
    public static void registerModArgumentTypes() {
        DestroyingMinecraft.LOGGER.info("Registering command argument types for destroying-minecraft");
        ArgumentTypeRegistry.registerArgumentType((class_2960)DestroyingMinecraft.idOf("shader_type"), PlayersCommand.ShaderTypeArgumentType.class, (class_2314)class_2319.method_41999(PlayersCommand.ShaderTypeArgumentType::shaderType));
        ArgumentTypeRegistry.registerArgumentType((class_2960)DestroyingMinecraft.idOf("destruction_types"), DestructionCommand.DestructionArgumentTypes.class, (class_2314)class_2319.method_41999(DestructionCommand.DestructionArgumentTypes::destructionType));
    }
}

