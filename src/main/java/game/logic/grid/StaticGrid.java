package game.logic.grid;

import org.joml.Matrix4f;
import org.lwjgl.opengl.GL40;

import engine.ObjectLoader;
import engine.Renderer;
import game.object.grid.GridRender;

public class StaticGrid extends GridRender implements Runnable {
    Thread thread = new Thread(this);

    public StaticGrid(ObjectLoader objectLoader, Renderer renderer) throws Exception {
        super(objectLoader, renderer);
    }

    int gridLogic[][] = new int[30][10];

    @Override
    protected void renderGrid() {
        for (int y = 0; y < 22; y++) {
            for (int x = 0; x < 10; x++) {
                Matrix4f matrix4f = createUITransformationMatrix(xOffset + x * xCoord * 2, yOffset + y * yCoord * 2);

                if (gridLogic[y][x] == 0)
                    continue;

                shader.setTitleIndex(gridLogic[y][x] - 1);

                shader.setUnifromDataMatrix(matrix4f);
                GL40.glDrawArrays(GL40.GL_TRIANGLE_STRIP, 0, grid.vertexCount);
            }
        }
    }

    @Override
    public void run() {
        int offset = 1;
        for (int i = 2; i < 22; i++) {
            int amount = 0;
            for (int tile : gridLogic[i]) {
                if (tile != 0)
                    amount++;
            }

            for (int j = 1; j < offset; j++) {
                gridLogic[i] = gridLogic[j + i].clone();
            }
            if (amount == 14)
                offset++;
        }
    }

    public void startLogic() {
        thread.run();
    }

}
