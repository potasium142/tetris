package game.logic.grid;

import org.joml.Matrix4f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL40;

import engine.ObjectLoader;
import engine.Renderer;
import game.GV;
import game.object.grid.GridRender;
import game.object.tetromino.T;

public class DynamicGrid extends GridRender implements Runnable {
    public T t = new T();

    private StaticGrid staticGrid;

    public DynamicGrid(ObjectLoader objectLoader, Renderer renderer,
            StaticGrid staticGrid)
            throws Exception {
        super(objectLoader, renderer);
        this.staticGrid = staticGrid;
        resetPiece();
    }

    private Thread thread = new Thread(this);

    private int currentRotation;
    public int nextRotation;

    public int xDynamicCoord;
    public int yDynamicCoord;
    private int xStaticCoord;
    public int yStaticCoord;
    public int dynamicGhostHeight;
    public int staticGhostHeight;

    public void startLogic() {
        thread.run();
    }

    @Override
    protected void renderGrid() {
        for (int x = 0; x < t.gridSize; x++) {
            for (int y = 0; y < t.gridSize; y++) {
                Matrix4f matrix4f = createUITransformationMatrix(xOffset + (x + xStaticCoord) * xCoord * 2,
                        yOffset + (y + staticGhostHeight) * yCoord * 2);

                int currentTitle = t.rotation[currentRotation][y][x];

                if (currentTitle == 0)
                    continue;

                shader.setTitleIndex(currentTitle + 9);

                shader.setUnifromDataMatrix(matrix4f);
                GL40.glDrawArrays(GL40.GL_TRIANGLE_STRIP, 0, grid.vertexCount);
            }
            for (int y = 0; y < t.gridSize; y++) {
                Matrix4f matrix4f = createUITransformationMatrix(xOffset + (x + xStaticCoord) * xCoord * 2,
                        yOffset + (y + yStaticCoord) * yCoord * 2);

                int currentTitle = t.rotation[currentRotation][y][x];

                if (currentTitle == 0)
                    continue;

                shader.setTitleIndex(currentTitle + 7);

                shader.setUnifromDataMatrix(matrix4f);
                GL40.glDrawArrays(GL40.GL_TRIANGLE_STRIP, 0, grid.vertexCount);
            }

        }
    }

    public double timer;

    private double lastFrametime;
    public double deltaFrametime;

    private void updateFrametime() {
        double currentFrametime = GLFW.glfwGetTime();
        deltaFrametime = currentFrametime - lastFrametime;
        lastFrametime = currentFrametime;
    }

    public boolean downHold = false;

    @Override
    public void run() {
        float gravityMultiplyer;
        ghostPiece();
        gridCheck();
        updateFrametime();
        timer += deltaFrametime;

        gravityMultiplyer = downHold ? GV.gravity / GV.softdropMultiplyer : GV.gravity;

        if (timer >= gravityMultiplyer) {
            timer = 0;
            yDynamicCoord--;
        }
    }

    private void gridCheck() {
        if (placementCheck(xDynamicCoord, yDynamicCoord)) {
            xStaticCoord = xDynamicCoord;
            yStaticCoord = yDynamicCoord;
            currentRotation = nextRotation;
            staticGhostHeight = dynamicGhostHeight;

        } else {
            xDynamicCoord = xStaticCoord;
            yDynamicCoord = yStaticCoord;
            nextRotation = currentRotation;
            dynamicGhostHeight = staticGhostHeight;

        }
    }

    private boolean placementCheck(int xPos, int yPos) {
        try {
            for (int y = 0; y < t.gridSize; y++) {
                for (int x = 0; x < t.gridSize; x++) {
                    if (t.rotation[nextRotation][y][x]
                            * staticGrid.gridLogic[yPos + y][xPos + x] != 0)
                        return false;

                }
            }
            return true;
        } catch (ArrayIndexOutOfBoundsException e) {
            return false;
        }
    }

    public void placeTetromino() {
        for (int y = 0; y < t.gridSize; y++) {
            for (int x = 0; x < t.gridSize; x++) {
                staticGrid.gridLogic[dynamicGhostHeight + y][xStaticCoord + x] += t.rotation[nextRotation][y][x];
            }
        }
        resetPiece();
    }

    private void resetPiece() {
        currentRotation = 2;
        nextRotation = 2;

        xDynamicCoord = 4 - (t.gridSize >> 1);
        yDynamicCoord = 19;
        xStaticCoord = 4 - (t.gridSize >> 1);
        yStaticCoord = 19;
        currentRotation = 0;
        dynamicGhostHeight = yStaticCoord;
    }

    private void ghostPiece() {
        while (true) {
            if (placementCheck(xDynamicCoord, dynamicGhostHeight - 1)) {
                dynamicGhostHeight--;
            } else {
                break;
            }
        }
    }
}
