package me.marty.openpixelmon.client.render.shader;

import net.minecraft.client.gl.GlShader;
import net.minecraft.client.gl.GlUniform;
import net.minecraft.util.math.Matrix4f;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.GL20;

import java.util.Objects;

public class GlArrayUniform extends GlUniform {

    private static final Logger LOGGER = LogManager.getLogger();
    private static final Matrix4f EMPTY = new Matrix4f();

    public GlArrayUniform(String name, int dataType, int count, GlShader program) {
        super(name, dataType, count, program);
    }

    public void set(Matrix4f[] matrices) {
        this.floatData.position(0);
        for (Matrix4f matrix : matrices) {
            Objects.requireNonNullElse(matrix, EMPTY).writeColumnMajor(this.floatData);
        }
        this.markStateDirty();
    }

    public void upload() {
        this.stateDirty = false;
        if (this.dataType <= 3) {
            this.uploadInts();
        } else if (this.dataType <= 7) {
            this.uploadFloats();
        } else {
            if (this.dataType > 10) {
                LOGGER.warn("Uniform.upload called, but type value ({}) is not a valid type. Ignoring.", this.dataType);
                return;
            }

            this.uploadMatrices();
        }
    }

    private void uploadMatrices() {
        switch (this.dataType) {
            case 8 -> GL20.glUniformMatrix2fv(this.location, false, this.floatData);
            case 9 -> GL20.glUniformMatrix3fv(this.location, false, this.floatData);
            case 10 -> GL20.glUniformMatrix4fv(this.location, false, this.floatData);
        }
        this.floatData.clear();
    }
}
