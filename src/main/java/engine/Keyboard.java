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
            if (action == GLFW.GLFW_PRESS) {
                System.out.println(key);
                for (Integer keys : timingKey)
                    if (key == keys) {
                        keyStack.push(keys);
                        movementMultiplyer = GV.DAS;
                        timer = 0;
                    }
                nonTimingKey(key);
                if (!keyStack.isEmpty())
                    checkKey();

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
        thread.start();
    }

    @Override
    public void run() {
        try {
            while (true) {
                updateFrametime();
                stackCheck();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*---------------------------------------------------------*/

    private void stackCheck() {
        while (!keyStack.isEmpty()) {
            timer += deltaFrametime;
            if (timer >= GV.ms * movementMultiplyer) {
                checkKey();
                movementMultiplyer = GV.ARR;
                timer = 0;
            }
            if (keyPressed(keyStack.peek()))
                break;
            else {
                keyStack.pop();
                // movementMultiplyer = GV.DAS;
            }
        }

    }

    // Field key update
    /*---------------------------------------------------------*/
    public void nonTimingKey(int key) {
        switch (key) {
            case 328:
            case GLFW.GLFW_KEY_UP:
                dynamicGrid.state = (dynamicGrid.state + 3) % 4;
                break;
            case GLFW.GLFW_KEY_Z:
                dynamicGrid.state = (dynamicGrid.state + 1) % 4;
                break;
            case GLFW.GLFW_KEY_A:
                dynamicGrid.state = (dynamicGrid.state + 2) % 4;
                break;
            default:
                break;
        }
        // movementMultiplyer = GV.DCD;
    }

    float movementMultiplyer = GV.DAS;
    double timer;

    private double lastFrametime;
    private double deltaFrametime;

    private void updateFrametime() {
        double currentFrametime = GLFW.glfwGetTime();
        deltaFrametime = currentFrametime - lastFrametime;
        lastFrametime = currentFrametime;
    }

    public void timingKey() {
        timer += deltaFrametime;
        if (keyPressed(keyStack.peek())) {
            if (timer >= GV.ms * movementMultiplyer) {
                checkKey();
                movementMultiplyer = GV.ARR;
                timer = 0;
            }
        }
    }

    private void checkKey() {
        switch (keyStack.peek()) {
            case 324:
            case GLFW.GLFW_KEY_LEFT:
                dynamicGrid.xOrigin--;
                dynamicGrid.xOrigin = Math.max(dynamicGrid.xOrigin, 0);
                break;

            case 326:
            case GLFW.GLFW_KEY_RIGHT:
                dynamicGrid.xOrigin++;
                dynamicGrid.xOrigin = Math.min(dynamicGrid.xOrigin, 8 - (dynamicGrid.t.gridSize >> 1));
                break;

        }
    }
}
