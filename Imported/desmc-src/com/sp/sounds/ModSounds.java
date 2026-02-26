/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_2378
 *  net.minecraft.class_2960
 *  net.minecraft.class_3414
 *  net.minecraft.class_7923
 */
package com.sp.sounds;

import com.sp.DestroyingMinecraft;
import net.minecraft.class_2378;
import net.minecraft.class_2960;
import net.minecraft.class_3414;
import net.minecraft.class_7923;

public class ModSounds {
    public static final class_3414 DOOR_OPEN = ModSounds.registerSoundEvent("door_open");
    public static final class_3414 DOOR_OPENING_LOOP = ModSounds.registerSoundEvent("door_opening_loop");
    public static final class_3414 DOOR_CLOSE = ModSounds.registerSoundEvent("door_close");
    public static final class_3414 ORBITAL_LASER_INITIALIZE = ModSounds.registerSoundEvent("ol_initialize");
    public static final class_3414 PLANET_INITIALIZE = ModSounds.registerSoundEvent("p_initialize");
    public static final class_3414 SUPERNOVA_INITIALIZE = ModSounds.registerSoundEvent("sn_initialize");
    public static final class_3414 BLACK_HOLE_INITIALIZE = ModSounds.registerSoundEvent("bh_initialize");
    public static final class_3414 PLANET_AMBIENCE = ModSounds.registerSoundEvent("planet_ambience");
    public static final class_3414 PLANET_RUMBLE = ModSounds.registerSoundEvent("planet_rumble");
    public static final class_3414 PLANET_IMPACT_INITIAL = ModSounds.registerSoundEvent("planet_impact_initial");
    public static final class_3414 PLANET_IMPACT = ModSounds.registerSoundEvent("planet_impact");
    public static final class_3414 METEOR_WHISTLE = ModSounds.registerSoundEvent("meteor_whistle");
    public static final class_3414 METEOR_IMPACT = ModSounds.registerSoundEvent("meteor_impact");
    public static final class_3414 LASER_CHARGE = ModSounds.registerSoundEvent("laser_charge");
    public static final class_3414 LASER_FIRE = ModSounds.registerSoundEvent("laser_fire");
    public static final class_3414 LASER_POWER_DOWN = ModSounds.registerSoundEvent("laser_power_down");
    public static final class_3414 LASER_PAUSE = ModSounds.registerSoundEvent("laser_pause");
    public static final class_3414 SUPERNOVA_JAZZ = ModSounds.registerSoundEvent("supernova_jazz");
    public static final class_3414 SUPERNOVA_EXPLOSION = ModSounds.registerSoundEvent("supernova_explosion");
    public static final class_3414 BLACK_HOLE_AMBIENCE = ModSounds.registerSoundEvent("black_hole_ambience");
    public static final class_3414 BLACK_HOLE_DESTRUCTION_AMBIENCE = ModSounds.registerSoundEvent("black_hole_destruction_ambience");
    public static final class_3414 SNAP_SNAP = ModSounds.registerSoundEvent("snap_snap");
    public static final class_3414 SNAP_SNAP_RUMBLE = ModSounds.registerSoundEvent("snap_snap_rumble");
    public static final class_3414 SNAP_SNAP_BREAK_OFF = ModSounds.registerSoundEvent("snap_snap_break_off");
    public static final class_3414 BREAK_OFF = ModSounds.registerSoundEvent("break_off");
    public static final class_3414 BLACK_HOLE_BRAAM1 = ModSounds.registerSoundEvent("black_hole_braam1");
    public static final class_3414 BLACK_HOLE_BRAAM2 = ModSounds.registerSoundEvent("black_hole_braam2");
    public static final class_3414 BLACK_HOLE_BRAAM3 = ModSounds.registerSoundEvent("black_hole_braam3");
    public static final class_3414 BLACK_HOLE_BRAAM_FINAL = ModSounds.registerSoundEvent("black_hole_braam_final");
    public static final class_3414 GLITCH = ModSounds.registerSoundEvent("glitch");
    public static final class_3414 LAVA_SPEW = ModSounds.registerSoundEvent("lava_spew");
    public static final class_3414 LASER_CRACKING_INITIAL = ModSounds.registerSoundEvent("laser_cracking_initial");
    public static final class_3414 LASER_CRACKING_LOOP = ModSounds.registerSoundEvent("laser_cracking_loop");
    public static final class_3414 LASER_LANDING = ModSounds.registerSoundEvent("laser_landing");
    public static final class_3414 LASER_LOOP = ModSounds.registerSoundEvent("laser_loop");
    public static final class_3414 LASER_END = ModSounds.registerSoundEvent("laser_end");
    public static final class_3414 LAVA_DEATH = ModSounds.registerSoundEvent("lava_death");
    public static final class_3414 WALKIE_TALKIE = ModSounds.registerSoundEvent("walkie_talkie");
    public static final class_3414 WAITING_ROOM_HIT = ModSounds.registerSoundEvent("waiting_room_hit");
    public static final class_3414 COUNT_DOWN = ModSounds.registerSoundEvent("count_down");

    private static class_3414 registerSoundEvent(String name) {
        class_2960 id = DestroyingMinecraft.idOf(name);
        return (class_3414)class_2378.method_10230((class_2378)class_7923.field_41172, (class_2960)id, (Object)class_3414.method_47908((class_2960)id));
    }

    public static void registerSounds() {
        DestroyingMinecraft.LOGGER.info("Registering Sounds for destroying-minecraft");
    }
}

