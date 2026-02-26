/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1297
 *  net.minecraft.class_1299
 *  net.minecraft.class_1937
 *  net.minecraft.class_2246
 *  net.minecraft.class_2248
 *  net.minecraft.class_2338
 *  net.minecraft.class_2374
 *  net.minecraft.class_238
 *  net.minecraft.class_2382
 *  net.minecraft.class_243
 *  net.minecraft.class_2487
 *  net.minecraft.class_2499
 *  net.minecraft.class_2512
 *  net.minecraft.class_2520
 *  net.minecraft.class_2540
 *  net.minecraft.class_2680
 *  net.minecraft.class_2940
 *  net.minecraft.class_2941
 *  net.minecraft.class_2943
 *  net.minecraft.class_2945
 *  net.minecraft.class_2945$class_7834
 *  net.minecraft.class_2945$class_9222
 *  net.minecraft.class_3218
 *  net.minecraft.class_3419
 *  net.minecraft.class_7225
 *  net.minecraft.class_7924
 *  org.joml.Quaternionf
 *  org.joml.Vector3d
 *  org.joml.Vector3f
 */
package com.sp.entity.custom;

import com.sp.cca.InitializeComponents;
import com.sp.cca.custom.entity.PhysicsBlockComponent;
import com.sp.collision.BlockOBB;
import com.sp.entity.ModEntities;
import com.sp.sounds.ModSounds;
import com.sp.util.MathUtil;
import com.sp.world.ModGameRules;
import com.sp.world.spinningblockexplosion.custom.PointSBE;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.class_1297;
import net.minecraft.class_1299;
import net.minecraft.class_1937;
import net.minecraft.class_2246;
import net.minecraft.class_2248;
import net.minecraft.class_2338;
import net.minecraft.class_2374;
import net.minecraft.class_238;
import net.minecraft.class_2382;
import net.minecraft.class_243;
import net.minecraft.class_2487;
import net.minecraft.class_2499;
import net.minecraft.class_2512;
import net.minecraft.class_2520;
import net.minecraft.class_2540;
import net.minecraft.class_2680;
import net.minecraft.class_2940;
import net.minecraft.class_2941;
import net.minecraft.class_2943;
import net.minecraft.class_2945;
import net.minecraft.class_3218;
import net.minecraft.class_3419;
import net.minecraft.class_7225;
import net.minecraft.class_7924;
import org.joml.Quaternionf;
import org.joml.Vector3d;
import org.joml.Vector3f;

