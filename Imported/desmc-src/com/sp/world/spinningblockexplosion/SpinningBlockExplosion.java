/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_243
 *  net.minecraft.class_3218
 *  net.minecraft.class_5819
 */
package com.sp.world.spinningblockexplosion;

import java.util.Vector;
import net.minecraft.class_243;
import net.minecraft.class_3218;
import net.minecraft.class_5819;

public abstract class SpinningBlockExplosion {
    protected final class_243 position;
    protected boolean explode;
    protected int progress;
    protected class_3218 world;
    protected final class_5819 random = class_5819.method_43047();
    private static final Vector<SpinningBlockExplosion> explosions = new Vector();

    public SpinningBlockExplosion(class_243 position) {
        this.position = position;
        explosions.add(this);
    }

    public void explode() {
        if (!this.explode) {
            return;
        }
    }

    public void beginExplosion(class_3218 world) {
        this.world = world;
        this.explode = true;
    }

    public class_3218 getWorld() {
        return this.world;
    }

    public static synchronized Vector<SpinningBlockExplosion> getExplosions() {
        return (Vector)explosions.clone();
    }

    public static synchronized void removeExplosion(SpinningBlockExplosion explosion) {
        explosions.remove(explosion);
    }
}

