package me.marty.openpixelmon.mixin.client.shader;

import com.google.common.collect.ImmutableMap;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import me.marty.openpixelmon.client.render.shader.PixelmonShaderExtensions;
import me.marty.openpixelmon.client.render.shader.ShaderExtensionInfo;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gl.GlUniform;
import net.minecraft.client.gl.ShaderParseException;
import net.minecraft.client.render.Shader;
import net.minecraft.util.JsonHelper;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.List;
import java.util.Map;

@Environment(EnvType.CLIENT)
@Mixin(Shader.class)
public abstract class ShaderMixin {

    @Shadow
    protected abstract void addUniform(JsonElement json) throws ShaderParseException;

    @Shadow
    @Final
    private List<GlUniform> uniforms;
    private static final Map<String, ShaderExtensionInfo> EXTENSIONS =
            new ImmutableMap.Builder<String, ShaderExtensionInfo>()
                    .put("pixelmon_arrays", PixelmonShaderExtensions::doArrayExtension)
                    .build();

    @Inject(method = "addUniform", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gl/GlUniform;<init>(Ljava/lang/String;IILnet/minecraft/client/gl/GlShader;)V"), cancellable = true, locals = LocalCapture.CAPTURE_FAILEXCEPTION)
    private void handleExtensions(JsonElement json, CallbackInfo ci, JsonObject uniform, String name, int type, int rawCount, float[] count, int unknown) {
        try {
            JsonArray extensions = JsonHelper.getArray(uniform, "extensions");

            for (JsonElement jsonElement : extensions) {
                String extensionName = jsonElement.getAsString();
                ShaderExtensionInfo extension = EXTENSIONS.get(extensionName);
                uniforms.add(extension.createUniform(uniform, name, type, rawCount, count, unknown, (Shader) (Object) this));
            }

            ci.cancel();
        } catch (JsonParseException ignored) {
        }
    }
}