public class BlockPhysicsEntity
extends class_1297 {
    private static final class_2940<class_2487> BLOCKS = class_2945.method_12791(BlockPhysicsEntity.class, (class_2941)class_2943.field_13318);
    public PhysicsBlockComponent component;
    private List<BlockData> blocks = new ArrayList<BlockData>();
    private boolean markForDiscard;
    private int startDiscardAge;

    public static BlockPhysicsEntity ofBlocks(class_1937 world, class_2338 corner1, class_2338 corner2) {
        ArrayList<class_2338> positions = new ArrayList<class_2338>();
        class_2338.method_20437((class_2338)corner2, (class_2338)corner1).forEach(blockPos -> positions.add((class_2338)blockPos.method_25503()));
        return BlockPhysicsEntity.ofBlocks(world, positions);
    }

    public static BlockPhysicsEntity ofBlocks(class_1937 world, List<class_2338> blocks) {
        BlockPhysicsEntity entity = new BlockPhysicsEntity(ModEntities.BLOCK_PHYSICS_ENTITY, world);
        class_243 pos = MathUtil.getCenterPos(blocks);
        entity.method_33574(pos);
        for (class_2338 blockPos : blocks) {
            class_2680 state = world.method_8320(blockPos);
            class_243 relativePos = class_243.method_24954((class_2382)blockPos).method_1020(pos).method_1031(0.5, 0.5, 0.5);
            if (!state.method_26215()) {
                entity.addBlock(new BlockData(state, relativePos));
            }
            if (!world.method_8450().method_8355(ModGameRules.ALLOW_EXPLOSIONS)) continue;
            world.method_8501(blockPos, class_2246.field_10124.method_9564());
        }
        world.method_8649((class_1297)entity);
        return entity;
    }

    public void setDown() {
        class_2338 blockPos = this.method_24515();
        class_243 directionOffset = new class_243((double)this.method_5755().method_10148(), (double)this.method_5755().method_10164(), (double)this.method_5755().method_10165());
        class_243 fracBlockPos = this.method_19538().method_1020(class_243.method_24954((class_2382)blockPos)).method_18806(directionOffset);
        int xOffset = fracBlockPos.field_1352 >= (double)0.85f ? 1 : 0;
        int yOffset = fracBlockPos.field_1351 >= (double)0.85f ? 1 : 0;
        int zOffset = fracBlockPos.field_1350 >= (double)0.85f ? 1 : 0;
        blockPos = blockPos.method_10069(xOffset, yOffset, zOffset);
        for (BlockData blockData : this.getBlocks()) {
            this.method_37908().method_8501(blockPos.method_10081((class_2382)class_2338.method_49638((class_2374)blockData.offset)), blockData.blockState);
        }
    }

    public BlockPhysicsEntity(class_1299<?> type, class_1937 world) {
        super(type, world);
        this.component = (PhysicsBlockComponent)InitializeComponents.PHYSICS_BLOCK.get((Object)this);
    }

    public void method_5773() {
        super.method_5773();
        if (!this.method_37908().field_9236) {
            if (this.component.isMeteorLike()) {
                if (this.field_6012 == 3) {
                    this.method_37908().method_43129(null, (class_1297)this, ModSounds.METEOR_WHISTLE, class_3419.field_15256, 10.0f, MathUtil.nextBetween(0.6f, 1.2f));
                }
                if (this.method_55667().method_51367()) {
                    this.method_5783(ModSounds.METEOR_IMPACT, 100.0f, 1.2f / (this.field_5974.method_43057() * 0.2f + 0.9f));
                    PointSBE explosion = new PointSBE(this.field_5974.method_39332(4, 7), 0.2f, this.method_19538());
                    explosion.beginExplosion((class_3218)this.method_37908());
                    this.method_31472();
                }
            }
            if (this.markForDiscard && this.field_6012 - this.startDiscardAge >= 2) {
                this.method_31472();
            }
        }
        this.method_5857(this.method_33332());
        Vector3f rotationSpeed = this.component.getRotationSpeed();
        this.component.getRotation().rotateLocalX((float)Math.toRadians(rotationSpeed.x));
        this.component.getRotation().rotateLocalY((float)Math.toRadians(rotationSpeed.y));
        this.component.getRotation().rotateLocalZ((float)Math.toRadians(rotationSpeed.z));
        this.move();
    }

    public List<BlockOBB.CollisionData> getAllCollisions(class_238 aabb) {
        ArrayList<BlockOBB.CollisionData> collisions = new ArrayList<BlockOBB.CollisionData>();
        for (BlockData block : this.getBlocks()) {
            BlockOBB obb = new BlockOBB(this.component.getRotation(), block);
            BlockOBB.CollisionData collisionData = obb.getMinCollisionWith(aabb, this);
            if (!collisionData.collides()) continue;
            collisions.add(collisionData);
        }
        return collisions;
    }

    public double getYAxisCollision(class_238 aabb) {
        double bestOffset = 0.0;
        for (BlockData block : this.getBlocks()) {
            BlockOBB.CollisionData collisionData;
            BlockOBB obb = new BlockOBB(this.component.getRotation(), block);
            if (!obb.collidesWith(aabb, this) || !(collisionData = obb.getMinCollisionWith(aabb.method_997(new class_243(0.0, bestOffset, 0.0)), this)).collides() || !(collisionData.overLapp() > 0.0)) continue;
            bestOffset += collisionData.overLapp();
        }
        return bestOffset;
    }

    public class_243 getBestCollisionOffset(class_238 aabb, class_243 movement) {
        class_243 offset = movement;
        for (BlockData block : this.getBlocks()) {
            BlockOBB.CollisionData collisionData;
            BlockOBB obb = new BlockOBB(this.component.getRotation(), block);
            if (!obb.collidesWith(aabb.method_997(offset), this) || !(collisionData = obb.getMinCollisionWith(aabb.method_997(offset), this)).collides()) continue;
            class_243 resolveAxis = collisionData.axis();
            double dot = movement.method_1026(resolveAxis);
            if (dot > 0.0) {
                resolveAxis = resolveAxis.method_22882();
            }
            offset = offset.method_1019(resolveAxis.method_1021(collisionData.overLapp()));
        }
        return offset;
    }

    public boolean collides(class_238 aabb) {
        for (BlockData block : this.getBlocks()) {
            BlockOBB obb = new BlockOBB(this.component.getRotation(), block);
            if (!obb.collidesWith(aabb, this)) continue;
            return true;
        }
        return false;
    }

    public void move() {
        this.method_33574(this.method_19538().method_1019(this.method_18798()));
    }

    protected class_238 method_33332() {
        if (this.component == null || this.getBlocks() == null || this.getBlocks().isEmpty()) {
            return super.method_33332();
        }
        class_243 min = new class_243(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);
        class_243 max = new class_243(Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY);
        for (BlockData blockData : this.getBlocks()) {
            for (double dx = 0.0; dx <= 1.0; dx += 1.0) {
                for (double dy = 0.0; dy <= 1.0; dy += 1.0) {
                    for (double dz = 0.0; dz <= 1.0; dz += 1.0) {
                        class_243 corner = new class_243(blockData.offset.method_10216() + dx - 0.5, blockData.offset.method_10214() + dy - 0.5, blockData.offset.method_10215() + dz - 0.5);
                        class_243 rotatedCorner = this.rotateVector(corner, this.component.getRotation());
                        min = new class_243(Math.min(min.field_1352, rotatedCorner.field_1352), Math.min(min.field_1351, rotatedCorner.field_1351), Math.min(min.field_1350, rotatedCorner.field_1350));
                        max = new class_243(Math.max(max.field_1352, rotatedCorner.field_1352), Math.max(max.field_1351, rotatedCorner.field_1351), Math.max(max.field_1350, rotatedCorner.field_1350));
                    }
                }
            }
        }
        min = min.method_1019(this.method_19538());
        max = max.method_1019(this.method_19538());
        return new class_238(min, max);
    }

    private class_243 rotateVector(class_243 vec, Quaternionf quaternion) {
        Vector3d result = quaternion.transform(new Vector3d(vec.field_1352, vec.field_1351, vec.field_1350));
        return new class_243(result.x, result.y, result.z);
    }

    public void markForDiscard() {
        this.markForDiscard = true;
        this.method_18800(0.0, 0.0, 0.0);
        this.field_6037 = true;
        this.field_6007 = true;
        this.startDiscardAge = this.field_6012;
    }

    public void method_5674(class_2940<?> data) {
        if (data.equals(BLOCKS) && this.method_37908().field_9236) {
            class_2487 compound = (class_2487)this.field_6011.method_12789(BLOCKS);
            class_2499 blockList = compound.method_10554("blocks_list", 10);
            this.setBlocks(blockList.stream().map(nbtElement -> {
                class_2487 blockNbt = (class_2487)nbtElement;
                return BlockData.fromNBT(blockNbt, (class_7225<class_2248>)this.method_37908().method_45448(class_7924.field_41254));
            }).toList());
        }
        super.method_5674(data);
    }

    public void method_48850(List<class_2945.class_7834<?>> entries) {
        super.method_48850(entries);
    }

    public void setBlocks(List<BlockData> blocks) {
        this.blocks = blocks;
        class_2487 nbtCompound = new class_2487();
        class_2499 blockList = new class_2499();
        for (BlockData blockData : blocks) {
            blockList.add((Object)blockData.asNBT());
        }
        nbtCompound.method_10566("blocks_list", (class_2520)blockList);
        this.field_6011.method_12778(BLOCKS, (Object)nbtCompound);
    }

    public void addBlock(BlockData blockData) {
        this.blocks.add(blockData);
        this.setBlocks(this.blocks);
    }

    public List<BlockData> getBlocks() {
        return this.blocks;
    }

    protected void method_5693(class_2945.class_9222 builder) {
        builder.method_56912(BLOCKS, (Object)new class_2487());
    }

    protected void method_5652(class_2487 nbt) {
        nbt.method_10566("blocks", (class_2520)((class_2487)this.field_6011.method_12789(BLOCKS)).method_10554("blocks_list", 10));
    }

    protected void method_5749(class_2487 nbt) {
        if (nbt.method_10545("blocks")) {
            class_2499 blockList = nbt.method_10554("blocks", 10);
            this.setBlocks(blockList.stream().map(nbtElement -> {
                class_2487 blockNbt = (class_2487)nbtElement;
                return BlockData.fromNBT(blockNbt, (class_7225<class_2248>)this.method_37908().method_45448(class_7924.field_41254));
            }).toList());
        }
    }

    public static class BlockData {
        public class_2680 blockState;
        public class_243 offset;
        public class_2540 buf;

        public BlockData(class_2680 blockState, class_243 offset) {
            this.blockState = blockState;
            this.offset = offset;
        }

        public BlockData(class_2540 buf) {
            this.buf = buf;
        }

        public class_2487 asNBT() {
            class_2487 nbt = new class_2487();
            nbt.method_10566("block", (class_2520)class_2512.method_10686((class_2680)this.blockState));
            nbt.method_10549("offsetX", this.offset.method_10216());
            nbt.method_10549("offsetY", this.offset.method_10214());
            nbt.method_10549("offsetZ", this.offset.method_10215());
            return nbt;
        }

        public static BlockData fromNBT(class_2487 nbt, class_7225<class_2248> wrapper) {
            class_2487 blockStateCompound = nbt.method_10562("block");
            class_2680 blockState1 = class_2512.method_10681(wrapper, (class_2487)blockStateCompound);
            class_243 offset = new class_243(nbt.method_10574("offsetX"), nbt.method_10574("offsetY"), nbt.method_10574("offsetZ"));
            return new BlockData(blockState1, offset);
        }
    }
}

