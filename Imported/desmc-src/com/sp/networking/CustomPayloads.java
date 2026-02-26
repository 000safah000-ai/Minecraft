/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_2338
 *  net.minecraft.class_2350
 *  net.minecraft.class_3414
 *  net.minecraft.class_8710
 *  net.minecraft.class_8710$class_9154
 *  net.minecraft.class_9129
 *  net.minecraft.class_9135
 *  net.minecraft.class_9139
 *  org.joml.Vector3f
 */
package com.sp.networking;

import com.sp.DestroyingMinecraft;
import net.minecraft.class_2338;
import net.minecraft.class_2350;
import net.minecraft.class_3414;
import net.minecraft.class_8710;
import net.minecraft.class_9129;
import net.minecraft.class_9135;
import net.minecraft.class_9139;
import org.joml.Vector3f;

public class CustomPayloads {

    public record LavaSpewPacketPayload(Vector3f position) implements class_8710
    {
        public static final class_8710.class_9154<LavaSpewPacketPayload> ID = new class_8710.class_9154(DestroyingMinecraft.idOf("lvaspw"));
        public static final class_9139<class_9129, LavaSpewPacketPayload> CODEC = class_9139.method_56434((class_9139)class_9135.field_48558, LavaSpewPacketPayload::position, LavaSpewPacketPayload::new);

        public class_8710.class_9154<? extends class_8710> method_56479() {
            return ID;
        }
    }

    public record WaitingRoomPacketPayload(boolean setInWaitingRoom) implements class_8710
    {
        public static final class_8710.class_9154<WaitingRoomPacketPayload> ID = new class_8710.class_9154(DestroyingMinecraft.idOf("wtingrm"));
        public static final class_9139<class_9129, WaitingRoomPacketPayload> CODEC = class_9139.method_56434((class_9139)class_9135.field_48547, WaitingRoomPacketPayload::setInWaitingRoom, WaitingRoomPacketPayload::new);

        public class_8710.class_9154<? extends class_8710> method_56479() {
            return ID;
        }
    }

    public record UpdatePlayZonePayload(double minX, double maxX, double minY, double maxY, double minZ, double maxZ, int playZoneID, boolean remove) implements class_8710
    {
        public static final class_8710.class_9154<UpdatePlayZonePayload> ID = new class_8710.class_9154(DestroyingMinecraft.idOf("upd_pz"));
        public static final class_9139<class_9129, UpdatePlayZonePayload> CODEC = new class_9139<class_9129, UpdatePlayZonePayload>(){

            public UpdatePlayZonePayload decode(class_9129 buf) {
                double minX = (Double)class_9135.field_48553.decode((Object)buf);
                double maxX = (Double)class_9135.field_48553.decode((Object)buf);
                double minY = (Double)class_9135.field_48553.decode((Object)buf);
                double maxY = (Double)class_9135.field_48553.decode((Object)buf);
                double minZ = (Double)class_9135.field_48553.decode((Object)buf);
                double maxZ = (Double)class_9135.field_48553.decode((Object)buf);
                int playZoneID = (Integer)class_9135.field_49675.decode((Object)buf);
                boolean remove = (Boolean)class_9135.field_48547.decode((Object)buf);
                return new UpdatePlayZonePayload(minX, maxX, minY, maxY, minZ, maxZ, playZoneID, remove);
            }

            public void encode(class_9129 buf, UpdatePlayZonePayload value) {
                class_9135.field_48553.encode((Object)buf, (Object)value.minX);
                class_9135.field_48553.encode((Object)buf, (Object)value.maxX);
                class_9135.field_48553.encode((Object)buf, (Object)value.minY);
                class_9135.field_48553.encode((Object)buf, (Object)value.maxY);
                class_9135.field_48553.encode((Object)buf, (Object)value.minZ);
                class_9135.field_48553.encode((Object)buf, (Object)value.maxZ);
                class_9135.field_49675.encode((Object)buf, (Object)value.playZoneID);
                class_9135.field_48547.encode((Object)buf, (Object)value.remove);
            }
        };

        public class_8710.class_9154<? extends class_8710> method_56479() {
            return ID;
        }
    }

    public record ShaderChangePacketPayload(String shader) implements class_8710
    {
        public static final class_8710.class_9154<ShaderChangePacketPayload> ID = new class_8710.class_9154(DestroyingMinecraft.idOf("shdrchng"));
        public static final class_9139<class_9129, ShaderChangePacketPayload> CODEC = class_9139.method_56434((class_9139)class_9135.field_48554, ShaderChangePacketPayload::shader, ShaderChangePacketPayload::new);

