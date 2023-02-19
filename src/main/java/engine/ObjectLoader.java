package engine;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL40;
import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;

public class ObjectLoader {
    private List<Integer> vaos = new ArrayList<Integer>();
    private List<Integer> vbos = new ArrayList<Integer>();
    private List<Integer> textures = new ArrayList<Integer>();

    public Object loadMesh(float[] vertices, int[] indices, float[] textureCoordinates) {
        int vaoID = GL40.glGenVertexArrays();
        int vboID = GL40.glGenBuffers();
        vaos.add(vaoID);
        vbos.add(vboID);

        GL40.glBindVertexArray(vaoID);
        GL40.glBindBuffer(GL40.GL_ELEMENT_ARRAY_BUFFER, vboID);
        IntBuffer buffer = storeDataInIntBuffer(indices);
        GL40.glBufferData(GL40.GL_ELEMENT_ARRAY_BUFFER, buffer, GL40.GL_STATIC_DRAW);

        storeDataInAttributeList(0, 3, vertices);
        storeDataInAttributeList(1, 2, textureCoordinates);

        GL40.glBindVertexArray(0);

        return new Object(vaoID, indices.length);
    }

    public int loadTexture(String filePath) throws Exception {
        int width, height;
        ByteBuffer buffer;
        MemoryStack stack = MemoryStack.stackPush();
        IntBuffer w = stack.mallocInt(1);
        IntBuffer h = stack.mallocInt(1);
        IntBuffer c = stack.mallocInt(1);

        buffer = STBImage.stbi_load(filePath, w, h, c, 4);

        if (buffer == null)
            throw new Exception(filePath + " failed to load");

        width = w.get();
        height = h.get();

        int textureID = GL40.glGenTextures();
        textures.add(textureID);

        GL40.glBindTexture(GL11.GL_TEXTURE_2D, textureID);
        GL40.glPixelStorei(GL40.GL_UNPACK_ALIGNMENT, 1);
        GL40.glTexImage2D(GL40.GL_TEXTURE_2D, 0, GL40.GL_RGBA,
                width, height, 0, GL40.GL_RGBA,
                GL40.GL_UNSIGNED_BYTE, buffer);
        GL40.glGenerateMipmap(GL40.GL_TEXTURE_2D);
        STBImage.stbi_image_free(buffer);

        return textureID;

    }

    private void storeDataInAttributeList(int attributeNumber, int vertexCount, float[] data) {
        int vbo = GL40.glGenBuffers();
        vbos.add(vbo);
        GL40.glBindBuffer(GL40.GL_ARRAY_BUFFER, vbo);
        FloatBuffer buffer = storeDataInFloatBuffer(data);
        GL40.glBufferData(GL40.GL_ARRAY_BUFFER, buffer, GL40.GL_STATIC_DRAW);
        GL40.glVertexAttribPointer(attributeNumber, vertexCount, GL40.GL_FLOAT, false, 0, 0);
        GL40.glBindBuffer(GL40.GL_ARRAY_BUFFER, 0);
    }

    public static FloatBuffer storeDataInFloatBuffer(float[] data) {
        FloatBuffer buffer = MemoryUtil.memAllocFloat(data.length);
        buffer.put(data).flip();
        return buffer;
    }

    public static IntBuffer storeDataInIntBuffer(int[] data) {
        IntBuffer buffer = MemoryUtil.memAllocInt(data.length);
        buffer.put(data).flip();
        return buffer;
    }

    public void cleanup() {
        for (int vao : vaos)
            GL40.glDeleteVertexArrays(vao);

        System.out.println("VAOS cleanup");

        for (int vbo : vbos)
            GL40.glDeleteBuffers(vbo);
        System.out.println("VBOS cleanup");

        for (int texture : textures)
            GL40.glDeleteTextures(texture);
        System.out.println("Textures cleanup");

    }
}
