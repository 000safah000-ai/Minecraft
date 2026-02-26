/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1297
 *  net.minecraft.class_1921
 *  net.minecraft.class_2338
 *  net.minecraft.class_238
 *  net.minecraft.class_243
 *  net.minecraft.class_4184
 *  net.minecraft.class_4587
 *  net.minecraft.class_4588
 *  net.minecraft.class_4597
 *  net.minecraft.class_863
 *  org.joml.Matrix4f
 */
package com.sp.util;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.class_1297;
import net.minecraft.class_1921;
import net.minecraft.class_2338;
import net.minecraft.class_238;
import net.minecraft.class_243;
import net.minecraft.class_4184;
import net.minecraft.class_4587;
import net.minecraft.class_4588;
import net.minecraft.class_4597;
import net.minecraft.class_863;
import org.joml.Matrix4f;

public class RenderUtil {
    public static void drawBlocksFromCorners(class_4587 matrices, class_4597 vertexConsumers, class_4184 camera, class_2338 corner1, class_2338 corner2, int red, int green, int blue, int alpha, boolean renderOutline, boolean inverted) {
        if (camera != null) {
            matrices.method_22904(-camera.method_19326().field_1352, -camera.method_19326().field_1351, -camera.method_19326().field_1350);
        }
        class_238 box = class_238.method_54784((class_2338)corner1, (class_2338)corner2);
        RenderUtil.drawBox(matrices, vertexConsumers, box, red, green, blue, alpha, renderOutline, inverted);
    }

    public static void drawBlocksFromCorners(class_4587 matrices, class_4597 vertexConsumers, class_4184 camera, class_243 corner1, class_243 corner2, int red, int green, int blue, int alpha, boolean renderOutline, boolean inverted) {
        if (camera != null) {
            matrices.method_22904(-camera.method_19326().field_1352, -camera.method_19326().field_1351, -camera.method_19326().field_1350);
        }
        class_238 box = new class_238(corner1, corner2);
        RenderUtil.drawBox(matrices, vertexConsumers, box, red, green, blue, alpha, renderOutline, inverted);
    }

    public static void drawBox(class_4587 matrices, class_4597 vertexConsumers, class_243 targetPos, double size, int red, int green, int blue, int alpha, boolean renderOutline, boolean inverted) {
        RenderUtil.drawBox(matrices, vertexConsumers, targetPos, new class_243(size, size, size), red, green, blue, alpha, renderOutline, inverted);
    }

    public static void drawBox(class_4587 matrices, class_4597 vertexConsumers, class_243 targetPos, class_243 size, int red, int green, int blue, int alpha, boolean renderOutline, boolean inverted) {
        class_238 box = class_238.method_30048((class_243)targetPos, (double)size.field_1352, (double)size.field_1351, (double)size.field_1350);
        RenderUtil.drawBox(matrices, vertexConsumers, box, red, green, blue, alpha, renderOutline, inverted);
    }

    public static void drawBox(class_4587 matrices, class_4597 vertexConsumers, class_238 box, int red, int green, int blue, int alpha, boolean renderOutline, boolean inverted) {
        if (inverted) {
            RenderUtil.drawInvertedBox(matrices, vertexConsumers, box, (float)red / 255.0f, (float)green / 255.0f, (float)blue / 255.0f, (float)alpha / 255.0f);
        } else {
            class_863.method_23102((class_4587)matrices, (class_4597)vertexConsumers, (class_238)box, (float)((float)red / 255.0f), (float)((float)green / 255.0f), (float)((float)blue / 255.0f), (float)((float)alpha / 255.0f));
        }
        if (renderOutline) {
            RenderUtil.drawOutline(matrices, vertexConsumers, box, 0.07f, red, green, blue);
        }
    }

    public static void drawEntityBox(class_4587 matrices, class_4597 vertexConsumers, class_243 targetPos, double size, class_1297 entity, int red, int green, int blue, int alpha) {
        class_243 offsetEntityPos = entity.method_19538().method_1031(0.0, 0.0, 0.0);
        class_863.method_23102((class_4587)matrices, (class_4597)vertexConsumers, (class_238)class_238.method_30048((class_243)targetPos, (double)size, (double)size, (double)size).method_989(-offsetEntityPos.field_1352, -offsetEntityPos.field_1351, -offsetEntityPos.field_1350), (float)((float)red / 255.0f), (float)((float)green / 255.0f), (float)((float)blue / 255.0f), (float)((float)alpha / 255.0f));
    }

    public static void drawInvertedBox(class_4587 matrices, class_4597 vertexConsumers, class_238 box, float red, float green, float blue, float alpha) {
        class_4588 vertexConsumer = vertexConsumers.getBuffer(class_1921.method_49047());
        Matrix4f matrix4f = matrices.method_23760().method_23761();
        float minX = (float)box.field_1323;
        float maxX = (float)box.field_1320;
        float minY = (float)box.field_1322;
        float maxY = (float)box.field_1325;
        float minZ = (float)box.field_1321;
        float maxZ = (float)box.field_1324;
        vertexConsumer.method_22918(matrix4f, minX, maxY, minZ).method_22915(red, green, blue, alpha);
        vertexConsumer.method_22918(matrix4f, maxX, maxY, minZ).method_22915(red, green, blue, alpha);
        vertexConsumer.method_22918(matrix4f, minX, maxY, maxZ).method_22915(red, green, blue, alpha);
        vertexConsumer.method_22918(matrix4f, maxX, maxY, maxZ).method_22915(red, green, blue, alpha);
        vertexConsumer.method_22918(matrix4f, maxX, minY, maxZ).method_22915(red, green, blue, alpha);
        vertexConsumer.method_22918(matrix4f, maxX, maxY, minZ).method_22915(red, green, blue, alpha);
        vertexConsumer.method_22918(matrix4f, maxX, minY, minZ).method_22915(red, green, blue, alpha);
        vertexConsumer.method_22918(matrix4f, minX, maxY, minZ).method_22915(red, green, blue, alpha);
        vertexConsumer.method_22918(matrix4f, minX, minY, minZ).method_22915(red, green, blue, alpha);
        vertexConsumer.method_22918(matrix4f, minX, maxY, maxZ).method_22915(red, green, blue, alpha);
        vertexConsumer.method_22918(matrix4f, minX, minY, maxZ).method_22915(red, green, blue, alpha);
        vertexConsumer.method_22918(matrix4f, maxX, minY, maxZ).method_22915(red, green, blue, alpha);
        vertexConsumer.method_22918(matrix4f, minX, minY, minZ).method_22915(red, green, blue, alpha);
        vertexConsumer.method_22918(matrix4f, maxX, minY, minZ).method_22915(red, green, blue, alpha);
    }

