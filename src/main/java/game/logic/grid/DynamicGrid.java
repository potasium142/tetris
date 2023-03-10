package game.logic.grid;

import java.util.Stack;

import org.joml.Matrix4f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL40;

import engine.ObjectLoader;
import engine.Renderer;
import engine.Window;
import game.GV;
import game.object.grid.GridRender;
import game.object.tetromino.Bag;
import game.object.tetromino.Tetromino;

public class DynamicGrid extends GridRender implements Runnable {
    public Tetromino tetromino;

    private Bag bag = new Bag();

    private StaticGrid staticGrid;
    private Window window;

    public DynamicGrid(ObjectLoader objectLoader,
            Renderer renderer,
            StaticGrid staticGrid,
            Window window) throws Exception {
        super(objectLoader, renderer);
        this.staticGrid = staticGrid;
        this.window = window;

        GLFW.glfwSetKeyCallback(window.window, (windowed, key, code, action, mods) -> {
            if (action == GLFW.GLFW_PRESS) {
                for (int timeKey : timingKey) {
                    if (key == timeKey) {
                        repeatKey(key);
                        keyStack.add(key);
                        movementMultiplyer = GV.DAS * GV.ms;
                        movementTimer = 0;
                        break;
                    }
                }
                nonRepeatKey(key);
            }
            if (action == GLFW.GLFW_RELEASE) {
                switch (key) {
                    case GLFW.GLFW_KEY_KP_5:
                    case GLFW.GLFW_KEY_KP_2:
                    case GLFW.GLFW_KEY_DOWN:
                        downHold = false;
                        break;
                }
            }
        });

        resetPiece();
    }

    private Thread thread = new Thread(this);

    public int currentRotation;
    public int nextRotation;

    public int xDynamicCoord;
    public int yDynamicCoord;
    public int xStaticCoord;
    public int yStaticCoord;
    public int dynamicGhostHeight;
    public int staticGhostHeight;

    public void startLogic() {
        thread.run();
    }

