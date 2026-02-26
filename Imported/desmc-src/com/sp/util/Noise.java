/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_243
 *  org.joml.Vector2d
 *  org.joml.Vector3d
 *  org.joml.Vector3dc
 *  org.joml.Vector4d
 *  org.joml.Vector4dc
 */
package com.sp.util;

import net.minecraft.class_243;
import org.joml.Vector2d;
import org.joml.Vector3d;
import org.joml.Vector3dc;
import org.joml.Vector4d;
import org.joml.Vector4dc;

public class Noise {
    public static float getCrackNoise(class_243 pos) {
        float noise = Noise.fbm(pos.method_1021(0.5), 7) * 2.0f - 1.0f;
        float noise2 = -noise;
        return Math.min(Math.max(noise, noise2) + Noise.fbm(pos.method_1021(0.3), 1), 1.0f);
    }

    private static Vector4d mod289(Vector4d x) {
        Vector4d result = new Vector4d();
        result.x = x.x - Math.floor(x.x * 0.0034602076124567475) * 289.0;
        result.y = x.y - Math.floor(x.y * 0.0034602076124567475) * 289.0;
        result.z = x.z - Math.floor(x.z * 0.0034602076124567475) * 289.0;
        result.w = x.w - Math.floor(x.w * 0.0034602076124567475) * 289.0;
        return result;
    }

    private static Vector4d perm(Vector4d x) {
        Vector4d temp = new Vector4d((Vector4dc)x);
        temp.mul(34.0);
        temp.add(1.0, 1.0, 1.0, 1.0);
        temp.mul((Vector4dc)x);
        return Noise.mod289(temp);
    }

    public static float perlinNoise(class_243 p) {
        Vector3d a = new Vector3d(Math.floor(p.field_1352), Math.floor(p.field_1351), Math.floor(p.field_1350));
        Vector3d d = new Vector3d(p.field_1352 - a.x, p.field_1351 - a.y, p.field_1350 - a.z);
        Vector3d d2 = new Vector3d((Vector3dc)d).mul((Vector3dc)d);
        Vector3d temp = new Vector3d(3.0 - 2.0 * d.x, 3.0 - 2.0 * d.y, 3.0 - 2.0 * d.z);
        d = new Vector3d((Vector3dc)d2).mul((Vector3dc)temp);
        Vector4d b = new Vector4d(a.x, a.x, a.y, a.y);
        b.add((Vector4dc)new Vector4d(0.0, 1.0, 0.0, 1.0));
        Vector4d k1 = Noise.perm(new Vector4d(b.x, b.y, b.x, b.y));
        Vector4d k1xy = new Vector4d(k1.x, k1.y, k1.x, k1.y);
        Vector4d bzzww = new Vector4d(b.z, b.z, b.w, b.w);
        Vector4d k2 = Noise.perm(new Vector4d((Vector4dc)k1xy).add((Vector4dc)bzzww));
        Vector4d c = new Vector4d((Vector4dc)k2);
        c.add((Vector4dc)new Vector4d(a.z, a.z, a.z, a.z));
        Vector4d k3 = Noise.perm(c);
        Vector4d k4 = Noise.perm(new Vector4d((Vector4dc)c).add(1.0, 1.0, 1.0, 1.0));
        Vector4d o1 = Noise.fract(new Vector4d((Vector4dc)k3).mul(0.024390243902439025));
        Vector4d o2 = Noise.fract(new Vector4d((Vector4dc)k4).mul(0.024390243902439025));
        Vector4d o3 = new Vector4d();
        o3.add((Vector4dc)new Vector4d((Vector4dc)o2).mul(d.z));
        o3.add((Vector4dc)new Vector4d((Vector4dc)o1).mul(1.0 - d.z));
        Vector2d o4 = new Vector2d();
        o4.x = o3.y * d.x + o3.x * (1.0 - d.x);
        o4.y = o3.w * d.x + o3.z * (1.0 - d.x);
        return (float)(o4.y * d.y + o4.x * (1.0 - d.y));
    }

    public static float fbm(class_243 x, int iterations) {
        float v = 0.0f;
        float a = 0.5f;
        class_243 shift = new class_243(100.0, 100.0, 100.0);
        for (int i = 0; i < iterations; ++i) {
            v += a * Noise.perlinNoise(x);
            x = x.method_1021(2.0).method_1019(shift);
            a *= 0.5f;
        }
        return v;
    }

    private static Vector4d fract(Vector4d v) {
        Vector4d result = new Vector4d();
        result.x = v.x - Math.floor(v.x);
        result.y = v.y - Math.floor(v.y);
        result.z = v.z - Math.floor(v.z);
        result.w = v.w - Math.floor(v.w);
        return result;
    }
}

