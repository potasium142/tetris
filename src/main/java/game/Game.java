package game;

import engine.Window;

public class Game {
    private static Window window = new Window(1600, 900, "Tetris");

    public static void main(String[] args) {
        window.init();

        float vertices[] = {
                -0.5f, -0.5f, 0.0f,
                0.5f, -0.5f, 0.0f,
                0.0f, 0.5f, 0.0f
        };

        while (!window.shouldClose()) {
            window.update();
        }

        window.cleanup();
    }
}
