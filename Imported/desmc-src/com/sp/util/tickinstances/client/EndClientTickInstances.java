/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.minecraft.class_310
 */
package com.sp.util.tickinstances.client;

import java.util.Vector;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_310;

@Environment(value=EnvType.CLIENT)
public abstract class EndClientTickInstances {
    private static final Vector<EndClientTickInstances> allInstances = new Vector();

    public EndClientTickInstances() {
        allInstances.add(this);
    }

    public abstract void tickClient(class_310 var1);

    public static Vector<EndClientTickInstances> getInstances() {
        return allInstances;
    }
}

