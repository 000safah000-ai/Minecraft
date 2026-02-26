/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1297
 *  net.minecraft.class_238
 *  net.minecraft.class_243
 *  org.joml.Quaternionf
 */
package com.sp.collision;

import com.sp.entity.custom.BlockPhysicsEntity;
import com.sp.util.MathUtil;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import net.minecraft.class_1297;
import net.minecraft.class_238;
import net.minecraft.class_243;
import org.joml.Quaternionf;

public class BlockOBB {
    public Quaternionf rotation;
    public BlockPhysicsEntity.BlockData blockData;
    public class_243 halfSize = new class_243(0.5, 0.5, 0.5);

    public BlockOBB(Quaternionf rotation, BlockPhysicsEntity.BlockData blockData) {
        this.rotation = rotation;
        this.blockData = blockData;
    }

    public class_243[] getLocalCorners() {
        class_243[] corners = new class_243[]{new class_243(this.halfSize.field_1352, this.halfSize.field_1351, this.halfSize.field_1350), new class_243(-this.halfSize.field_1352, this.halfSize.field_1351, this.halfSize.field_1350), new class_243(-this.halfSize.field_1352, this.halfSize.field_1351, -this.halfSize.field_1350), new class_243(this.halfSize.field_1352, this.halfSize.field_1351, -this.halfSize.field_1350), new class_243(this.halfSize.field_1352, -this.halfSize.field_1351, this.halfSize.field_1350), new class_243(-this.halfSize.field_1352, -this.halfSize.field_1351, this.halfSize.field_1350), new class_243(-this.halfSize.field_1352, -this.halfSize.field_1351, -this.halfSize.field_1350), new class_243(this.halfSize.field_1352, -this.halfSize.field_1351, -this.halfSize.field_1350)};
        for (int i = 0; i < corners.length; ++i) {
            class_243 corner = corners[i];
            class_243 newCornerPos = MathUtil.toVec3d(this.rotation.transform(MathUtil.toVector3d(corner.method_1019(new class_243(this.blockData.offset.method_10216(), this.blockData.offset.method_10214(), this.blockData.offset.method_10215())))));
            corners[i] = newCornerPos.method_1031(-0.5, -0.5, -0.5);
        }
        return corners;
    }

    public List<class_243> getGlobalCorners(class_1297 entity) {
        return Arrays.stream(this.getLocalCorners()).map(corner -> corner.method_1019(entity.method_19538())).collect(Collectors.toList());
    }

    public static List<class_243> getAABBCorners(class_238 aabb) {
        ArrayList<class_243> corners = new ArrayList<class_243>();
        corners.add(new class_243(aabb.field_1323 - 0.5, aabb.field_1322 - 0.5, aabb.field_1321 - 0.5));
        corners.add(new class_243(aabb.field_1320 - 0.5, aabb.field_1322 - 0.5, aabb.field_1321 - 0.5));
        corners.add(new class_243(aabb.field_1323 - 0.5, aabb.field_1325 - 0.5, aabb.field_1321 - 0.5));
        corners.add(new class_243(aabb.field_1320 - 0.5, aabb.field_1325 - 0.5, aabb.field_1321 - 0.5));
        corners.add(new class_243(aabb.field_1323 - 0.5, aabb.field_1322 - 0.5, aabb.field_1324 - 0.5));
        corners.add(new class_243(aabb.field_1320 - 0.5, aabb.field_1322 - 0.5, aabb.field_1324 - 0.5));
        corners.add(new class_243(aabb.field_1323 - 0.5, aabb.field_1325 - 0.5, aabb.field_1324 - 0.5));
        corners.add(new class_243(aabb.field_1320 - 0.5, aabb.field_1325 - 0.5, aabb.field_1324 - 0.5));
        return corners;
    }

    public List<class_243> getNormalAxis() {
        class_243[] axis = new class_243[]{new class_243(1.0, 0.0, 0.0), new class_243(0.0, 1.0, 0.0), new class_243(0.0, 0.0, 1.0)};
        for (int i = 0; i < axis.length; ++i) {
            class_243 axisVec = axis[i];
            axis[i] = MathUtil.toVec3d(this.rotation.transform(axisVec.method_46409())).method_1029();
        }
        return Arrays.asList(axis);
    }

    public static List<class_243> getAABBNormalAxis() {
        ArrayList<class_243> axis = new ArrayList<class_243>();
        axis.add(new class_243(1.0, 0.0, 0.0));
        axis.add(new class_243(0.0, 1.0, 0.0));
        axis.add(new class_243(0.0, 0.0, 1.0));
        return axis;
    }

    public List<class_243> getCrossProductAxis(List<class_243> obbAxis, List<class_243> aabbAxis) {
        ArrayList<class_243> crossProductAxis = new ArrayList<class_243>();
        for (class_243 obb : obbAxis) {
            for (class_243 aabb : aabbAxis) {
                class_243 crossProduct = obb.method_1036(aabb);
                class_243 norm = crossProduct.method_1029();
                crossProductAxis.add(norm);
            }
        }
        return crossProductAxis;
    }

