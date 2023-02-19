package engine;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.lwjgl.opengl.GL40;

public class Shader {
    private int programID;
    private int vertexShaderID, fragmentShaderID;
    private int unifrom_UV;

    public Shader(String vertShaderPath, String fragShaderPath) throws Exception {
        this.programID = GL40.glCreateProgram();
        this.vertexShaderID = createShader(vertShaderPath, GL40.GL_VERTEX_SHADER);
        this.vertexShaderID = createShader(fragShaderPath, GL40.GL_FRAGMENT_SHADER);

        GL40.glBindAttribLocation(programID, 0, "fragTextureCoord");

        GL40.glLinkProgram(programID);
        GL40.glValidateProgram(programID);

        unifrom_UV = createUnifrom("textureSampler");
    }

    private int createUnifrom(String unifromName) throws Exception {
        return GL40.glGetUniformLocation(programID, unifromName);
    }

    private int createShader(String filePath, int shaderType) throws Exception {
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

        if (shaderID == 0)
            throw new Exception("Error creating shader : " + shaderType);

        GL40.glShaderSource(shaderID, shaderSource);

        GL40.glCompileShader(shaderID);
        if (GL40.glGetShaderi(shaderID, GL40.GL_COMPILE_STATUS) == 0)
            throw new Exception("Error compiling shader : " + shaderType);

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
