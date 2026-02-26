/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_2338
 *  net.minecraft.class_243
 *  net.minecraft.class_3532
 *  net.minecraft.class_5819
 *  org.joml.Vector3d
 *  org.joml.Vector3f
 */
package com.sp.util;

import java.util.List;
import net.minecraft.class_2338;
import net.minecraft.class_243;
import net.minecraft.class_3532;
import net.minecraft.class_5819;
import org.joml.Vector3d;
import org.joml.Vector3f;

public class MathUtil {
    private static final class_5819 random = class_5819.method_43047();

    public static <E> E randomValueInList(List<E> list) {
        return list.get(random.method_39332(0, list.size() - 1));
    }

    public static <E> E randomValueInList(E[] array) {
        return array[random.method_39332(0, array.length - 1)];
    }

    public static float nextBetween(float min, float max) {
        return min + random.method_43057() * (max - min);
    }

    public static float Lerp(float source, float destination, float smoothingFactor, float delta) {
        return class_3532.method_16439((float)(1.0f - (float)Math.pow(smoothingFactor, delta)), (float)source, (float)destination);
    }

    public static class_243 getCenterPos(List<class_2338> blocks) {
        double sumX = 0.0;
        double sumY = 0.0;
        double sumZ = 0.0;
        for (class_2338 blockPos : blocks) {
            sumX += (double)blockPos.method_10263();
            sumY += (double)blockPos.method_10264();
            sumZ += (double)blockPos.method_10260();
        }
        int count = blocks.size();
        return new class_243((sumX /= (double)count) + 0.5, (sumY /= (double)count) + 0.5, (sumZ /= (double)count) + 0.5);
    }

    public static Vector3d toVector3d(class_243 vec) {
        return new Vector3d(vec.field_1352, vec.field_1351, vec.field_1350);
    }

    public static class_243 toVec3d(Vector3d vec) {
        return new class_243(vec.x, vec.y, vec.z);
    }

    public static class_243 toVec3d(Vector3f vec) {
        return new class_243((double)vec.x, (double)vec.y, (double)vec.z);
    }
}

