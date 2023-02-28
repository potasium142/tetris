package engine;

import java.util.Stack;

import org.lwjgl.glfw.GLFW;

import game.GV;
import game.logic.Field;

public class Keyboard implements Runnable {
    private Window window;
    private Thread thread = new Thread(this, "Keyboard");
    public Field field;

    /*-----------------------------------*/

    protected final int[] timingKey = {
            GLFW.GLFW_KEY_DOWN,
            GLFW.GLFW_KEY_LEFT,
            GLFW.GLFW_KEY_RIGHT,
    };

    protected final int[] nonTimingKey = {
            GLFW.GLFW_KEY_UP,
    };

    /*-----------------------------------*/

    public Stack<Integer> keyStack = new Stack<Integer>();

    /*-----------------------------------*/
    public Keyboard(Window window) {
        this.window = window;

        GLFW.glfwSetKeyCallback(window.window, (windowed, key, code, action, mods) -> {
            if (action == GLFW.GLFW_PRESS) {
                for (Integer keys : timingKey)
                    if (key == keys) {
                        keyStack.push(keys);
                        movementMultiplyer = GV.DAS;
                        timer = 0;
                    }
                nonTimingKey(key);
                if (!keyStack.isEmpty())
                    checkKey();
            }
        });
    }

    private boolean keyPressed(int keyCode) {
        return (GLFW.glfwGetKey(window.window, keyCode) == GLFW.GLFW_PRESS);
    }

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

    private void stackCheck() {
        while (!keyStack.isEmpty()) {
            timer += deltaFrametime;
            if (timer >= GV.ms * movementMultiplyer) {
                checkKey();
                movementMultiplyer = GV.ARR;
                timer = 0;
            }
            if (keyPressed(keyStack.peek())) {
                break;
            } else {
                keyStack.pop();
                movementMultiplyer = GV.DAS;
            }
        }
    }

    // Field key update
    /*-----------------------------------*/
    public void nonTimingKey(int key) {
        switch (key) {
            case GLFW.GLFW_KEY_UP:
                field.state = (field.state + 1) % 4;
                break;

            default:
                break;
        }
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
            case GLFW.GLFW_KEY_LEFT:
                field.xOrigin--;
                break;
            case GLFW.GLFW_KEY_RIGHT:
                field.xOrigin++;
                break;
            default:
                break;
        }
    }
}
