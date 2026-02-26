/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.serialization.Codec
 *  net.minecraft.class_3542
 */
package com.sp.destruction;

import com.mojang.serialization.Codec;
import java.util.Objects;
import java.util.Optional;
import net.minecraft.class_3542;

public enum DestructionType implements class_3542
{
    NUKE("nuke"),
    ORBITAL_LASER("laser"),
    PLANET("planet"),
    SUPERNOVA("supernova"),
    BLACK_HOLE("black_hole");

    private final String name;
    public static final Codec<DestructionType> CODEC;

    private DestructionType(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public static Optional<DestructionType> getFromName(String name) {
        for (DestructionType destruction : DestructionType.values()) {
            if (!Objects.equals(destruction.getName(), name)) continue;
            return Optional.of(destruction);
        }
        return Optional.empty();
    }

    public String method_15434() {
        return this.name;
    }

    static {
        CODEC = class_3542.method_28140(DestructionType::values);
    }
}

