/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.minecraft.class_1937
 */
package com.sp.util.tickinstances.client;

import java.util.Vector;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1937;

@Environment(value=EnvType.CLIENT)
public abstract class EndClientWorldTickInstances {
    private static final Vector<EndClientWorldTickInstances> allInstances = new Vector();

    public EndClientWorldTickInstances() {
        allInstances.add(this);
    }

    public abstract void tickClientWorld(class_1937 var1);

    public static Vector<EndClientWorldTickInstances> getInstances() {
        return allInstances;
    }
}

