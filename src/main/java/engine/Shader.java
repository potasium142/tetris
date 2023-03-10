package engine;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.FloatBuffer;

import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL40;

public abstract class Shader {
    protected int programID;
    private int vertexShaderID, fragmentShaderID;
    private FloatBuffer matrixBuffer = BufferUtils.createFloatBuffer(16);

    public Shader(String vertShaderPath, String fragShaderPath) throws Exception {
        this.programID = GL40.glCreateProgram();
        this.vertexShaderID = createShader(vertShaderPath, GL40.GL_VERTEX_SHADER);
        this.vertexShaderID = createShader(fragShaderPath, GL40.GL_FRAGMENT_SHADER);

        bindAttribute();

        GL40.glLinkProgram(programID);
        GL40.glValidateProgram(programID);

        createUnifrom("textureSampler");

        initUnifrom();
    }

    public abstract void bindAttribute();

    public abstract void initUnifrom();

    public void unifromFloat(int location, float value) {
        GL40.glUniform1f(location, value);
    }

    public void unifromInt(int location, int value) {
        GL40.glUniform1d(location, value);
    }

    public void unifromVec2D(int location, float x, float y) {
        GL40.glUniform2f(location, x, y);
    }

    protected int createUnifrom(String unifromName) {
        return GL40.glGetUniformLocation(programID, unifromName);
    }

    protected void unifromMatrix(int location, Matrix4f matrix) {
        matrix.get(matrixBuffer);
        GL40.glUniformMatrix4fv(location, false, matrixBuffer);
    }

    private int createShader(String filePath, int shaderType) {
        StringBuilder shaderSource = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line;
            while ((line = reader.readLine()) != null) {
                shaderSource.append(line).append("//\n");
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        int shaderID = GL40.glCreateShader(shaderType);

        GL40.glShaderSource(shaderID, shaderSource);

        GL40.glCompileShader(shaderID);
        try {
            if (shaderID == 0)
                throw new Exception("Error creating shader : " + shaderType);
            if (GL40.glGetShaderi(shaderID, GL40.GL_COMPILE_STATUS) == 0)
                throw new Exception("Error compiling shader : " + shaderType);
        } catch (Exception e) {
            System.exit(-2);
            e.printStackTrace();
        }

        GL40.glAttachShader(programID, shaderID);

        return shaderID;
    }

    public void start() {
        GL40.glUseProgram(programID);
    }

    public void stop() {
        GL40.glUseProgram(0);
    }

    public void cleanup() {
        stop();
        GL40.glDetachShader(programID, vertexShaderID);
        GL40.glDetachShader(programID, fragmentShaderID);
        GL40.glDeleteShader(vertexShaderID);
        GL40.glDeleteShader(fragmentShaderID);
        GL40.glDeleteProgram(programID);
    }
}
