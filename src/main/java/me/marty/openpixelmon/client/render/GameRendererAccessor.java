package me.marty.openpixelmon.client.render;

import net.minecraft.client.render.Shader;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.resource.ResourceFactory;

import java.io.IOException;

public interface GameRendererAccessor {

    Shader loadPixelmonShader(ResourceFactory factory, String name, VertexFormat vertexFormat) throws IOException;
}
