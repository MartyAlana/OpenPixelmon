package me.marty.openpixelmon.mixin.client;

import me.marty.openpixelmon.client.OpenPixelmonClient;
import me.marty.openpixelmon.client.render.GameRendererAccessor;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.Shader;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.resource.ResourceFactory;
import net.minecraft.resource.ResourceManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.IOException;

@Mixin(GameRenderer.class)
public abstract class GameRendererMixin implements GameRendererAccessor {

    @Shadow
    protected abstract Shader loadShader(ResourceFactory factory, String name, VertexFormat vertexFormat) throws IOException;

    @Inject(method = "loadShaders", at = @At(value = "TAIL"))
    private void loadPixelmonShaders(ResourceManager manager, CallbackInfo ci) {
        try {
            OpenPixelmonClient.loadShaders(manager, this);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load Pixelmon shaders", e);
        }
    }

    @Override
    public Shader loadPixelmonShader(ResourceFactory factory, String name, VertexFormat vertexFormat) throws IOException {
        return loadShader(factory, name, vertexFormat);
    }
}
