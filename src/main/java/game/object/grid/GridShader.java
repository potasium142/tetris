package game.object.grid;

import org.joml.Matrix4f;
import org.lwjgl.opengl.GL40;

import engine.Shader;

public class GridShader extends Shader {

    public int titleLocation;
    public int titleIndex;
    public int visibility;

    public GridShader(String vertShaderPath, String fragShaderPath) throws Exception {
        super(vertShaderPath, fragShaderPath);
    }

    @Override
    public void bindAttribute() {
        GL40.glBindAttribLocation(programID, 0, "fragTextureCoord");
    }

    @Override
    public void initUnifrom() {
        titleLocation = super.createUnifrom("titleLocation");
        titleIndex = super.createUnifrom("titleIndex");
        visibility = super.createUnifrom("visibility");
    }

    public void setUnifromDataMatrix(Matrix4f matrix) {
        super.unifromMatrix(titleLocation, matrix);
    }

    public void setTitleIndex(int index) {
        super.unifromFloat(titleIndex, index);
    }

    public void setVisibility(int data) {
        super.unifromFloat(visibility, data);
    }

}
