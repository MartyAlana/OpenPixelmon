package me.marty.openpixelmon.client.render.renderer;

import org.lwjgl.opengl.GL15C;
import org.lwjgl.opengl.GL43C;

import java.nio.ByteBuffer;

/**
 * Represents a Persistently mapped Storage Buffer
 */
public record StaticStorageBuffer(int id, long size, ByteBuffer pointer) {

    public void bind() {
        GL15C.glBindBuffer(GL43C.GL_SHADER_STORAGE_BUFFER, id);
    }

    public void free() {
        // TODO:
    }
}
