package engine;

import java.util.HashSet;
import java.util.Set;

import org.lwjgl.glfw.GLFW;

public class Keyboard implements Runnable {
    private Window window;
    private Thread thread = new Thread(this, "Keyboard Thread");

    protected int[] keyCheck = {
            GLFW.GLFW_KEY_UP,
            GLFW.GLFW_KEY_DOWN,
            GLFW.GLFW_KEY_LEFT,
            GLFW.GLFW_KEY_RIGHT,
    };

    private Set<Integer> keyPress = new HashSet<Integer>();

    public Keyboard(Window window) {
        this.window = window;
    }

    public boolean keyPressed(int keyCode) {
        return keyPress.contains(keyCode);
    }

    @Override
    public void run() {
        try {
            while (true) {
                for (Integer key : keyCheck) {
                    if (GLFW.glfwGetKey(window.window, key) == GLFW.GLFW_PRESS) {
                        keyPress.add(key);
                    } else
                        keyPress.remove(key);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void start() {
        thread.start();
    }

    public int keyPressedID(int keyCode) {
        return keyPress.contains(keyCode) ? keyCode : 0;
    }

    public void cleanup() {
        thread.stop();
    }
}
