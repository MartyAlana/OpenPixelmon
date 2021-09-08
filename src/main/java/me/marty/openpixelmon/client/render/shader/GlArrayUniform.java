package me.marty.openpixelmon.client.render.shader;

import net.minecraft.client.gl.GlShader;
import net.minecraft.client.gl.GlUniform;
import net.minecraft.util.math.Matrix4f;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.GL20;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.lwjgl.opengl.GL20.glUniformMatrix4fv;

public class GlArrayUniform extends GlUniform implements ExtendedGlUniform {

    private static final Logger LOGGER = LogManager.getLogger();
    private static final Matrix4f EMPTY = new Matrix4f();
    private final List<Integer> uniformLocations = new ArrayList<>();
    private int dataTypeCount;

    public GlArrayUniform(String name, int dataType, int count, GlShader shader) {
        super(name, dataType, count, shader);
        if (dataType == 10) {
            dataTypeCount = count / 16;
        } else {
            dataTypeCount = -1;
        }
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
            case 10 -> {
                int i = 0;
                for (int uniformLocation : uniformLocations) {
                    glUniformMatrix4fv(uniformLocation, false, this.floatData.position((getCount() / dataTypeCount) * i));
                    i++;
                }
            }
        }
        this.floatData.clear();
    }

    @Override
    public void setLoc(int loc) {
        this.location = -1;
    }

    @Override
    public void loadReference(int programId, String uniformName, List<Integer> loadedUniformIds) {
        for (int i = 0; i < dataTypeCount; i++) {
            uniformLocations.add(GlUniform.getUniformLocation(programId, uniformName + "[" + i + "]"));
            loadedUniformIds.add(location);
        }
    }
}
