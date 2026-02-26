/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  foundry.veil.Veil
 *  foundry.veil.api.client.render.dynamicbuffer.DynamicBufferType
 *  foundry.veil.api.client.render.shader.processor.ShaderPreProcessor$Context
 *  foundry.veil.api.client.render.shader.processor.ShaderPreProcessor$MinecraftContext
 *  foundry.veil.impl.client.render.dynamicbuffer.DynamicBufferProcessor
 *  io.github.ocelot.glslprocessor.api.GlslInjectionPoint
 *  io.github.ocelot.glslprocessor.api.GlslParser
 *  io.github.ocelot.glslprocessor.api.GlslSyntaxException
 *  io.github.ocelot.glslprocessor.api.grammar.GlslSpecifiedType
 *  io.github.ocelot.glslprocessor.api.grammar.GlslTypeSpecifier
 *  io.github.ocelot.glslprocessor.api.grammar.GlslTypeSpecifier$BuiltinType
 *  io.github.ocelot.glslprocessor.api.grammar.GlslVersionStatement
 *  io.github.ocelot.glslprocessor.api.node.GlslNode
 *  io.github.ocelot.glslprocessor.api.node.GlslNodeList
 *  io.github.ocelot.glslprocessor.api.node.GlslTree
 *  io.github.ocelot.glslprocessor.api.node.GlslTree$GlslBlock
 *  io.github.ocelot.glslprocessor.api.node.branch.GlslIfNode
 *  io.github.ocelot.glslprocessor.api.node.constant.GlslConstantNode
 *  io.github.ocelot.glslprocessor.api.node.expression.GlslAssignmentNode
 *  io.github.ocelot.glslprocessor.api.node.expression.GlslAssignmentNode$Operand
 *  io.github.ocelot.glslprocessor.api.node.expression.GlslOperationNode
 *  io.github.ocelot.glslprocessor.api.node.expression.GlslOperationNode$Operand
 *  io.github.ocelot.glslprocessor.api.node.function.GlslFunctionNode
 *  io.github.ocelot.glslprocessor.api.node.function.GlslInvokeFunctionNode
 *  io.github.ocelot.glslprocessor.api.node.variable.GlslNewFieldNode
 *  io.github.ocelot.glslprocessor.api.node.variable.GlslVariableNode
 *  io.github.ocelot.glslprocessor.api.visitor.GlslNodeStringWriter
 *  io.github.ocelot.glslprocessor.impl.GlslParserImpl
 *  io.github.ocelot.glslprocessor.impl.GlslTokenReader
 *  io.github.ocelot.glslprocessor.lib.anarres.cpp.LexerException
 *  net.minecraft.class_293
 *  net.minecraft.class_296
 *  org.spongepowered.asm.mixin.Final
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.Overwrite
 *  org.spongepowered.asm.mixin.Shadow
 */
package com.sp.mixin.materialsampler;

import com.sp.render.materialsampler.CustomDynamicBuffers;
import foundry.veil.Veil;
import foundry.veil.api.client.render.dynamicbuffer.DynamicBufferType;
import foundry.veil.api.client.render.shader.processor.ShaderPreProcessor;
import foundry.veil.impl.client.render.dynamicbuffer.DynamicBufferProcessor;
import io.github.ocelot.glslprocessor.api.GlslInjectionPoint;
import io.github.ocelot.glslprocessor.api.GlslParser;
import io.github.ocelot.glslprocessor.api.GlslSyntaxException;
import io.github.ocelot.glslprocessor.api.grammar.GlslSpecifiedType;
import io.github.ocelot.glslprocessor.api.grammar.GlslTypeSpecifier;
import io.github.ocelot.glslprocessor.api.grammar.GlslVersionStatement;
import io.github.ocelot.glslprocessor.api.node.GlslNode;
import io.github.ocelot.glslprocessor.api.node.GlslNodeList;
import io.github.ocelot.glslprocessor.api.node.GlslTree;
import io.github.ocelot.glslprocessor.api.node.branch.GlslIfNode;
import io.github.ocelot.glslprocessor.api.node.constant.GlslConstantNode;
import io.github.ocelot.glslprocessor.api.node.expression.GlslAssignmentNode;
import io.github.ocelot.glslprocessor.api.node.expression.GlslOperationNode;
import io.github.ocelot.glslprocessor.api.node.function.GlslFunctionNode;
import io.github.ocelot.glslprocessor.api.node.function.GlslInvokeFunctionNode;
import io.github.ocelot.glslprocessor.api.node.variable.GlslNewFieldNode;
import io.github.ocelot.glslprocessor.api.node.variable.GlslVariableNode;
import io.github.ocelot.glslprocessor.api.visitor.GlslNodeStringWriter;
import io.github.ocelot.glslprocessor.impl.GlslParserImpl;
import io.github.ocelot.glslprocessor.impl.GlslTokenReader;
import io.github.ocelot.glslprocessor.lib.anarres.cpp.LexerException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import net.minecraft.class_293;
import net.minecraft.class_296;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value={DynamicBufferProcessor.class}, remap=false)
public class DynamicBufferProcessorMixin {
    @Shadow
    @Final
    private static String[] VECTOR_ELEMENTS;
    @Shadow
    @Final
    private static Set<String> BLOCK_SHADERS;

