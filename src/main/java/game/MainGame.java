package game;

import org.lwjgl.opengl.GL40;

import engine.Keyboard;
import engine.ObjectLoader;
import engine.Renderer;
import engine.Window;
import game.logic.Field;
import game.object.background.Background;
import game.object.grid.GridRender;

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
        GridRender grid = new GridRender(loader, renderer);

        Keyboard keyboard = new Keyboard(window);
        Field field = new Field(grid, keyboard, window);
        keyboard.field = field;

        keyboard.start();

        try {
            while (!window.shouldClose()) {
                GL40.glClear(GL40.GL_COLOR_BUFFER_BIT | GL40.GL_DEPTH_BUFFER_BIT);
                background.render();
                field.run();
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
