package game.logic.grid;

import org.joml.Matrix4f;
import org.lwjgl.opengl.GL40;

import engine.ObjectLoader;
import engine.Renderer;
import game.object.grid.GridRender;
import game.object.grid.GridShader;

public class BackgroundGrid extends GridRender {
    private StaticGrid staticGrid;

    public BackgroundGrid(ObjectLoader objectLoader, Renderer renderer, StaticGrid staticGrid) throws Exception {
        super(objectLoader, renderer);
        this.shader = new GridShader("./src/main/java/game/logic/grid/shader/bg_vert.vert",
                "./src/main/java/game/logic/grid/shader/bg_frag.frag");
        this.grid = objectLoader.loadMesh2D(super.vertices, textureCoordinates);
        super.grid.textureID = objectLoader.loadTexture("./src/main/asset/blank_tile.png")[0];
        this.staticGrid = staticGrid;
    }

    private float[] textureCoordinates = {
            0, 0,
            0, 1f,
            1f, 0,
            1, 1f
    };

    @Override
    protected void renderGrid() {
        for (int y = 0; y < 22; y++) {
            for (int x = 0; x < 10; x++) {
                Matrix4f matrix4f = createUITransformationMatrix(xOffset + x * xCoord * 2, yOffset + y * yCoord * 2);

                // shader.setVisibility(1f);
                if (staticGrid.gridLogic[y][x] != 0)
                    continue;
                shader.setUnifromDataMatrix(matrix4f);
                GL40.glDrawArrays(GL40.GL_TRIANGLE_STRIP, 0, grid.vertexCount);
            }
        }
    }

}