        public class_8710.class_9154<? extends class_8710> method_56479() {
            return ID;
        }
    }

    public record SBEPayload(Vector3f position, int radius) implements class_8710
    {
        public static final class_8710.class_9154<SBEPayload> ID = new class_8710.class_9154(DestroyingMinecraft.idOf("sbe"));
        public static final class_9139<class_9129, SBEPayload> CODEC = class_9139.method_56435((class_9139)class_9135.field_48558, SBEPayload::position, (class_9139)class_9135.field_49675, SBEPayload::radius, SBEPayload::new);

        public class_8710.class_9154<? extends class_8710> method_56479() {
            return ID;
        }
    }

    public record DestructionPayload(String name, Vector3f position, long startTime) implements class_8710
    {
        public static final class_8710.class_9154<DestructionPayload> ID = new class_8710.class_9154(DestroyingMinecraft.idOf("dest"));
        public static final class_9139<class_9129, DestructionPayload> CODEC = class_9139.method_56436((class_9139)class_9135.field_48554, DestructionPayload::name, (class_9139)class_9135.field_48558, DestructionPayload::position, (class_9139)class_9135.field_48551, DestructionPayload::startTime, DestructionPayload::new);

        public class_8710.class_9154<? extends class_8710> method_56479() {
            return ID;
        }
    }

    public record BraamPayload(class_3414 soundEvent) implements class_8710
    {
        public static final class_8710.class_9154<BraamPayload> ID = new class_8710.class_9154(DestroyingMinecraft.idOf("asp"));
        public static final class_9139<class_9129, BraamPayload> CODEC = class_9139.method_56434((class_9139)class_3414.field_48278, BraamPayload::soundEvent, BraamPayload::new);

        public class_8710.class_9154<? extends class_8710> method_56479() {
            return ID;
        }
    }

    public record UpdatePhysicsDoorBlock(class_2338 blockEntityPos, class_2338 corner1, class_2338 corner2, class_2350 direction, int numOfBlocks, int speed, boolean showSelection, boolean playSound) implements class_8710
    {
        public static final class_8710.class_9154<UpdatePhysicsDoorBlock> ID = new class_8710.class_9154(DestroyingMinecraft.idOf("updatephysdoorblk"));
        public static final class_9139<class_9129, UpdatePhysicsDoorBlock> CODEC = new class_9139<class_9129, UpdatePhysicsDoorBlock>(){

            public UpdatePhysicsDoorBlock decode(class_9129 buf) {
                class_2338 entityPos = (class_2338)class_2338.field_48404.decode((Object)buf);
                class_2338 corner1 = (class_2338)class_2338.field_48404.decode((Object)buf);
                class_2338 corner2 = (class_2338)class_2338.field_48404.decode((Object)buf);
                class_2350 direction = (class_2350)class_2350.field_48450.decode((Object)buf);
                Integer numOfBlocks = (Integer)class_9135.field_49675.decode((Object)buf);
                Integer speed = (Integer)class_9135.field_49675.decode((Object)buf);
                Boolean showSelection = (Boolean)class_9135.field_48547.decode((Object)buf);
                Boolean playSound = (Boolean)class_9135.field_48547.decode((Object)buf);
                return new UpdatePhysicsDoorBlock(entityPos, corner1, corner2, direction, numOfBlocks, speed, showSelection, playSound);
            }

            public void encode(class_9129 buf, UpdatePhysicsDoorBlock value) {
                class_2338.field_48404.encode((Object)buf, (Object)value.blockEntityPos);
                class_2338.field_48404.encode((Object)buf, (Object)value.corner1);
                class_2338.field_48404.encode((Object)buf, (Object)value.corner2);
                class_2350.field_48450.encode((Object)buf, (Object)value.direction);
                class_9135.field_49675.encode((Object)buf, (Object)value.numOfBlocks);
                class_9135.field_49675.encode((Object)buf, (Object)value.speed);
                class_9135.field_48547.encode((Object)buf, (Object)value.showSelection);
                class_9135.field_48547.encode((Object)buf, (Object)value.playSound);
            }
        };

        public class_8710.class_9154<? extends class_8710> method_56479() {
            return ID;
        }
    }
}