    @Override
    protected void renderGrid() {
        for (int x = 0; x < tetromino.gridSize; x++) {
            for (int y = 0; y < tetromino.gridSize; y++) {
                Matrix4f matrix4f = createUITransformationMatrix(xOffset + (x + xStaticCoord) * xCoord * 2,
                        yOffset + (y + staticGhostHeight) * yCoord * 2);

                int currentTitle = tetromino.rotation[currentRotation][y][x];

                if (currentTitle == 0)
                    continue;

                shader.setTitleIndex(8);

                shader.setUnifromDataMatrix(matrix4f);
                GL40.glDrawArrays(GL40.GL_TRIANGLE_STRIP, 0, grid.vertexCount);
            }
            for (int y = 0; y < tetromino.gridSize; y++) {
                Matrix4f matrix4f = createUITransformationMatrix(xOffset + (x + xStaticCoord) * xCoord * 2,
                        yOffset + (y + yStaticCoord) * yCoord * 2);

                int currentTitle = tetromino.rotation[currentRotation][y][x];

                if (currentTitle == 0)
                    continue;

                shader.setTitleIndex(currentTitle - 1);

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

    double movementTimer = 0;
    double gravityTimer = 0;

    public Stack<Integer> keyStack = new Stack<Integer>();

    private final int[] timingKey = {
            GLFW.GLFW_KEY_DOWN,
            GLFW.GLFW_KEY_LEFT,
            GLFW.GLFW_KEY_RIGHT,
            324, 326
    };
    float movementMultiplyer;
    float gravityMultiplyer;

    void movement() {

        try {
            while (!(GLFW.glfwGetKey(window.window, keyStack.peek()) == GLFW.GLFW_PRESS)) {
                keyStack.pop();
                movementMultiplyer = GV.DAS * GV.ms;

            }
            if (movementTimer >= movementMultiplyer) {
                movementTimer = 0;
                repeatKey(keyStack.peek());
                gridCheck();
                movementMultiplyer = GV.ARR * GV.ms;
            }

        } catch (Exception e) {
        }

        gravityMultiplyer = downHold ? GV.gravity / GV.softdropMultiplyer : GV.gravity;

        if (gravityTimer >= gravityMultiplyer) {
            gravityTimer = 0;
            yDynamicCoord--;
        }
    }

    boolean nonRepeatKey(int key) {
        switch (key) {

            case GLFW.GLFW_KEY_KP_8:
                rotation(nextRotation = (nextRotation + 3) % 4);
                break;

            case GLFW.GLFW_KEY_Z:
                rotation(nextRotation = (nextRotation + 1) % 4);
                break;

            case GLFW.GLFW_KEY_A:
                rotation(nextRotation = (nextRotation + 2) % 4);
                break;

            case GLFW.GLFW_KEY_KP_5:
            case GLFW.GLFW_KEY_KP_2:
            case GLFW.GLFW_KEY_DOWN:
                downHold = true;
                break;

            case GLFW.GLFW_KEY_SPACE:
                placeTetromino();
                break;

            default:
                return false;
        }
        return true;
    }

    boolean repeatKey(int key) {
        switch (key) {
            case GLFW.GLFW_KEY_KP_4:
            case GLFW.GLFW_KEY_LEFT:
                xDynamicCoord = xDynamicCoord - 1;
                break;

            case GLFW.GLFW_KEY_KP_6:
            case GLFW.GLFW_KEY_RIGHT:
                xDynamicCoord = xDynamicCoord + 1;
                break;

            default:
                return false;
        }
        return true;
    }

    void rotation(int nextState) {
        // if (placementCheck(xDynamicCoord, yDynamicCoord, nextState)) {
        // xStaticCoord = xDynamicCoord;
        // yStaticCoord = yDynamicCoord;
        // currentRotation = nextRotation;

        // } else

        {

            System.out.println(currentRotation + " : " + nextState);
            int[][] currentOffsetTable = tetromino.offsetData[currentRotation];
            int[][] nextOffsetTable = tetromino.offsetData[nextState];

            for (int i = 0; i < nextOffsetTable.length; i++) {
                int xOffsetPlacement = xDynamicCoord + (currentOffsetTable[i][0] - nextOffsetTable[i][0]);
                int yOffsetPlacement = yDynamicCoord + (currentOffsetTable[i][1] - nextOffsetTable[i][1]);
                if (placementCheck(
                        xOffsetPlacement,
                        yOffsetPlacement,
                        nextState)) {
                    xStaticCoord = xOffsetPlacement;
                    yStaticCoord = yOffsetPlacement;

                    currentRotation = nextRotation;

                    break;
                }
            }
        }
    }

    @Override
    public void run() {
        movement();
        gridCheck();
        ghostPiece();
        gravityLock();
        updateFrametime();
        gravityTimer += deltaFrametime;
        movementTimer += deltaFrametime;

    }

    float gravityLockTimer = 0;

    private void gravityLock() {
        if (yStaticCoord != staticGhostHeight) {
            gravityLockTimer = 0;
            return;
        }

        if (gravityLockTimer >= gravityMultiplyer * 10) {
            placeTetromino();
            gravityLockTimer = 0;
        }

        gravityLockTimer += deltaFrametime;
    }

    private void gridCheck() {
        if (placementCheck(xDynamicCoord, yDynamicCoord, nextRotation)) {
            xStaticCoord = xDynamicCoord;
            yStaticCoord = yDynamicCoord;
            currentRotation = nextRotation;

        } else {
            xDynamicCoord = xStaticCoord;
            yDynamicCoord = yStaticCoord;
            nextRotation = currentRotation;
        }
    }

    private boolean placementCheck(int xPos, int yPos, int rotation) {
        try {
            for (int y = 0; y < tetromino.gridSize; y++) {
                for (int x = 0; x < tetromino.gridSize; x++) {
                    if (tetromino.rotation[rotation][y][x] == 0)
                        continue;
                    if (staticGrid.gridLogic[yPos + y][xPos + x] != 0)
                        return false;

                }
            }
            return true;
        } catch (ArrayIndexOutOfBoundsException e) {
            return false;
        }
    }

    public void placeTetromino() {
        for (int y = 0; y < tetromino.gridSize; y++) {
            for (int x = 0; x < tetromino.gridSize; x++) {
                if (tetromino.rotation[nextRotation][y][x] == 0)
                    continue;
                staticGrid.gridLogic[dynamicGhostHeight + y][xStaticCoord
                        + x] += tetromino.rotation[nextRotation][y][x];
            }
        }
        resetPiece();
    }

    private void resetPiece() {
        tetromino = bag.bag.poll();
        bag.checkBag();
        currentRotation = 2;
        nextRotation = 2;

        xDynamicCoord = 4 - (tetromino.gridSize >> 1);
        yDynamicCoord = 19;
        xStaticCoord = 4 - (tetromino.gridSize >> 1);
        yStaticCoord = 19;
        currentRotation = 0;
        staticGhostHeight = yStaticCoord;
    }

    private void ghostPiece() {
        dynamicGhostHeight = yStaticCoord;
        while (placementCheck(xDynamicCoord, dynamicGhostHeight - 1, currentRotation)) {
            dynamicGhostHeight--;
        }
        staticGhostHeight = dynamicGhostHeight;
    }
}
