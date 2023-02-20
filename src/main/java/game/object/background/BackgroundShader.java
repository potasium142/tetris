package game.object.background;

import org.lwjgl.opengl.GL40;

import engine.Shader;

public class BackgroundShader extends Shader {

    public BackgroundShader(String vertShaderPath, String fragShaderPath) throws Exception {
        super(vertShaderPath, fragShaderPath);
    }

    @Override
    public void bindAttribute() {
        GL40.glBindAttribLocation(programID, 0, "fragTextureCoord");
    }

    @Override
    public void initUnifrom() {

    }

}
