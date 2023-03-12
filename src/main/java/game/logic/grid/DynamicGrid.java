package game.logic.grid;

import java.util.Iterator;
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

    public boolean logicRunning = true;

    private Bag bag = new Bag();

    private StaticGrid staticGrid;
    private Window window;

    private Tetromino hold;

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
        nextPiece();
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

    float visibility = 1;

    @Override
    protected void renderGrid() {
        for (int x = 0; x < tetromino.gridSize; x++) {
            for (int y = 0; y < tetromino.gridSize; y++) {

                int currentTitle = tetromino.rotation[currentRotation][y][x];
                if (currentTitle == 0)
                    continue;

                Matrix4f matrix4f = createUITransformationMatrix(xOffset + (x + xStaticCoord) * xCoord * 2,
                        yOffset + (y + yStaticCoord) * yCoord * 2);

                shader.setTitleIndex(currentTitle - 1);
                shader.setVisibility(visibility);

                shader.setUnifromDataMatrix(matrix4f);
                GL40.glDrawArrays(GL40.GL_TRIANGLE_STRIP, 0, grid.vertexCount);

                // draw ghost pieces

                matrix4f = createUITransformationMatrix(xOffset + (x + xStaticCoord) * xCoord * 2,
                        yOffset + (y + staticGhostHeight) * yCoord * 2);

                shader.setTitleIndex(7);
                shader.setVisibility(1f);

                shader.setUnifromDataMatrix(matrix4f);
                GL40.glDrawArrays(GL40.GL_TRIANGLE_STRIP, 0, grid.vertexCount);

            }

        }
        if (hold != null) {
            for (int x = 0; x < hold.gridSize; x++) {
                for (int y = 0; y < hold.gridSize; y++) {
                    int currentTitle = hold.rotation[2][y][x];
                    if (currentTitle == 0)
                        continue;

                    Matrix4f matrix4f = createUITransformationMatrix(xOffset + (x - 6) * xCoord * 2,
                            yOffset + (y + 16) * yCoord * 2);

                    shader.setTitleIndex(currentTitle - 1);

                    shader.setUnifromDataMatrix(matrix4f);
                    GL40.glDrawArrays(GL40.GL_TRIANGLE_STRIP, 0, grid.vertexCount);

                }
            }
        }
        Iterator<Tetromino> previewBag = bag.bag.iterator();
        for (int i = 4; i != 0; i--) {
            Tetromino currentPreview = previewBag.next();
            for (int x = 0; x < currentPreview.gridSize; x++) {
                for (int y = 0; y < currentPreview.gridSize; y++) {
                    int currentTitle = currentPreview.rotation[2][y][x];
                    if (currentTitle == 0)
                        continue;

                    Matrix4f matrix4f = createUITransformationMatrix(xOffset + (x + 13) * xCoord * 2,
                            yOffset + (y + (i * 5) - 5) * yCoord * 2);

                    shader.setTitleIndex(currentTitle - 1);

                    shader.setUnifromDataMatrix(matrix4f);
                    GL40.glDrawArrays(GL40.GL_TRIANGLE_STRIP, 0, grid.vertexCount);

                }
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

    // movement stack
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
                if (gridCheck()) {
                    movementMultiplyer = GV.ARR * GV.ms;
                    softGravityLockTimer = 0;
                }

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
                softGravityLockTimer = 0;

                break;

            case GLFW.GLFW_KEY_Z:
                rotation(nextRotation = (nextRotation + 1) % 4);
                softGravityLockTimer = 0;

                break;

            case GLFW.GLFW_KEY_A:
                rotation180(nextRotation = (nextRotation + 2) % 4);
                softGravityLockTimer = 0;

                break;

            case GLFW.GLFW_KEY_KP_5:
            case GLFW.GLFW_KEY_KP_2:
            case GLFW.GLFW_KEY_DOWN:
                downHold = true;
                break;

            case GLFW.GLFW_KEY_SPACE:
                if (movementTimer >= GV.DCD * GV.ms)
                    placeTetromino();
                break;

            case GLFW.GLFW_KEY_R:
                reset();
                break;

            case GLFW.GLFW_KEY_LEFT_SHIFT:
                holdPiece();
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

    boolean holded = false;

    void holdPiece() {
        if (hold == null) {
            hold = tetromino;
            tetromino = bag.bag.poll();
            resetPiece();
            holded = true;
            return;
        }

        if (holded)
            return;

        Tetromino tmp;
        tmp = tetromino;
        tetromino = hold;
        hold = tmp;
        resetPiece();

        holded = true;

    }

    void rotation(int nextState) {

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

    void rotation180(int nextState) {

        int[][] currentOffsetTable = tetromino.offsetData_180[currentRotation];

        for (int i = 0; i < currentOffsetTable.length; i++) {
            int xOffsetPlacement = xDynamicCoord + (currentOffsetTable[i][0]);
            int yOffsetPlacement = yDynamicCoord + (currentOffsetTable[i][1]);
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

    public void reset() {
        staticGrid.reset();
        hold = null;
        bag.reset();
        tetromino = bag.bag.poll();
        bag.checkBag();
        resetPiece();
        logicRunning = true;
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

    float hardGravityLockTimer = 0;
    float softGravityLockTimer = 0;

    private void gravityLock() {
        if (yStaticCoord != staticGhostHeight) {
            hardGravityLockTimer = 0;
            softGravityLockTimer = 0;
            return;
        }

        if (downHold)
            gravityMultiplyer *= 10;

        if (hardGravityLockTimer >= GV.ms * 100 || softGravityLockTimer >= gravityMultiplyer) {
            placeTetromino();
            hardGravityLockTimer = 0;
            softGravityLockTimer = 0;
        }

        hardGravityLockTimer += deltaFrametime;
        softGravityLockTimer += deltaFrametime;

        visibility = 1 - softGravityLockTimer / (gravityMultiplyer * 2);

    }

    private boolean gridCheck() {
        if (placementCheck(xDynamicCoord, yDynamicCoord, nextRotation)) {
            xStaticCoord = xDynamicCoord;
            yStaticCoord = yDynamicCoord;
            currentRotation = nextRotation;
            return true;
        }
        xDynamicCoord = xStaticCoord;
        yDynamicCoord = yStaticCoord;
        nextRotation = currentRotation;

        return false;
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
        nextPiece();
        resetPiece();
    }

    void nextPiece() {
        tetromino = bag.bag.poll();
        bag.checkBag();
    }

    private void resetPiece() {

        holded = false;
        currentRotation = 2;
        nextRotation = 2;

        visibility = 1;

        xDynamicCoord = 5 - (tetromino.gridSize >> 1);
        yDynamicCoord = 19;
        xStaticCoord = 5 - (tetromino.gridSize >> 1);
        yStaticCoord = 19;
        currentRotation = 0;
        staticGhostHeight = yStaticCoord;
        if (!gridCheck()) {
            logicRunning = false;
        }
    }

    private void ghostPiece() {
        dynamicGhostHeight = yStaticCoord;
        while (placementCheck(xDynamicCoord, dynamicGhostHeight - 1, currentRotation)) {
            dynamicGhostHeight--;
        }
        staticGhostHeight = dynamicGhostHeight;
    }
}
