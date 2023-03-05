package engine;

import java.util.Stack;

import org.lwjgl.glfw.GLFW;

import game.GV;
import game.logic.grid.DynamicGrid;

public class Keyboard implements Runnable {
    private Window window;
    private Thread thread = new Thread(this, "Keyboard");

    private DynamicGrid dynamicGrid;

    /*---------------------------------------------------------*/

    private final int[] timingKey = {
            GLFW.GLFW_KEY_DOWN,
            GLFW.GLFW_KEY_LEFT,
            GLFW.GLFW_KEY_RIGHT,
            324, 326
    };

    /*---------------------------------------------------------*/

    private Stack<Integer> keyStack = new Stack<Integer>();

    /*---------------------------------------------------------*/
    public Keyboard(Window window, DynamicGrid dynamicGrid) {
        this.window = window;
        this.dynamicGrid = dynamicGrid;

        GLFW.glfwSetKeyCallback(window.window, (windowed, key, code, action, mods) -> {
            // need rework
            if (action == GLFW.GLFW_PRESS) {
                for (Integer keys : timingKey)
                    if (key == keys) {
                        keyStack.push(keys);
                        movementMultiplyer = GV.DAS;
                        timer = 0;
                    }

                checkKey(key);
                nonTimingKey(key);

                if (key == GLFW.GLFW_KEY_DOWN) {
                    dynamicGrid.downHold = true;

                }
            }

            if (action == GLFW.GLFW_RELEASE) {
                if (key == GLFW.GLFW_KEY_DOWN) {
                    dynamicGrid.downHold = false;

                }
            }
        });
    }
    /*---------------------------------------------------------*/

    private boolean keyPressed(int keyCode) {
        return (GLFW.glfwGetKey(window.window, keyCode) == GLFW.GLFW_PRESS);
    }

    /*---------------------------------------------------------*/

    public void start() {
        thread.run();
    }

    @Override
    public void run() {

        updateFrametime();
        stackCheck();

    }

    /*---------------------------------------------------------*/
    // need rework
    private void stackCheck() {
        if (!keyStack.isEmpty()) {
            timer += deltaFrametime;
            if (timer >= GV.ms * movementMultiplyer) {
                checkKey(keyStack.peek());
                movementMultiplyer = GV.ARR;
            }
            if (keyPressed(keyStack.peek()))
                return;
            else {
                keyStack.pop();
            }
        }
    }

    // Field key update
    /*---------------------------------------------------------*/
    public void nonTimingKey(int key) {
        switch (key) {
            case 328:
            case GLFW.GLFW_KEY_UP:
                dynamicGrid.nextRotation = (dynamicGrid.nextRotation + 3) % 4;
                break;

            case GLFW.GLFW_KEY_Z:
                dynamicGrid.nextRotation = (dynamicGrid.nextRotation + 1) % 4;
                break;

            case GLFW.GLFW_KEY_A:
                dynamicGrid.nextRotation = (dynamicGrid.nextRotation + 2) % 4;
                break;

            case GLFW.GLFW_KEY_SPACE:
                dynamicGrid.placeTetromino();
                break;
            default:
                break;
        }
        dynamicGrid.dynamicGhostHeight = dynamicGrid.yStaticCoord;

    }

    float movementMultiplyer = GV.DAS;
    public double timer;

    private double lastFrametime;
    private double deltaFrametime;

    private void updateFrametime() {
        double currentFrametime = GLFW.glfwGetTime();
        deltaFrametime = currentFrametime - lastFrametime;
        lastFrametime = currentFrametime;
    }

    private void checkKey(int key) {
        switch (key) {
            case 324:
            case GLFW.GLFW_KEY_LEFT:
                dynamicGrid.xDynamicCoord--;
                dynamicGrid.dynamicGhostHeight = dynamicGrid.yStaticCoord;
                timer = 0;
                break;

            case 326:
            case GLFW.GLFW_KEY_RIGHT:
                dynamicGrid.xDynamicCoord++;
                dynamicGrid.dynamicGhostHeight = dynamicGrid.yStaticCoord;
                timer = 0;
                break;
        }

    }
}
