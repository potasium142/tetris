package game.logic;

import engine.Keyboard;
import engine.Window;
import game.object.grid.GridRender;
import game.object.tetromino.T;

public class Field implements Runnable {
    GridRender grid;
    Keyboard keyboard;
    Window window;
    T t = new T();
    int[][] ogGrid = new int[11][25];
    public int xOrigin = 4;
    public int yOrigin = 0;
    public int state = 0;

    Thread thread = new Thread(this, "Field");

    public Field(GridRender grid, Keyboard keyboard, Window window) {
        this.grid = grid;
        this.keyboard = keyboard;
        this.window = window;
    }

    @Override
    public void run() {

        ogGrid = grid.gridState.clone();
        for (int i = 0; i < t.gridSize; i++) {
            grid.gridState[i + 1 + xOrigin][3 + yOrigin] = t.rotation[state][i];
            grid.gridState[i + 1 + xOrigin][2 + yOrigin] = t.rotation[state][3 + i];
            grid.gridState[i + 1 + xOrigin][1 + yOrigin] = t.rotation[state][6 + i];
        }
        grid.render();
        grid.gridState = ogGrid;
    }

}
