package game;

import org.lwjgl.opengl.GL40;

import engine.ObjectLoader;
import engine.Renderer;
import engine.Window;
import game.object.background.Background;
import game.object.grid.Grid;

public class MainGame {
    public static void main(String[] args) throws Exception {
        Window window = new Window(GV.width, GV.height, GV.title);
        window.init();

        Renderer renderer = new Renderer();

        ObjectLoader loader = new ObjectLoader();

        Background background = new Background(loader, renderer);
        Grid grid = new Grid(loader, renderer);

        while (!window.shouldClose()) {
            GL40.glClear(GL40.GL_COLOR_BUFFER_BIT | GL40.GL_DEPTH_BUFFER_BIT);

            background.render();
            grid.render();
            window.update();
        }
        {
            loader.cleanup();
            window.cleanup();
        }
    }
}
