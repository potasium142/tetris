package game.logic.grid;

import java.util.Arrays;

import org.joml.Matrix4f;
import org.lwjgl.opengl.GL40;

import engine.ObjectLoader;
import engine.Renderer;
import game.object.grid.GridRender;

public class StaticGrid extends GridRender implements Runnable {
    Thread thread = new Thread(this);

    public StaticGrid(ObjectLoader objectLoader, Renderer renderer) throws Exception {
        super(objectLoader, renderer);

        // DAMN YOU JAVA
        Arrays.fill(horizontalBorder, -1);
        verticalBorder[0] = -1;
        verticalBorder[11] = -1;

        for (int i = 0; i < 30; i++) {
            gridLogic[i] = verticalBorder.clone();
        }

        gridLogic[0] = horizontalBorder.clone();

    }

    int gridLogic[][] = new int[30][12];

    int verticalBorder[] = new int[12];
    int horizontalBorder[] = new int[12];

    @Override
    protected void renderGrid() {
        for (int y = 0; y < 22; y++) {
            for (int x = 0; x < 12; x++) {
                Matrix4f matrix4f = createUITransformationMatrix(xOffset + x * xCoord * 2, yOffset + y * yCoord * 2);

                shader.setTitleIndex(gridLogic[y][x] + 7);

                shader.setUnifromDataMatrix(matrix4f);
                GL40.glDrawArrays(GL40.GL_TRIANGLE_STRIP, 0, grid.vertexCount);
            }
        }
    }

    @Override
    public void run() {
        int lineAmount = 0;
        int endLine = 0;
        for (int y = 1; y < 25; y++) {
            int amount = 0;
            for (int slot : gridLogic[y]) {
                if (slot != 0)
                    amount++;
            }
            if (amount == 12) {
                endLine = y;
                lineAmount++;
                continue;
            }
        }
    }

    public void startLogic() {
        thread.run();
    }

}
