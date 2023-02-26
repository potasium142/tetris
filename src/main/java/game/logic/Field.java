package game.logic;

import org.lwjgl.glfw.GLFW;

import engine.Keyboard;
import game.object.grid.GridRender;
import game.object.tetromino.T;

public class Field implements Runnable {
    GridRender grid;
    Keyboard keyboard;
    T t = new T();
    int[][] ogGrid = new int[11][25];
    int xOrigin = 4;
    int yOrigin = 0;

    Thread thread = new Thread(this, "Field");

    public Field(GridRender grid, Keyboard keyboard) {
        this.grid = grid;
        this.keyboard = keyboard;
    }

    public void update() {
        keyUpdate();
        ogGrid = grid.gridState.clone();
        for (int i = 0; i < t.gridSize; i++) {
            grid.gridState[i + 1 + xOrigin][3 + yOrigin] = t.rotation[0][i];
            grid.gridState[i + 1 + xOrigin][2 + yOrigin] = t.rotation[0][3 + i];
            grid.gridState[i + 1 + xOrigin][1 + yOrigin] = t.rotation[0][6 + i];
        }
        grid.render();
        grid.gridState = ogGrid.clone();
    }

    private void keyUpdate() {
        if (keyboard.keyPressed(GLFW.GLFW_KEY_RIGHT))
            xOrigin++;
        if (keyboard.keyPressed(GLFW.GLFW_KEY_LEFT))
            xOrigin--;
    }

    @Override
    public void run() {
        keyUpdate();
        ogGrid = grid.gridState.clone();
        for (int i = 0; i < t.gridSize; i++) {
            grid.gridState[i + 1 + xOrigin][3 + yOrigin] = t.rotation[0][i];
            grid.gridState[i + 1 + xOrigin][2 + yOrigin] = t.rotation[0][3 + i];
            grid.gridState[i + 1 + xOrigin][1 + yOrigin] = t.rotation[0][6 + i];
        }
        grid.render();
        grid.gridState = ogGrid.clone();
    }

}
