package me.marty.openpixelmon.client.render.shader;

import java.util.List;

/**
 * Allows for custom handling of some parts which minecraft just won't give us.
 */
public interface ExtendedGlUniform {

    /**
     * Called when the shader is loading uniform references
     *
     * @param programId   the Gl id of the program
     * @param uniformName the name of the uniform
     */
    void loadReference(int programId, String uniformName, List<Integer> loadedUniformIds);
}
