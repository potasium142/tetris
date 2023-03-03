package game.object.grid;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL40;

import engine.Object;
import engine.ObjectLoader;
import engine.Renderer;
import game.GV;

public abstract class GridRender {
    protected GridShader shader;
    protected Object grid;

    // logic run on 10*40 grid, render 10*20 grid

    public int gridRender[][] = new int[11][25];

    protected final float o12 = (float) 1 / 12;

    protected final float h = (GV.height * 2);
    protected final float w = (GV.width * 2);
    protected final float r = 1 / (w * 20);

    protected final float titleScale = .8f;

    protected final float xCoord = (h * r) * titleScale;
    protected final float yCoord = (w * r) * titleScale;

    protected final float xOffset = -xCoord * 10;
    protected final float yOffset = -yCoord * 20;

    private float[] textureCoordinates = {
            0, 0,
            0, 1,
            o12, 0,
            o12, 1
    };

    private final float[] vertices = {
            -xCoord, yCoord,
            -xCoord, -yCoord,
            xCoord, yCoord,
            xCoord, -yCoord,
    };

    public void render() {

        shader.start();
        // GL40.glClear(GL40.GL_COLOR_BUFFER_BIT | GL40.GL_DEPTH_BUFFER_BIT);
        GL40.glBindVertexArray(grid.meshID);

        GL40.glEnableVertexAttribArray(0);
        GL40.glEnableVertexAttribArray(1);

        GL40.glEnable(GL40.GL_BLEND);
        GL40.glBlendFunc(GL40.GL_SRC_ALPHA, GL40.GL_ONE_MINUS_SRC_ALPHA);

        GL40.glActiveTexture(GL40.GL_TEXTURE);
        GL40.glBindTexture(GL40.GL_TEXTURE_2D, grid.textureID);

        // for (int j = 0; j < 20; j++) {
        // for (int i = 0; i < 10; i++) {
        // Matrix4f matrix4f = createUITransformationMatrix(xOffset + i * xCoord * 2,
        // yOffset + j * yCoord * 2);

        // shader.setTitleIndex(gridRender[i][j] + 7);

        // shader.loadTransformation(matrix4f);
        // GL40.glDrawArrays(GL40.GL_TRIANGLE_STRIP, 0, grid.vertexCount);
        // }
        // }
        renderGrid();

        GL40.glDisable(GL40.GL_BLEND);

        GL40.glDisableVertexAttribArray(0);
        GL40.glDisableVertexAttribArray(1);

        GL40.glBindVertexArray(0);
        shader.stop();
    }

    public GridRender(ObjectLoader objectLoader, Renderer renderer) throws Exception {
        this.shader = new GridShader("./src/main/java/game/object/grid/vert.vert",
                "./src/main/java/game/object/grid/frag.frag");

        int[] textureAttribute = objectLoader.loadTexture(GV.tetromino);

        this.grid = objectLoader.loadMesh2D(vertices, textureCoordinates);
        grid.textureID = textureAttribute[0];
    }

    public static Matrix4f createUITransformationMatrix(float x, float y) {
        Matrix4f matrix = new Matrix4f();

        matrix.identity().translate(new Vector3f(x, y, 0), matrix);

        return matrix;
    }

    protected abstract void renderGrid();

}
