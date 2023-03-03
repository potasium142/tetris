package game.logic.grid;

import org.joml.Matrix4f;
import org.lwjgl.opengl.GL40;

import engine.ObjectLoader;
import engine.Renderer;
import game.object.grid.GridRender;

public class StaticGrid extends GridRender {
    public StaticGrid(ObjectLoader objectLoader, Renderer renderer) throws Exception {
        super(objectLoader, renderer);
    }

    int[][] gridLogic = new int[11][25];

    @Override
    protected void renderGrid() {
        for (int y = 0; y < 20; y++) {
            for (int x = 0; x < 10; x++) {
                Matrix4f matrix4f = createUITransformationMatrix(xOffset + x * xCoord * 2, yOffset + y * yCoord * 2);

                shader.setTitleIndex(gridRender[x][y] + 7);

                shader.setUnifromDataMatrix(matrix4f);
                GL40.glDrawArrays(GL40.GL_TRIANGLE_STRIP, 0, grid.vertexCount);
            }
        }
    }

}
