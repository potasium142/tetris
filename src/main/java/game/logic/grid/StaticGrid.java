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

    int gridLogic[][] = new int[40][10];

    public void reset() {
        gridLogic = new int[40][10];
    }

    float visibility;

    @Override
    protected void renderGrid() {
        for (int y = 0; y < 22; y++) {
            for (int x = 0; x < 10; x++) {
                Matrix4f matrix4f = createUITransformationMatrix(xOffset + x * xCoord * 2, yOffset + y * yCoord * 2);

                shader.setVisibility(1f);
                if (gridLogic[y][x] == 0)
                    continue;
                shader.setVisibility(1 - visibility);
                shader.setTitleIndex(gridLogic[y][x] - 1);

                shader.setUnifromDataMatrix(matrix4f);
                GL40.glDrawArrays(GL40.GL_TRIANGLE_STRIP, 0, grid.vertexCount);
            }
        }
    }

    @Override
    public void run() {
        int i = 0;
        do {

            if (rowAmount(gridLogic[i]) == 10) {
                int offset = i + 1;
                System.out.println(offset);

                for (int j = offset; j < 22; j++) {
                    if (rowAmount(gridLogic[j]) == 10)
                        continue;
                    gridLogic[i++] = gridLogic[j].clone();
                }
                break;
            }

        } while (i++ < 22);
    }

    private int rowAmount(int[] row) {
        int amount = 0;
        for (int tile : row) {
            if (tile != 0)
                amount++;
        }
        return amount;
    }

    public void startLogic() {
        thread.run();
    }

}
