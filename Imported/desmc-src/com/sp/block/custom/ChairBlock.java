/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.serialization.MapCodec
 *  net.minecraft.class_1269
 *  net.minecraft.class_1657
 *  net.minecraft.class_1750
 *  net.minecraft.class_1922
 *  net.minecraft.class_1937
 *  net.minecraft.class_2248
 *  net.minecraft.class_2338
 *  net.minecraft.class_2350
 *  net.minecraft.class_2383
 *  net.minecraft.class_243
 *  net.minecraft.class_2561
 *  net.minecraft.class_259
 *  net.minecraft.class_265
 *  net.minecraft.class_2680
 *  net.minecraft.class_2689$class_2690
 *  net.minecraft.class_2741
 *  net.minecraft.class_2746
 *  net.minecraft.class_2769
 *  net.minecraft.class_3726
 *  net.minecraft.class_3965
 *  net.minecraft.class_4050
 *  net.minecraft.class_4970$class_2251
 *  org.jetbrains.annotations.Nullable
 */
package com.sp.block.custom;

import com.mojang.serialization.MapCodec;
import com.sp.mixininterfaces.LayingDownPlayerEntity;
import net.minecraft.class_1269;
import net.minecraft.class_1657;
import net.minecraft.class_1750;
import net.minecraft.class_1922;
import net.minecraft.class_1937;
import net.minecraft.class_2248;
import net.minecraft.class_2338;
import net.minecraft.class_2350;
import net.minecraft.class_2383;
import net.minecraft.class_243;
import net.minecraft.class_2561;
import net.minecraft.class_259;
import net.minecraft.class_265;
import net.minecraft.class_2680;
import net.minecraft.class_2689;
import net.minecraft.class_2741;
import net.minecraft.class_2746;
import net.minecraft.class_2769;
import net.minecraft.class_3726;
import net.minecraft.class_3965;
import net.minecraft.class_4050;
import net.minecraft.class_4970;
import org.jetbrains.annotations.Nullable;

public class ChairBlock
extends class_2383 {
    public static final MapCodec<ChairBlock> CODEC = ChairBlock.method_54094(ChairBlock::new);
    public static final class_2746 OCCUPIED = class_2741.field_12528;
    private static final class_265 BOTTOM_SHAPE = class_2248.method_9541((double)0.0, (double)0.0, (double)0.0, (double)16.0, (double)8.0, (double)16.0);
    private static final class_265 EAST_SHAPE = class_259.method_1084((class_265)BOTTOM_SHAPE, (class_265)class_2248.method_9541((double)8.0, (double)8.0, (double)0.0, (double)16.0, (double)16.0, (double)16.0));
    private static final class_265 WEST_SHAPE = class_259.method_1084((class_265)BOTTOM_SHAPE, (class_265)class_2248.method_9541((double)0.0, (double)8.0, (double)0.0, (double)8.0, (double)16.0, (double)16.0));
    private static final class_265 SOUTH_SHAPE = class_259.method_1084((class_265)BOTTOM_SHAPE, (class_265)class_2248.method_9541((double)0.0, (double)8.0, (double)8.0, (double)16.0, (double)16.0, (double)16.0));
    private static final class_265 NORTH_SHAPE = class_259.method_1084((class_265)BOTTOM_SHAPE, (class_265)class_2248.method_9541((double)0.0, (double)8.0, (double)0.0, (double)16.0, (double)16.0, (double)8.0));

    public ChairBlock(class_4970.class_2251 settings) {
        super(settings);
    }

    protected class_265 method_9530(class_2680 state, class_1922 world, class_2338 pos, class_3726 context) {
        return switch ((class_2350)state.method_11654((class_2769)field_11177)) {
            case class_2350.field_11034 -> EAST_SHAPE;
            case class_2350.field_11039 -> WEST_SHAPE;
            case class_2350.field_11043 -> NORTH_SHAPE;
            default -> SOUTH_SHAPE;
        };
    }

    public static class_2350 getDirection(class_1937 world, class_2338 blockPos) {
        class_2680 blockState = world.method_8320(blockPos);
        return blockState.method_26204() instanceof ChairBlock ? (class_2350)blockState.method_11654((class_2769)field_11177) : null;
    }

    protected class_1269 method_55766(class_2680 state, class_1937 world, class_2338 pos, class_1657 player, class_3965 hit) {
        if (world.field_9236) {
            return class_1269.field_21466;
        }
        if (((Boolean)state.method_11654((class_2769)OCCUPIED)).booleanValue()) {
            player.method_7353((class_2561)class_2561.method_43470((String)"This chair is occupied"), true);
        } else {
            player.method_18380(class_4050.field_18078);
            if (player instanceof LayingDownPlayerEntity) {
                LayingDownPlayerEntity layingDownPLayerEntity = (LayingDownPlayerEntity)player;
                layingDownPLayerEntity.setLayingDown(true);
                layingDownPLayerEntity.setLayingDownPos(pos);
            }
            player.method_5814((double)pos.method_10263() + 0.5, (double)(pos.method_10264() + 1), (double)pos.method_10260() + 0.5);
            player.method_18799(class_243.field_1353);
            world.method_8501(pos, (class_2680)state.method_11657((class_2769)OCCUPIED, (Comparable)Boolean.valueOf(true)));
        }
        return class_1269.field_5812;
    }

    @Nullable
    public class_2680 method_9605(class_1750 ctx) {
        return (class_2680)((class_2680)this.method_9564().method_11657((class_2769)OCCUPIED, (Comparable)Boolean.valueOf(false))).method_11657((class_2769)field_11177, (Comparable)ctx.method_8042());
    }

    protected void method_9515(class_2689.class_2690<class_2248, class_2680> builder) {
        super.method_9515(builder);
        builder.method_11667(new class_2769[]{OCCUPIED, field_11177});
    }

    protected MapCodec<? extends class_2383> method_53969() {
        return CODEC;
    }
}

