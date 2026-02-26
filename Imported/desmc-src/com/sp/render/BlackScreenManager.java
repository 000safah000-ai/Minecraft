/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_310
 */
package com.sp.render;

import net.minecraft.class_310;

public class BlackScreenManager {
    private static boolean isBlackScreen;

    public static boolean isBlackScreen() {
        return isBlackScreen;
    }

    public static void setBlackScreen(boolean isBlackScreen) {
        BlackScreenManager.isBlackScreen = isBlackScreen;
        class_310.method_1551().field_1690.field_1842 = isBlackScreen;
    }
}

