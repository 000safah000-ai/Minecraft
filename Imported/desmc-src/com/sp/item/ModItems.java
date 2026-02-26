/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1792
 *  net.minecraft.class_1792$class_1793
 *  net.minecraft.class_2378
 *  net.minecraft.class_2960
 *  net.minecraft.class_7923
 */
package com.sp.item;

import com.sp.DestroyingMinecraft;
import com.sp.item.custom.CameraShakeStick;
import com.sp.item.custom.WalkieTalkie;
import net.minecraft.class_1792;
import net.minecraft.class_2378;
import net.minecraft.class_2960;
import net.minecraft.class_7923;

public class ModItems {
    public static final class_1792 CAMERA_SHAKE_STICK_ITEM = ModItems.registerItem("camera_shake_stick", new CameraShakeStick(new class_1792.class_1793()));
    public static final class_1792 WALKIE_TALKIE_ITEM = ModItems.registerItem("walkie_talkie", new WalkieTalkie(new class_1792.class_1793().method_7889(1)));

    private static class_1792 registerItem(String name, class_1792 item) {
        return (class_1792)class_2378.method_10230((class_2378)class_7923.field_41178, (class_2960)DestroyingMinecraft.idOf(name), (Object)item);
    }

    public static void registerModItems() {
        DestroyingMinecraft.LOGGER.info("Registering Mod Items for destroying-minecraft");
    }
}

