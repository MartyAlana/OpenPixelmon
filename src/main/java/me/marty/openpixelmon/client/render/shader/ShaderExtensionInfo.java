package me.marty.openpixelmon.client.render.shader;

import com.google.gson.JsonObject;
import net.minecraft.client.gl.GlUniform;
import net.minecraft.client.render.Shader;

public interface ShaderExtensionInfo {

    GlUniform createUniform(JsonObject object, String name, int type, int rawCount, int unknown, Shader shader);
}
