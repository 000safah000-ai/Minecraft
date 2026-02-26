/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_2338
 *  net.minecraft.class_2350
 */
package com.sp.mixininterfaces;

import java.util.Optional;
import net.minecraft.class_2338;
import net.minecraft.class_2350;

public interface LayingDownPlayerEntity {
    public boolean isLayingDown();

    public void setLayingDown(boolean var1);

    public Optional<class_2338> getLayingDownPos();

    public void setLayingDownPos(class_2338 var1);

    public class_2350 getLayingDownDirection();

    public void getUp();
}

