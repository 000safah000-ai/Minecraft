/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory
 *  net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry
 *  net.minecraft.class_1928$class_4310
 *  net.minecraft.class_1928$class_4313
 *  net.minecraft.class_1928$class_4314
 *  net.minecraft.class_1928$class_5198
 */
package com.sp.world;

import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry;
import net.minecraft.class_1928;

public class ModGameRules {
    public static final class_1928.class_4313<class_1928.class_4310> ALLOW_EXPLOSIONS = GameRuleRegistry.register((String)"allowExplosions", (class_1928.class_5198)class_1928.class_5198.field_24100, (class_1928.class_4314)GameRuleFactory.createBooleanRule((boolean)true));

    public static void registerGameRules() {
    }
}

