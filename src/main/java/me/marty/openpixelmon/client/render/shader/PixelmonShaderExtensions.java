package me.marty.openpixelmon.client.render.shader;

import com.google.gson.JsonObject;
import net.minecraft.client.gl.GlUniform;
import net.minecraft.client.render.Shader;

/**
 * All shader extensions used by Pixelmon.
 */
public class PixelmonShaderExtensions {

    public static GlUniform doArrayExtension(JsonObject uniform, String name, int dataType, int rawCount, float[] count, int unknown, Shader shader) {
        return new GlArrayUniform(name, dataType + unknown, rawCount, shader);
    }
}
