package me.marty.openpixelmon.client.render.shader;

import net.minecraft.client.gl.GlShader;
import net.minecraft.client.gl.GlUniform;
import net.minecraft.util.math.Matrix4f;

public class GlArrayUniform extends GlUniform {

    public GlArrayUniform(String name, int dataType, int count, GlShader program) {
        super(name, dataType, count, program);
    }

    public void set(Matrix4f[] matrices) {
//        this.floatData.position(0);
//        values.writeColumnMajor(this.floatData);
//        this.markStateDirty();
    }
}
