package game.object.grid;

import engine.ObjectLoader;
import engine.Renderer;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL40;

import engine.Object;
import game.GV;

public class Grid {
    private GridShader shader;

    // logic run on 10*40 grid, render 10*20 grid
    private int gridState[][] = new int[10][41];

    private float o12 = (float) 1 / 12;

    private final float h = (GV.height * 2);
    private final float w = (GV.width * 2);
    private final float r = 1 / (w * 20);

    private final float titleScale = .8f;

    private final float x = (h * r) * titleScale;
    private final float y = (w * r) * titleScale;

    private final float xOffset = -x * 10;
    private final float yOffset = -y * 20;

    private float[] textureCoordinates = {
            0, 0,
            0, 1,
            o12, 0,
            o12, 1
    };

    // private final float[] vertices = {
    // -1f, 1f,
    // -1f, -1f,

    // 1f, 1f,
    // 1f, -1f,
    // };
    private final float[] vertices = {
            -x, y,
            -x, -y,
            x, y,
            x, -y,
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
        for (int i = 1; i <= 10; i++) {
            for (int j = 1; j <= 20; j++) {
                Matrix4f matrix4f = createUITransformationMatrix(xOffset + i * x * 2, yOffset + j * y * 2);
                shader.setTitleIndex(7);
                shader.loadTransformation(matrix4f);
                GL40.glDrawArrays(GL40.GL_TRIANGLE_STRIP, 0, grid.vertexCount);
            }
        }

        GL40.glDisable(GL40.GL_BLEND);

        GL40.glDisableVertexAttribArray(0);
        GL40.glDisableVertexAttribArray(1);

        GL40.glBindVertexArray(0);
        shader.stop();
    }

    public Grid(ObjectLoader objectLoader, Renderer renderer) throws Exception {
        this.shader = new GridShader("./src/main/java/game/object/grid/vert.vert",
                "./src/main/java/game/object/grid/frag.frag");

        int[] textureAttribute = objectLoader.loadTexture(GV.tetriminoTexture);

        this.grid = objectLoader.loadMesh2D(vertices, textureCoordinates);
        grid.textureID = textureAttribute[0];
    }

    public static Matrix4f createUITransformationMatrix(
            float x, float y) {
        Matrix4f matrix = new Matrix4f();

        matrix.identity().translate(new Vector3f(x, y, 0), matrix);

        return matrix;
    }

    private Object grid;
}
