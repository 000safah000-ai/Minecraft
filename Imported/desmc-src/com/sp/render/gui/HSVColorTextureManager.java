/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1011
 *  net.minecraft.class_1043
 *  net.minecraft.class_2960
 *  net.minecraft.class_310
 */
package com.sp.render.gui;

import net.minecraft.class_1011;
import net.minecraft.class_1043;
import net.minecraft.class_2960;
import net.minecraft.class_310;

public class HSVColorTextureManager {
    private static boolean init;
    private static class_1043 hsvTexture;
    private static class_1011 hsvImage;
    private static class_2960 hsvTextureIdentifier;
    private static class_1043 hueTexture;
    private static class_1011 hueImage;
    private static class_2960 hueTextureIdentifier;

    public static void init() {
        if (!init) {
            hsvTexture = new class_1043(255, 255, false);
            hsvImage = hsvTexture.method_4525();
            hsvTextureIdentifier = class_310.method_1551().method_1531().method_4617("hsv_color", hsvTexture);
            hueTexture = new class_1043(20, 255, false);
            hueImage = hueTexture.method_4525();
            hueTextureIdentifier = class_310.method_1551().method_1531().method_4617("hue_color", hueTexture);
            init = true;
        }
    }

    public static class_1043 getHsvTexture() {
        return hsvTexture;
    }

    public static class_1011 getHsvImage() {
        return hsvImage;
    }

    public static class_2960 getHsvTextureIdentifier() {
        return hsvTextureIdentifier;
    }

    public static class_1043 getHueTexture() {
        return hueTexture;
    }

    public static class_1011 getHueImage() {
        return hueImage;
    }

    public static class_2960 getHueTextureIdentifier() {
        return hueTextureIdentifier;
    }

    public static void upload() {
        hsvTexture.method_4524();
        hueTexture.method_4524();
    }
}

