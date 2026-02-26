/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.llamalad7.mixinextras.sugar.Local
 *  foundry.veil.api.client.render.dynamicbuffer.DynamicBufferType
 *  foundry.veil.api.client.render.shader.processor.ShaderPreProcessor$Context
 *  foundry.veil.impl.compat.sodium.SodiumShaderPreProcessor
 *  io.github.ocelot.glslprocessor.api.GlslInjectionPoint
 *  io.github.ocelot.glslprocessor.api.GlslParser
 *  io.github.ocelot.glslprocessor.api.GlslSyntaxException
 *  io.github.ocelot.glslprocessor.api.node.GlslNode
 *  io.github.ocelot.glslprocessor.api.node.GlslNodeList
 *  io.github.ocelot.glslprocessor.api.node.GlslTree
 *  io.github.ocelot.glslprocessor.api.node.branch.GlslIfNode
 *  io.github.ocelot.glslprocessor.api.node.expression.GlslAssignmentNode
 *  io.github.ocelot.glslprocessor.api.node.expression.GlslAssignmentNode$Operand
 *  io.github.ocelot.glslprocessor.api.node.variable.GlslVariableNode
 *  io.github.ocelot.glslprocessor.api.visitor.GlslNodeStringWriter
 *  io.github.ocelot.glslprocessor.impl.GlslParserImpl
 *  io.github.ocelot.glslprocessor.impl.GlslTokenReader
 *  net.minecraft.class_310
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.Unique
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.At$Shift
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 */
package com.sp.mixin.materialsampler.sodium;

import com.llamalad7.mixinextras.sugar.Local;
import com.sp.render.materialsampler.CustomDynamicBuffers;
import foundry.veil.api.client.render.dynamicbuffer.DynamicBufferType;
import foundry.veil.api.client.render.shader.processor.ShaderPreProcessor;
import foundry.veil.impl.compat.sodium.SodiumShaderPreProcessor;
import io.github.ocelot.glslprocessor.api.GlslInjectionPoint;
import io.github.ocelot.glslprocessor.api.GlslParser;
import io.github.ocelot.glslprocessor.api.GlslSyntaxException;
import io.github.ocelot.glslprocessor.api.node.GlslNode;
import io.github.ocelot.glslprocessor.api.node.GlslNodeList;
import io.github.ocelot.glslprocessor.api.node.GlslTree;
import io.github.ocelot.glslprocessor.api.node.branch.GlslIfNode;
import io.github.ocelot.glslprocessor.api.node.expression.GlslAssignmentNode;
import io.github.ocelot.glslprocessor.api.node.variable.GlslVariableNode;
import io.github.ocelot.glslprocessor.api.visitor.GlslNodeStringWriter;
import io.github.ocelot.glslprocessor.impl.GlslParserImpl;
import io.github.ocelot.glslprocessor.impl.GlslTokenReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.minecraft.class_310;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={SodiumShaderPreProcessor.class})
public class SodiumShaderPreProcessorMixin {
    @Unique
    private boolean once;

    private void setOnce(ShaderPreProcessor.Context ctx, GlslTree tree, CallbackInfo ci) {
        this.once = false;
    }

    @Inject(method={"modify"}, at={@At(value="INVOKE", target="Lio/github/ocelot/glslprocessor/api/visitor/GlslNodeStringWriter;visitTypeSpecifier(Lio/github/ocelot/glslprocessor/api/grammar/GlslTypeSpecifier;)V", shift=At.Shift.BY, by=2)}, remap=false)
    private void setMaterialSampler(ShaderPreProcessor.Context ctx, GlslTree tree, CallbackInfo ci, @Local DynamicBufferType type, @Local List<GlslNode> mainBody, @Local GlslNodeList treeBody, @Local boolean modified, @Local String sourceName, @Local GlslNodeStringWriter writer, @Local(ordinal=1) int i) throws GlslSyntaxException {
        if (type == CustomDynamicBuffers.MATERIAL_BUFFER) {
            String tempOutput = "layout(location = " + (1 + i) + ") out " + String.valueOf(writer) + " " + sourceName;
            if (ctx.isVertex()) {
                treeBody.add(GlslInjectionPoint.BEFORE_MAIN, GlslParser.parseExpression((String)"flat out int material"));
                mainBody.add(GlslParser.parseExpression((String)"material = 2"));
                modified = true;
                class_310.method_1551().method_60646().method_60637(modified);
            }
            if (ctx.isFragment()) {
                treeBody.add(GlslInjectionPoint.BEFORE_MAIN, GlslParser.parseExpression((String)"flat in int material"));
                treeBody.add(GlslInjectionPoint.BEFORE_MAIN, GlslParser.parseExpression((String)tempOutput));
                mainBody.add(1, GlslParser.parseExpression((String)(sourceName + " = ivec4(material, 0, 0, 1)")));
                modified = true;
                class_310.method_1551().method_60646().method_60637(modified);
            }
        }
        if (ctx.isVertex() && !this.once) {
            treeBody.add(GlslInjectionPoint.BEFORE_MAIN, GlslParser.parseExpression((String)"uniform int renderingShadow "));
            GlslTokenReader reader = new GlslTokenReader("vec3 distort(in vec3 shadowPosition) {\n    const float bias0 = 0.95;\n    const float bias1 = 1.0 - bias0;\n\n    float factorDistance = length(shadowPosition.xy);\n\n    float distortFactor = factorDistance * bias0 + bias1;\n\n    return shadowPosition * vec3(vec2(1.0 / distortFactor), 0.2);\n}");
            treeBody.add(GlslInjectionPoint.BEFORE_MAIN, (GlslNode)GlslParserImpl.parseFunctionDefinition((GlslTokenReader)reader));
            GlslTokenReader reader2 = new GlslTokenReader("(renderingShadow == 1)");
            ArrayList<GlslAssignmentNode> nodeList = new ArrayList<GlslAssignmentNode>();
            nodeList.add(new GlslAssignmentNode((GlslNode)new GlslVariableNode("gl_Position.xyz"), (GlslNode)new GlslVariableNode("distort(gl_Position.xyz)"), GlslAssignmentNode.Operand.EQUAL));
            mainBody.add(mainBody.size(), (GlslNode)new GlslIfNode(GlslParserImpl.parseCondition((GlslTokenReader)reader2), nodeList, Collections.emptyList()));
            modified = true;
            class_310.method_1551().method_60646().method_60637(modified);
            this.once = true;
        }
    }
}