    @Overwrite
    public void modify(ShaderPreProcessor.Context ctx, GlslTree tree) throws IOException, GlslSyntaxException, LexerException {
        DynamicBufferType[] types = DynamicBufferType.decode((int)ctx.activeBuffers());
        Map markers = tree.getMarkers();
        GlslFunctionNode mainFunction = (GlslFunctionNode)tree.mainFunction().orElseThrow();
        GlslNodeList mainFunctionBody = Objects.requireNonNull(mainFunction.getBody());
        GlslNodeList treeBody = tree.getBody();
        GlslVersionStatement version = tree.getVersionStatement();
        if (version.getVersion() < 330) {
            version.setVersion(330);
        }
        version.setCore(true);
        GlslNode sampler = null;
        GlslNode lightmapUV = null;
        boolean blockLightmap = false;
        boolean injectLightmap = !markers.containsKey("veil:" + DynamicBufferType.LIGHT_COLOR.getName()) || !markers.containsKey("veil:" + DynamicBufferType.LIGHT_UV.getName());
        Map data = ctx.customProgramData();
        GlslNodeStringWriter writer = new GlslNodeStringWriter(true);
        boolean modified = false;
        if (ctx instanceof ShaderPreProcessor.MinecraftContext) {
            ShaderPreProcessor.MinecraftContext minecraftContext = (ShaderPreProcessor.MinecraftContext)ctx;
            class_293 vertexFormat = minecraftContext.vertexFormat();
            if (ctx.isVertex() && injectLightmap) {
                Optional<GlslNode> texelFetchOptional;
                Optional<GlslNode> sampleLightmapOptional = mainFunction.stream().filter(node -> {
                    GlslVariableNode variableNode;
                    GlslInvokeFunctionNode invokeFunctionNode;
                    if (!(node instanceof GlslInvokeFunctionNode) || (invokeFunctionNode = (GlslInvokeFunctionNode)node).getParameters().size() != 2) {
                        return false;
                    }
                    GlslNode patt0$temp = invokeFunctionNode.getHeader();
                    return patt0$temp instanceof GlslVariableNode && "minecraft_sample_lightmap".equals((variableNode = (GlslVariableNode)patt0$temp).getName());
                }).findFirst();
                if (sampleLightmapOptional.isPresent()) {
                    List parameters = ((GlslInvokeFunctionNode)sampleLightmapOptional.get()).getParameters();
                    sampler = (GlslNode)parameters.get(0);
                    lightmapUV = (GlslNode)parameters.get(1);
                    blockLightmap = true;
                } else if (vertexFormat.method_60836(class_296.field_52112) && (texelFetchOptional = mainFunction.stream().filter(node -> {
                    if (!(node instanceof GlslInvokeFunctionNode)) return false;
                    GlslInvokeFunctionNode invokeFunctionNode = (GlslInvokeFunctionNode)node;
                    if (invokeFunctionNode.getParameters().size() != 3) {
                        return false;
                    }
                    List parameters = invokeFunctionNode.getParameters();
                    GlslNode patt0$temp = invokeFunctionNode.getHeader();
                    if (!(patt0$temp instanceof GlslVariableNode)) return false;
                    GlslVariableNode functionName = (GlslVariableNode)patt0$temp;
                    if (!"texelFetch".equals(functionName.getName())) return false;
                    Object patt1$temp = parameters.get(1);
                    if (!(patt1$temp instanceof GlslOperationNode)) return false;
                    GlslOperationNode operation = (GlslOperationNode)patt1$temp;
                    GlslNode patt2$temp = operation.getFirst();
                    if (!(patt2$temp instanceof GlslVariableNode)) return false;
                    GlslVariableNode variableNode = (GlslVariableNode)patt2$temp;
                    GlslNode patt3$temp = operation.getSecond();
                    if (!(patt3$temp instanceof GlslConstantNode)) return false;
                    GlslConstantNode constantNode = (GlslConstantNode)patt3$temp;
                    if (constantNode.intValue() != 16) return false;
                    if (operation.getOperand() != GlslOperationNode.Operand.DIVIDE) return false;
                    if (!vertexFormat.method_60837(class_296.field_52112).equals(variableNode.getName())) return false;
                    return true;
                }).findFirst()).isPresent()) {
                    List parameters = ((GlslInvokeFunctionNode)texelFetchOptional.get()).getParameters();
                    sampler = (GlslNode)parameters.get(0);
                    lightmapUV = ((GlslOperationNode)parameters.get(1)).getFirst();
                }
            }
            for (int i = 0; i < types.length; ++i) {
                boolean inVertex;
                DynamicBufferType type = types[i];
                String sourceName = type.getSourceName();
                writer.clear();
                writer.visitTypeSpecifier((GlslTypeSpecifier)type.getType());
                String output = "layout(location = " + (1 + i) + ") out " + String.valueOf(writer) + " " + sourceName;
                String shaderName = minecraftContext.shaderInstance();
                if ("rendertype_lines".equals(shaderName)) {
                    if (type == DynamicBufferType.NORMAL && ctx.isFragment()) {
                        treeBody.add(GlslInjectionPoint.BEFORE_MAIN, GlslParser.parseExpression((String)output));
                        mainFunctionBody.add((GlslNode)new GlslAssignmentNode((GlslNode)new GlslVariableNode(type.getSourceName()), GlslParser.parseExpression((String)"vec4(0.0, 0.0, 0.0, 1.0)"), GlslAssignmentNode.Operand.EQUAL));
                    }
                    if (type != DynamicBufferType.ALBEDO) continue;
                }
                boolean bl = inVertex = data.containsKey("mask") && ((Integer)data.get("mask") & type.getMask()) != 0;
                if (injectLightmap) {
                    if (type == DynamicBufferType.LIGHT_UV) {
                        if (markers.containsKey("veil:" + DynamicBufferType.LIGHT_UV.getName())) continue;
                        if (ctx.isVertex()) {
                            if (lightmapUV != null) {
                                treeBody.add(GlslInjectionPoint.BEFORE_MAIN, GlslParser.parseExpression((String)("out vec2 Pass" + type.getSourceName())));
                                if (blockLightmap) {
                                    mainFunctionBody.add(GlslParser.parseExpression((String)("vec2 veilTexCoord2 = clamp(" + lightmapUV.toSourceString() + " / 256.0, vec2(0.5 / 16.0), vec2(15.5 / 16.0))")));
                                    mainFunctionBody.add((GlslNode)new GlslAssignmentNode((GlslNode)new GlslVariableNode("Pass" + type.getSourceName()), (GlslNode)new GlslVariableNode("veilTexCoord2"), GlslAssignmentNode.Operand.EQUAL));
                                } else {
                                    mainFunctionBody.add((GlslNode)new GlslAssignmentNode((GlslNode)new GlslVariableNode("Pass" + type.getSourceName()), GlslParser.parseExpression((String)("vec2(" + lightmapUV.toSourceString() + " / 256.0)")), GlslAssignmentNode.Operand.EQUAL));
                                }
                                modified = true;
                                data.compute("mask", (s, o) -> {
                                    int n;
                                    if (o instanceof Integer) {
                                        Integer val = (Integer)o;
                                        n = val;
                                    } else {
                                        n = 0;
                                    }
                                    return n | type.getMask();
                                });
                            }
                        } else if (inVertex) {
                            treeBody.add(GlslInjectionPoint.BEFORE_MAIN, GlslParser.parseExpression((String)("in vec2 Pass" + type.getSourceName())));
                            treeBody.add(GlslInjectionPoint.BEFORE_MAIN, GlslParser.parseExpression((String)output));
                            mainFunctionBody.add((GlslNode)new GlslAssignmentNode((GlslNode)new GlslVariableNode(type.getSourceName()), GlslParser.parseExpression((String)("vec4(Pass" + type.getSourceName() + ", 0.0, 1.0)")), GlslAssignmentNode.Operand.EQUAL));
                            modified = true;
                        }
                    } else if (type == DynamicBufferType.LIGHT_COLOR) {
                        if (markers.containsKey("veil:" + DynamicBufferType.LIGHT_COLOR.getName())) continue;
                        if (ctx.isVertex()) {
                            if (lightmapUV != null && sampler != null) {
                                treeBody.add(GlslInjectionPoint.BEFORE_MAIN, GlslParser.parseExpression((String)("out vec3 Pass" + type.getSourceName())));
                                if (blockLightmap) {
                                    mainFunctionBody.add((GlslNode)new GlslAssignmentNode((GlslNode)new GlslVariableNode("Pass" + type.getSourceName()), GlslParser.parseExpression((String)("texture(" + sampler.toSourceString() + ", veilTexCoord2).rgb")), GlslAssignmentNode.Operand.EQUAL));
                                } else {
                                    mainFunctionBody.add((GlslNode)new GlslAssignmentNode((GlslNode)new GlslVariableNode("Pass" + type.getSourceName()), GlslParser.parseExpression((String)("texelFetch(" + sampler.toSourceString() + ", " + lightmapUV.toSourceString() + " / 16, 0).rgb")), GlslAssignmentNode.Operand.EQUAL));
                                }
                                modified = true;
                                data.compute("mask", (s, o) -> {
                                    int n;
                                    if (o instanceof Integer) {
                                        Integer val = (Integer)o;
                                        n = val;
                                    } else {
                                        n = 0;
                                    }
                                    return n | type.getMask();
                                });
                            }
                        } else if (inVertex) {
                            treeBody.add(GlslInjectionPoint.BEFORE_MAIN, GlslParser.parseExpression((String)("in vec3 Pass" + type.getSourceName())));
                            treeBody.add(GlslInjectionPoint.BEFORE_MAIN, GlslParser.parseExpression((String)output));
                            mainFunctionBody.add((GlslNode)new GlslAssignmentNode((GlslNode)new GlslVariableNode(type.getSourceName()), GlslParser.parseExpression((String)("vec4(Pass" + type.getSourceName() + ", 1.0)")), GlslAssignmentNode.Operand.EQUAL));
                            modified = true;
                        }
                    }
                }
                if (type == CustomDynamicBuffers.MATERIAL_BUFFER && !markers.containsKey("veil:" + CustomDynamicBuffers.MATERIAL_BUFFER.getName())) {
                    if ("rendertype_entity_cutout_no_cull".equals(shaderName) || "rendertype_entity_translucent".equals(shaderName) || "rendertype_entity_solid".equals(shaderName)) {
                        if (ctx.isFragment()) {
                            treeBody.add(GlslInjectionPoint.BEFORE_MAIN, GlslParser.parseExpression((String)output));
                            mainFunctionBody.add((GlslNode)new GlslAssignmentNode((GlslNode)new GlslVariableNode(sourceName), GlslParser.parseExpression((String)"ivec4(1, 0, 0, 1)"), GlslAssignmentNode.Operand.EQUAL));
                            modified = true;
                        }
                    } else if ("particle".equals(shaderName)) {
                        if (ctx.isFragment()) {
                            treeBody.add(GlslInjectionPoint.BEFORE_MAIN, GlslParser.parseExpression((String)output));
                            mainFunctionBody.add((GlslNode)new GlslAssignmentNode((GlslNode)new GlslVariableNode(sourceName), GlslParser.parseExpression((String)"ivec4(1, 0, 0, 1)"), GlslAssignmentNode.Operand.EQUAL));
                            modified = true;
                        }
                    } else if ("rendertype_clouds".equals(shaderName)) {
                        if (ctx.isFragment()) {
                            treeBody.add(GlslInjectionPoint.BEFORE_MAIN, GlslParser.parseExpression((String)output));
                            mainFunctionBody.add((GlslNode)new GlslAssignmentNode((GlslNode)new GlslVariableNode(sourceName), GlslParser.parseExpression((String)"ivec4(12, 0, 0, 1)"), GlslAssignmentNode.Operand.EQUAL));
                            modified = true;
                        }
                    } else if (BLOCK_SHADERS.contains(shaderName) && ctx.isFragment()) {
                        treeBody.add(GlslInjectionPoint.BEFORE_MAIN, GlslParser.parseExpression((String)output));
                        mainFunctionBody.add((GlslNode)new GlslAssignmentNode((GlslNode)new GlslVariableNode(sourceName), GlslParser.parseExpression((String)"ivec4(2, 0, 0, 1)"), GlslAssignmentNode.Operand.EQUAL));
                        modified = true;
                    }
                }
                if (type == DynamicBufferType.NORMAL && !markers.containsKey("veil:" + DynamicBufferType.NORMAL.getName())) {
                    if (ctx.isFragment() && ("particle".equals(shaderName) || "rendertype_leash".equals(shaderName) || "rendertype_text".equals(shaderName))) {
                        treeBody.add(GlslInjectionPoint.BEFORE_MAIN, GlslParser.parseExpression((String)output));
                        mainFunctionBody.add((GlslNode)new GlslAssignmentNode((GlslNode)new GlslVariableNode(type.getSourceName()), GlslParser.parseExpression((String)"vec4(0.0, 0.0, 1.0, 1.0)"), GlslAssignmentNode.Operand.EQUAL));
                        modified = true;
                    }
                    if (vertexFormat.method_60836(class_296.field_52113)) {
                        if (ctx.isVertex()) {
                            Optional fieldOptional = tree.field(vertexFormat.method_60837(class_296.field_52113));
                            if (fieldOptional.isPresent()) {
                                treeBody.add(GlslInjectionPoint.BEFORE_MAIN, GlslParser.parseExpression((String)"uniform mat3 NormalMat"));
                                treeBody.add(GlslInjectionPoint.BEFORE_MAIN, GlslParser.parseExpression((String)("out vec3 Pass" + type.getSourceName())));
                                mainFunctionBody.add((GlslNode)new GlslAssignmentNode((GlslNode)new GlslVariableNode("Pass" + type.getSourceName()), GlslParser.parseExpression((String)("NormalMat * " + ((GlslNewFieldNode)fieldOptional.get()).getName())), GlslAssignmentNode.Operand.EQUAL));
                                modified = true;
                                data.compute("mask", (s, o) -> {
                                    int n;
                                    if (o instanceof Integer) {
                                        Integer val = (Integer)o;
                                        n = val;
                                    } else {
                                        n = 0;
                                    }
                                    return n | type.getMask();
                                });
                            }
                        } else if (ctx.isFragment() && inVertex) {
                            treeBody.add(GlslInjectionPoint.BEFORE_MAIN, GlslParser.parseExpression((String)("in vec3 Pass" + type.getSourceName())));
                            treeBody.add(GlslInjectionPoint.BEFORE_MAIN, GlslParser.parseExpression((String)output));
                            mainFunctionBody.add((GlslNode)new GlslAssignmentNode((GlslNode)new GlslVariableNode(type.getSourceName()), GlslParser.parseExpression((String)("vec4(Pass" + type.getSourceName() + ", 1.0)")), GlslAssignmentNode.Operand.EQUAL));
                            modified = true;
                        }
                    }
                }
                if (type != DynamicBufferType.ALBEDO || markers.containsKey("veil:" + DynamicBufferType.ALBEDO.getName())) continue;
                if (ctx.isVertex()) {
                    Optional fieldOptional;
                    Optional<GlslNode> mixLightOptional;
                    if (BLOCK_SHADERS.contains(shaderName)) {
                        // empty if block
                    }
                    if ((mixLightOptional = mainFunction.stream().filter(node -> {
                        GlslVariableNode variableNode;
                        GlslInvokeFunctionNode invokeFunctionNode;
                        if (!(node instanceof GlslInvokeFunctionNode) || (invokeFunctionNode = (GlslInvokeFunctionNode)node).getParameters().size() != 4) {
                            return false;
                        }
                        GlslNode patt0$temp = invokeFunctionNode.getHeader();
                        return patt0$temp instanceof GlslVariableNode && "minecraft_mix_light".equals((variableNode = (GlslVariableNode)patt0$temp).getName());
                    }).findFirst()).isPresent()) {
                        GlslNode color = (GlslNode)((GlslInvokeFunctionNode)mixLightOptional.get()).getParameters().get(3);
                        treeBody.add(GlslInjectionPoint.BEFORE_MAIN, GlslParser.parseExpression((String)("out vec4 Pass" + type.getSourceName())));
                        mainFunctionBody.add((GlslNode)new GlslAssignmentNode((GlslNode)new GlslVariableNode("Pass" + type.getSourceName()), color, GlslAssignmentNode.Operand.EQUAL));
                        modified = true;
                        data.compute("mask", (s, o) -> {
                            int n;
                            if (o instanceof Integer) {
                                Integer val = (Integer)o;
                                n = val;
                            } else {
                                n = 0;
                            }
                            return n | type.getMask();
                        });
                        continue;
                    }
                    if (!vertexFormat.method_60836(class_296.field_52108) || !(fieldOptional = tree.field(vertexFormat.method_60837(class_296.field_52108))).isPresent()) continue;
                    treeBody.add(GlslInjectionPoint.BEFORE_MAIN, GlslParser.parseExpression((String)("out vec4 Pass" + type.getSourceName())));
                    mainFunctionBody.add((GlslNode)new GlslAssignmentNode((GlslNode)new GlslVariableNode("Pass" + type.getSourceName()), (GlslNode)new GlslVariableNode(((GlslNewFieldNode)fieldOptional.get()).getName()), GlslAssignmentNode.Operand.EQUAL));
                    modified = true;
                    data.compute("mask", (s, o) -> {
                        int n;
                        if (o instanceof Integer) {
                            Integer val = (Integer)o;
                            n = val;
                        } else {
                            n = 0;
                        }
                        return n | type.getMask();
                    });
                    continue;
                }
                if (!inVertex) continue;
                treeBody.add(GlslInjectionPoint.BEFORE_MAIN, GlslParser.parseExpression((String)("in vec4 Pass" + type.getSourceName())));
                treeBody.addFirst((Object)GlslParser.parseExpression((String)output));
                boolean hasColorModulator = tree.field("ColorModulator").isPresent();
                boolean inserted = false;
                for (int j = 0; j < mainFunctionBody.size(); ++j) {
                    GlslNode body = mainFunctionBody.get(i);
                    Optional<GlslNode> textureOptional = body.stream().filter(node -> {
                        GlslVariableNode textureSampler;
                        Object patt1$temp;
                        GlslVariableNode variableNode;
                        GlslInvokeFunctionNode invokeFunctionNode;
                        if (!(node instanceof GlslInvokeFunctionNode) || (invokeFunctionNode = (GlslInvokeFunctionNode)node).getParameters().size() != 2) {
                            return false;
                        }
                        GlslNode patt0$temp = invokeFunctionNode.getHeader();
                        return patt0$temp instanceof GlslVariableNode && "texture".equals((variableNode = (GlslVariableNode)patt0$temp).getName()) && (patt1$temp = invokeFunctionNode.getParameters().getFirst()) instanceof GlslVariableNode && "Sampler0".equals((textureSampler = (GlslVariableNode)patt1$temp).getName());
                    }).findFirst();
                    if (!textureOptional.isPresent()) continue;
                    if (hasColorModulator) {
                        mainFunctionBody.add((GlslNode)new GlslAssignmentNode((GlslNode)new GlslVariableNode(type.getSourceName()), GlslParser.parseExpression((String)(textureOptional.get().toSourceString() + " * ColorModulator * Pass" + type.getSourceName())), GlslAssignmentNode.Operand.EQUAL));
                    } else {
                        mainFunctionBody.add((GlslNode)new GlslAssignmentNode((GlslNode)new GlslVariableNode(type.getSourceName()), GlslParser.parseExpression((String)(textureOptional.get().toSourceString() + " * Pass" + type.getSourceName())), GlslAssignmentNode.Operand.EQUAL));
                    }
                    inserted = true;
                    break;
                }
                if (!inserted) {
                    if (hasColorModulator) {
                        mainFunctionBody.add((GlslNode)new GlslAssignmentNode((GlslNode)new GlslVariableNode(type.getSourceName()), GlslParser.parseExpression((String)("Pass" + type.getSourceName() + " * ColorModulator")), GlslAssignmentNode.Operand.EQUAL));
                    } else {
                        mainFunctionBody.add((GlslNode)new GlslAssignmentNode((GlslNode)new GlslVariableNode(type.getSourceName()), (GlslNode)new GlslVariableNode("Pass" + type.getSourceName()), GlslAssignmentNode.Operand.EQUAL));
                    }
                }
                modified = true;
            }
            if (ctx.isVertex()) {
                treeBody.add(GlslInjectionPoint.BEFORE_DECLARATIONS, GlslParser.parseExpression((String)"uniform int renderingShadow"));
                GlslTokenReader reader = new GlslTokenReader("vec3 distort(in vec3 shadowPosition) {\n    const float bias0 = 0.95;\n    const float bias1 = 1.0 - bias0;\n\n    float factorDistance = length(shadowPosition.xy);\n\n    float distortFactor = factorDistance * bias0 + bias1;\n\n    return shadowPosition * vec3(vec2(1.0 / distortFactor), 0.2);\n}");
                treeBody.add(GlslInjectionPoint.AFTER_FUNCTIONS, (GlslNode)GlslParserImpl.parseFunctionDefinition((GlslTokenReader)reader));
                GlslTokenReader reader2 = new GlslTokenReader("(renderingShadow == 1)");
                ArrayList<GlslAssignmentNode> nodeList = new ArrayList<GlslAssignmentNode>();
                nodeList.add(new GlslAssignmentNode((GlslNode)new GlslVariableNode("gl_Position.xyz"), (GlslNode)new GlslVariableNode("distort(gl_Position.xyz)"), GlslAssignmentNode.Operand.EQUAL));
                mainFunctionBody.add(mainFunctionBody.size(), (GlslNode)new GlslIfNode(GlslParserImpl.parseCondition((GlslTokenReader)reader2), nodeList, Collections.emptyList()));
            }
        }
        for (int i = 0; i < types.length; ++i) {
            GlslVariableNode expression;
            String cast;
            Object sourceName;
            GlslTypeSpecifier.BuiltinType nodeType;
            GlslAssignmentNode assignmentNode;
            Optional block;
            boolean vertexPassthrough;
            DynamicBufferType bufferType = types[i];
            String typeName = bufferType.getName();
            GlslTypeSpecifier.BuiltinType outType = bufferType.getType();
            GlslNode node2 = (GlslNode)markers.get("veil:" + typeName);
            boolean bl = vertexPassthrough = data.containsKey("passmask") && ((Integer)data.get("passmask") & bufferType.getMask()) != 0;
            if (node2 == null) {
                if (!vertexPassthrough) continue;
                String sourceName2 = bufferType.getSourceName();
                writer.clear();
                writer.visitTypeSpecifier((GlslTypeSpecifier)outType);
                treeBody.add(GlslInjectionPoint.BEFORE_MAIN, GlslParser.parseExpression((String)("in " + String.valueOf(writer) + " Pass" + sourceName2)));
                treeBody.add(GlslInjectionPoint.BEFORE_MAIN, GlslParser.parseExpression((String)("layout(location = " + (1 + i) + ") out " + String.valueOf(writer) + " " + sourceName2)));
                mainFunctionBody.add((GlslNode)new GlslAssignmentNode((GlslNode)new GlslVariableNode(sourceName2), (GlslNode)new GlslVariableNode("Pass" + sourceName2), GlslAssignmentNode.Operand.EQUAL));
                continue;
            }
            if (vertexPassthrough) {
                throw new IOException("Node marked '#veil:" + typeName + "' in both vertex and fragment shader");
            }
            GlslSpecifiedType specifiedType = null;
            String copyName = null;
            GlslNodeList body = null;
            int index = 0;
            if (node2 instanceof GlslNewFieldNode) {
                GlslNewFieldNode newNode = (GlslNewFieldNode)node2;
                block = tree.containingBlock((GlslNode)newNode);
                if (block.isPresent()) {
                    copyName = newNode.getName();
                    specifiedType = newNode.getType();
                    GlslTree.GlslBlock pair = (GlslTree.GlslBlock)block.get();
                    body = pair.body();
                    index = pair.index() + 1;
                }
            } else if (node2 instanceof GlslAssignmentNode && (block = (assignmentNode = (GlslAssignmentNode)node2).getFirst()) instanceof GlslVariableNode) {
                List fields;
                GlslVariableNode variableNode = (GlslVariableNode)block;
                block = tree.containingBlock((GlslNode)assignmentNode);
                if (block.isPresent() && (fields = tree.searchField(copyName = variableNode.getName()).toList()).size() == 1) {
                    specifiedType = ((GlslNewFieldNode)fields.getFirst()).getType();
                    GlslTree.GlslBlock pair = (GlslTree.GlslBlock)block.get();
                    body = pair.body();
                    index = pair.index() + 1;
                }
            }
            if (copyName == null || specifiedType == null || !((assignmentNode = specifiedType.getSpecifier()) instanceof GlslTypeSpecifier.BuiltinType) || !(nodeType = (GlslTypeSpecifier.BuiltinType)assignmentNode).isPrimitive() && !nodeType.isVector() || !nodeType.isFloat() && !nodeType.isInteger() && !nodeType.isUnsignedInteger()) {
                Veil.LOGGER.warn("Invalid node marked '#veil:{}' in {} shader: {}", new Object[]{typeName, ctx.typeName(), ctx.name()});
                continue;
            }
            modified = true;
            writer.clear();
            writer.visitTypeSpecifier((GlslTypeSpecifier)outType);
            if (ctx.isVertex()) {
                sourceName = "Pass" + bufferType.getSourceName();
                treeBody.add(GlslInjectionPoint.BEFORE_MAIN, GlslParser.parseExpression((String)("out " + String.valueOf(writer) + " " + (String)sourceName)));
                data.compute("passmask", (s, o) -> {
                    int n;
                    if (o instanceof Integer) {
                        Integer val = (Integer)o;
                        n = val;
                    } else {
                        n = 0;
                    }
                    return n | bufferType.getMask();
                });
            } else {
                sourceName = bufferType.getSourceName();
                treeBody.add(GlslInjectionPoint.BEFORE_MAIN, GlslParser.parseExpression((String)("layout(location = " + (1 + i) + ") out " + String.valueOf(writer) + " " + (String)sourceName)));
            }
            switch (outType) {
                case FLOAT: 
                case VEC2: 
                case VEC3: 
                case VEC4: {
                    String string;
                    if (!nodeType.isFloat()) {
                        string = "float";
                        break;
                    }
                    string = null;
                    break;
                }
                case INT: 
                case IVEC2: 
                case IVEC3: 
                case IVEC4: {
                    String string;
                    if (!nodeType.isInteger()) {
                        string = "int";
                        break;
                    }
                    string = null;
                    break;
                }
                case UINT: 
                case UVEC2: 
                case UVEC3: 
                case UVEC4: {
                    String string;
                    if (!nodeType.isUnsignedInteger()) {
                        string = "uint";
                        break;
                    }
                    string = null;
                    break;
                }
                default: {
                    String string = cast = null;
                }
            }
            if (nodeType == outType) {
                expression = new GlslVariableNode(copyName);
            } else if (nodeType.getComponents() < outType.getComponents()) {
                int j;
                StringBuilder builder = new StringBuilder(writer.toString()).append("(");
                String padding = outType.getConstant(0.0);
                if (nodeType.getComponents() == 1) {
                    builder.append(cast != null ? cast + "(" + copyName + "), " : copyName + ", ");
                } else {
                    for (j = 0; j < nodeType.getComponents(); ++j) {
                        builder.append(cast != null ? cast + "(" + copyName + VECTOR_ELEMENTS[j] + "), " : copyName + VECTOR_ELEMENTS[j] + ", ");
                    }
                }
                for (j = nodeType.getComponents(); j < 3; ++j) {
                    builder.append(padding).append(", ");
                }
                builder.append(outType.getConstant(1.0));
                builder.append(')');
                expression = GlslParser.parseExpression((String)builder.toString());
            } else {
                expression = GlslParser.parseExpression((String)((cast != null ? writer.toString() : "") + "(" + copyName + ")"));
            }
            body.add(index, new GlslAssignmentNode((GlslNode)new GlslVariableNode((String)sourceName), (GlslNode)expression, GlslAssignmentNode.Operand.EQUAL));
        }
        if (modified && ctx.isFragment()) {
            tree.markOutputs();
        }
    }
}

