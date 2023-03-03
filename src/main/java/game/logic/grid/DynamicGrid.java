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

    public DynamicGrid(ObjectLoader objectLoader, Renderer renderer) throws Exception {
        super(objectLoader, renderer);
    }

    private Thread thread = new Thread(this);

    public int state = 2;
    public int xOrigin = 4 - (t.gridSize >> 1);
    public int yOrigin = 19;

    public void startLogic() {
        thread.start();
    }

    @Override
    protected void renderGrid() {
        for (int x = 0; x < t.gridSize; x++) {
            for (int y = 0; y < t.gridSize; y++) {
                Matrix4f matrix4f = createUITransformationMatrix(xOffset + (x + xOrigin) * xCoord * 2,
                        yOffset + (y + yOrigin) * yCoord * 2);

                int currentTitle = t.rotation[state][x + (y * 3)];

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
    private double deltaFrametime;

    private void updateFrametime() {
        double currentFrametime = GLFW.glfwGetTime();
        deltaFrametime = currentFrametime - lastFrametime;
        lastFrametime = currentFrametime;
    }

    public boolean downHold = false;

    @Override
    public void run() {
        float gravityMultiplyer;
        while (true) {
            updateFrametime();
            timer += deltaFrametime;

            gravityMultiplyer = downHold ? GV.gravity / GV.softdropMultiplyer : GV.gravity;

            if (timer >= gravityMultiplyer) {
                timer = 0;
                yOrigin--;
                yOrigin = Math.max(yOrigin, 0 - (t.gridSize >> 1));
            }
        }
    }
}
