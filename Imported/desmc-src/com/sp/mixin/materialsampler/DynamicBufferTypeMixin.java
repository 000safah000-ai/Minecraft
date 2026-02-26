/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  foundry.veil.api.client.render.dynamicbuffer.DynamicBufferType
 *  foundry.veil.api.client.render.framebuffer.FramebufferAttachmentDefinition$Format
 *  io.github.ocelot.glslprocessor.api.grammar.GlslTypeSpecifier$BuiltinType
 *  org.spongepowered.asm.mixin.Final
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.Mutable
 *  org.spongepowered.asm.mixin.Shadow
 *  org.spongepowered.asm.mixin.Unique
 *  org.spongepowered.asm.mixin.gen.Invoker
 */
package com.sp.mixin.materialsampler;

import com.sp.render.materialsampler.CustomDynamicBuffers;
import foundry.veil.api.client.render.dynamicbuffer.DynamicBufferType;
import foundry.veil.api.client.render.framebuffer.FramebufferAttachmentDefinition;
import io.github.ocelot.glslprocessor.api.grammar.GlslTypeSpecifier;
import java.util.ArrayList;
import java.util.Arrays;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.gen.Invoker;

@Unique
@Mixin(value={DynamicBufferType.class})
public abstract class DynamicBufferTypeMixin {
    @Shadow
    @Final
    @Mutable
    private static DynamicBufferType[] $VALUES;
    private static final DynamicBufferType MATERIAL;
    private static final DynamicBufferType BLOOM;
    @Shadow
    @Final
    @Mutable
    public static DynamicBufferType[] BUFFERS;
    @Shadow
    @Final
    @Mutable
    private static int MASK;

    @Invoker(value="<init>")
    public static DynamicBufferType invokeInit(String par1, int par2, String par3, GlslTypeSpecifier.BuiltinType par4, FramebufferAttachmentDefinition.Format par5) {
        throw new AssertionError();
    }

    private static DynamicBufferType addVariant(String sourceName, GlslTypeSpecifier.BuiltinType type, FramebufferAttachmentDefinition.Format format) {
        ArrayList<DynamicBufferType> variants = new ArrayList<DynamicBufferType>(Arrays.asList($VALUES));
        DynamicBufferType bufferType = DynamicBufferTypeMixin.invokeInit(sourceName, variants.get(variants.size() - 1).ordinal() + 1, sourceName, type, format);
        variants.add(bufferType);
        DynamicBufferType[] newList = variants.toArray($VALUES);
        $VALUES = newList;
        return bufferType;
    }

    static {
        MATERIAL = CustomDynamicBuffers.MATERIAL_BUFFER = DynamicBufferTypeMixin.addVariant("Material", GlslTypeSpecifier.BuiltinType.IVEC4, FramebufferAttachmentDefinition.Format.R8UI);
        BLOOM = CustomDynamicBuffers.BLOOM_BUFFER = DynamicBufferTypeMixin.addVariant("Bloom", GlslTypeSpecifier.BuiltinType.VEC3, FramebufferAttachmentDefinition.Format.R11F_G11F_B10F);
        BUFFERS = new DynamicBufferType[]{DynamicBufferType.ALBEDO, DynamicBufferType.NORMAL, DynamicBufferType.LIGHT_UV, DynamicBufferType.LIGHT_COLOR, DynamicBufferType.DEBUG, CustomDynamicBuffers.MATERIAL_BUFFER, CustomDynamicBuffers.BLOOM_BUFFER};
        MASK = (1 << BUFFERS.length) - 1;
    }
}