    public List<CollisionData> getAllCollisionAxisWith(class_238 aabb, class_1297 entity) {
        ArrayList<CollisionData> collisions = new ArrayList<CollisionData>();
        List<class_243> obbCorners = this.getGlobalCorners(entity);
        List<class_243> aabbCorners = BlockOBB.getAABBCorners(aabb);
        List<class_243> allAxis = BlockOBB.getAABBNormalAxis();
        allAxis.addAll(this.getNormalAxis());
        allAxis.addAll(this.getCrossProductAxis(this.getNormalAxis(), BlockOBB.getAABBNormalAxis()));
        for (class_243 axis : allAxis) {
            double projection;
            if (axis.method_1027() < 1.0E-10) continue;
            double obbMin = Double.MAX_VALUE;
            double obbMax = -1.7976931348623157E308;
            double aabbMin = Double.MAX_VALUE;
            double aabbMax = -1.7976931348623157E308;
            for (class_243 corner : obbCorners) {
                projection = axis.method_1026(corner);
                obbMin = Math.min(obbMin, projection);
                obbMax = Math.max(obbMax, projection);
            }
            for (class_243 aabbCorner : aabbCorners) {
                projection = axis.method_1026(aabbCorner);
                aabbMin = Math.min(aabbMin, projection);
                aabbMax = Math.max(aabbMax, projection);
            }
            double overlap = Math.min(obbMax - aabbMin, aabbMax - obbMin);
            if (!(overlap > 1.0)) continue;
            collisions.add(new CollisionData(overlap, axis, true));
        }
        return collisions;
    }

    public boolean collidesWith(class_238 aabb, class_1297 entity) {
        List<class_243> obbCorners = this.getGlobalCorners(entity);
        List<class_243> aabbCorners = BlockOBB.getAABBCorners(aabb);
        List<class_243> allAxis = BlockOBB.getAABBNormalAxis();
        allAxis.addAll(this.getNormalAxis());
        allAxis.addAll(this.getCrossProductAxis(this.getNormalAxis(), BlockOBB.getAABBNormalAxis()));
        for (class_243 axis : allAxis) {
            double projection;
            if (axis.method_1027() < 1.0E-10) continue;
            double obbMin = Double.MAX_VALUE;
            double obbMax = -1.7976931348623157E308;
            double aabbMin = Double.MAX_VALUE;
            double aabbMax = -1.7976931348623157E308;
            for (class_243 corner : obbCorners) {
                projection = axis.method_1026(corner);
                obbMin = Math.min(obbMin, projection);
                obbMax = Math.max(obbMax, projection);
            }
            for (class_243 aabbCorner : aabbCorners) {
                projection = axis.method_1026(aabbCorner);
                aabbMin = Math.min(aabbMin, projection);
                aabbMax = Math.max(aabbMax, projection);
            }
            if (!(obbMax < aabbMin) && !(obbMin > aabbMax)) continue;
            return false;
        }
        return true;
    }

    public CollisionData getYAxisCollisionWith(class_238 aabb, class_1297 entity) {
        double overlap;
        double projection;
        List<class_243> obbCorners = this.getGlobalCorners(entity);
        List<class_243> aabbCorners = BlockOBB.getAABBCorners(aabb);
        double minOverlap = Double.MAX_VALUE;
        class_243 minAxis = null;
        boolean collides = true;
        class_243 axis = new class_243(0.0, 1.0, 0.0);
        double obbMin = Double.MAX_VALUE;
        double obbMax = -1.7976931348623157E308;
        double aabbMin = Double.MAX_VALUE;
        double aabbMax = -1.7976931348623157E308;
        for (class_243 corner : obbCorners) {
            projection = axis.method_1026(corner);
            obbMin = Math.min(obbMin, projection);
            obbMax = Math.max(obbMax, projection);
        }
        for (class_243 aabbCorner : aabbCorners) {
            projection = axis.method_1026(aabbCorner);
            aabbMin = Math.min(aabbMin, projection);
            aabbMax = Math.max(aabbMax, projection);
        }
        if (obbMax < aabbMin || obbMin > aabbMax) {
            collides = false;
        }
        if ((overlap = Math.min(obbMax - aabbMin, aabbMax - obbMin)) < minOverlap) {
            minOverlap = overlap;
            minAxis = axis;
        }
        return new CollisionData(minOverlap, minAxis, collides);
    }

    public CollisionData getMinCollisionWith(class_238 aabb, class_1297 entity) {
        List<class_243> obbCorners = this.getGlobalCorners(entity);
        List<class_243> aabbCorners = BlockOBB.getAABBCorners(aabb);
        List<class_243> allAxis = BlockOBB.getAABBNormalAxis();
        allAxis.addAll(this.getNormalAxis());
        allAxis.addAll(this.getCrossProductAxis(this.getNormalAxis(), BlockOBB.getAABBNormalAxis()));
        double minOverlap = Double.MAX_VALUE;
        class_243 minAxis = null;
        boolean collides = true;
        for (class_243 axis : allAxis) {
            double overlap;
            double projection;
            if (axis.method_1027() < 1.0E-10) continue;
            double obbMin = Double.MAX_VALUE;
            double obbMax = Double.MIN_VALUE;
            double aabbMin = Double.MAX_VALUE;
            double aabbMax = Double.MIN_VALUE;
            for (class_243 corner : obbCorners) {
                projection = axis.method_1026(corner);
                obbMin = Math.min(obbMin, projection);
                obbMax = Math.max(obbMax, projection);
            }
            for (class_243 aabbCorner : aabbCorners) {
                projection = axis.method_1026(aabbCorner);
                aabbMin = Math.min(aabbMin, projection);
                aabbMax = Math.max(aabbMax, projection);
            }
            if (obbMax < aabbMin || obbMin > aabbMax) {
                collides = false;
            }
            if (!((overlap = Math.min(obbMax - aabbMin, aabbMax - obbMin)) < minOverlap)) continue;
            minOverlap = overlap;
            minAxis = axis;
        }
        return new CollisionData(minOverlap, minAxis, collides);
    }

    public record CollisionData(double overLapp, class_243 axis, boolean collides) {
    }

    public record RayCollisionResult(double tMin, double tMax, boolean collide) {
    }
}

