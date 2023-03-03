package game;

import org.lwjgl.opengl.GL40;

import engine.Keyboard;
import engine.ObjectLoader;
import engine.Renderer;
import engine.Window;
import game.logic.grid.DynamicGrid;
import game.logic.grid.StaticGrid;
import game.object.background.Background;

//NOTE:
//DO NOT ATTEMPT TO RUN THIS IN DEBUG MODE
//for some unknown reason, debug mode will make it crash

public class MainGame {
    public static void main(String[] args) throws Exception {
        Window window = new Window(GV.width, GV.height, GV.title);
        window.init();

        Renderer renderer = new Renderer();

        ObjectLoader loader = new ObjectLoader();

        Background background = new Background(loader, renderer);

        DynamicGrid dynamicGrid = new DynamicGrid(loader, renderer);
        StaticGrid staticGrid = new StaticGrid(loader, renderer);

        Keyboard keyboard = new Keyboard(window, dynamicGrid);

        keyboard.start();
        dynamicGrid.startLogic();

        try {
            while (!window.shouldClose()) {
                GL40.glClear(GL40.GL_COLOR_BUFFER_BIT | GL40.GL_DEPTH_BUFFER_BIT);
                background.render();
                staticGrid.render();
                dynamicGrid.render();
                window.update();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        {
            loader.cleanup();
            window.cleanup();
            System.exit(0);
        }
    }
}
