/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1282
 *  net.minecraft.class_1937
 *  net.minecraft.class_2960
 *  net.minecraft.class_5321
 *  net.minecraft.class_6880
 *  net.minecraft.class_7924
 *  net.minecraft.class_8110
 *  net.minecraft.class_8112
 */
package com.sp.entity;

import com.sp.DestroyingMinecraft;
import net.minecraft.class_1282;
import net.minecraft.class_1937;
import net.minecraft.class_2960;
import net.minecraft.class_5321;
import net.minecraft.class_6880;
import net.minecraft.class_7924;
import net.minecraft.class_8110;
import net.minecraft.class_8112;

public class ModDamageSources {
    public static class_8112 PLAY_ZONE_TYPE;
    public static class_8112 CRACKS_TYPE;
    public static class_5321<class_8110> PLAY_ZONE_DAMAGE_TYPE;
    public static class_5321<class_8110> CRACKS_DAMAGE_TYPE;

    public static class_1282 of(class_1937 world, class_5321<class_8110> key) {
        return new class_1282((class_6880)world.method_30349().method_30530(class_7924.field_42534).method_40290(key));
    }

    static {
        PLAY_ZONE_DAMAGE_TYPE = class_5321.method_29179((class_5321)class_7924.field_42534, (class_2960)DestroyingMinecraft.idOf("play_zone"));
        CRACKS_DAMAGE_TYPE = class_5321.method_29179((class_5321)class_7924.field_42534, (class_2960)DestroyingMinecraft.idOf("cracks"));
    }
}