    public static void drawLine(class_4587 matrices, class_4597 vertexConsumers, class_243 camera, class_243 startPos, class_243 targetPos, int red, int green, int blue, int alpha) {
        class_4588 vertexConsumer = vertexConsumers.getBuffer(class_1921.method_49043((double)1.0));
        vertexConsumer.method_56824(matrices.method_23760(), (float)(startPos.field_1352 - camera.field_1352), (float)(startPos.field_1351 - camera.field_1351), (float)(startPos.field_1350 - camera.field_1350)).method_39415(RenderUtil.getArgb(alpha, red, green, blue));
        vertexConsumer.method_56824(matrices.method_23760(), (float)(targetPos.field_1352 - camera.field_1352), (float)(targetPos.field_1351 - camera.field_1351), (float)(targetPos.field_1350 - camera.field_1350)).method_39415(RenderUtil.getArgb(alpha, red, green, blue));
    }

    public static int getArgb(int alpha, int red, int green, int blue) {
        return alpha << 24 | red << 16 | green << 8 | blue;
    }

    public static int getRgb(int red, int green, int blue) {
        return red << 16 | green << 8 | blue;
    }

    public static void drawOutline(class_4587 matrices, class_4597 vertexConsumers, class_238 box, float thickness, int red, int green, int blue) {
        for (class_238 edge : RenderUtil.getEdges(box, thickness)) {
            class_863.method_23102((class_4587)matrices, (class_4597)vertexConsumers, (class_238)edge, (float)((float)red / 255.0f), (float)((float)green / 255.0f), (float)((float)blue / 255.0f), (float)1.0f);
        }
    }

    private static List<class_238> getEdges(class_238 box, double thickness) {
        ArrayList<class_238> edges = new ArrayList<class_238>();
        edges.add(class_238.method_30048((class_243)new class_243((box.field_1323 + box.field_1320) / 2.0, box.field_1322, box.field_1321), (double)(box.field_1323 - box.field_1320 - thickness), (double)thickness, (double)thickness));
        edges.add(class_238.method_30048((class_243)new class_243((box.field_1323 + box.field_1320) / 2.0, box.field_1325, box.field_1321), (double)(box.field_1323 - box.field_1320 - thickness), (double)thickness, (double)thickness));
        edges.add(class_238.method_30048((class_243)new class_243((box.field_1323 + box.field_1320) / 2.0, box.field_1322, box.field_1324), (double)(box.field_1323 - box.field_1320 - thickness), (double)thickness, (double)thickness));
        edges.add(class_238.method_30048((class_243)new class_243((box.field_1323 + box.field_1320) / 2.0, box.field_1325, box.field_1324), (double)(box.field_1323 - box.field_1320 - thickness), (double)thickness, (double)thickness));
        edges.add(class_238.method_30048((class_243)new class_243(box.field_1323, (box.field_1322 + box.field_1325) / 2.0, box.field_1321), (double)thickness, (double)(box.field_1322 - box.field_1325 - thickness), (double)thickness));
        edges.add(class_238.method_30048((class_243)new class_243(box.field_1320, (box.field_1322 + box.field_1325) / 2.0, box.field_1321), (double)thickness, (double)(box.field_1322 - box.field_1325 - thickness), (double)thickness));
        edges.add(class_238.method_30048((class_243)new class_243(box.field_1323, (box.field_1322 + box.field_1325) / 2.0, box.field_1324), (double)thickness, (double)(box.field_1322 - box.field_1325 - thickness), (double)thickness));
        edges.add(class_238.method_30048((class_243)new class_243(box.field_1320, (box.field_1322 + box.field_1325) / 2.0, box.field_1324), (double)thickness, (double)(box.field_1322 - box.field_1325 - thickness), (double)thickness));
        edges.add(class_238.method_30048((class_243)new class_243(box.field_1323, box.field_1322, (box.field_1321 + box.field_1324) / 2.0), (double)thickness, (double)thickness, (double)(box.field_1321 - box.field_1324 - thickness)));
        edges.add(class_238.method_30048((class_243)new class_243(box.field_1320, box.field_1322, (box.field_1321 + box.field_1324) / 2.0), (double)thickness, (double)thickness, (double)(box.field_1321 - box.field_1324 - thickness)));
        edges.add(class_238.method_30048((class_243)new class_243(box.field_1323, box.field_1325, (box.field_1321 + box.field_1324) / 2.0), (double)thickness, (double)thickness, (double)(box.field_1321 - box.field_1324 - thickness)));
        edges.add(class_238.method_30048((class_243)new class_243(box.field_1320, box.field_1325, (box.field_1321 + box.field_1324) / 2.0), (double)thickness, (double)thickness, (double)(box.field_1321 - box.field_1324 - thickness)));
        return edges;
    }
}

