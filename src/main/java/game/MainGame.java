package game;

import org.lwjgl.opengl.GL40;

import engine.ObjectLoader;
import engine.Renderer;
import engine.Window;
import game.logic.grid.BackgroundGrid;
import game.logic.grid.DynamicGrid;
import game.logic.grid.StaticGrid;
import game.object.UI.Border;
import game.object.background.Background;
import game.object.background.TopOutScreen;

//NOTE:
//DO NOT ATTEMPT TO RUN THIS IN DEBUG MODE IN JDK19
//for some unknown reason, debug mode will make it crash

public class MainGame {

    public static void main(String[] args) throws Exception {
        Window window = new Window(GV.width, GV.height, GV.title);
        window.init();

        Renderer renderer = new Renderer();

        ObjectLoader loader = new ObjectLoader();

        Background background = new Background(loader, renderer);
        TopOutScreen tos = new TopOutScreen(loader, renderer);

        BackgroundGrid backgroundGrid = new BackgroundGrid(loader, renderer);
        StaticGrid staticGrid = new StaticGrid(loader, renderer);
        DynamicGrid dynamicGrid = new DynamicGrid(loader, renderer, staticGrid, window);
        Border border = new Border(loader);

        try {
            while (!window.shouldClose()) {
                GL40.glClear(GL40.GL_COLOR_BUFFER_BIT | GL40.GL_DEPTH_BUFFER_BIT);
                if (dynamicGrid.logicRunning) {
                    dynamicGrid.startLogic();
                    staticGrid.startLogic();
                }

                background.render();
                backgroundGrid.render();
                staticGrid.render();
                dynamicGrid.render();
                border.renderer.render();
                if (!dynamicGrid.logicRunning) {
                    tos.render();
                }

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
