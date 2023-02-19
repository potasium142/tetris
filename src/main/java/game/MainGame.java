package game;

import engine.Shader;
import engine.Window;
import game.object.background.Background;
import engine.ObjectLoader;
import engine.Renderer;

public class MainGame {
    public static void main(String[] args) throws Exception {
        Window window = new Window(1600, 900, "Ohio Adventure");
        window.init();

        Renderer render = new Renderer();
        ObjectLoader loader = new ObjectLoader();

        Shader shader = new Shader("./src/main/java/game/shader/vert.vert", "./src/main/java/game/shader/frag.frag");

        Background background = new Background(loader);

        while (!window.shouldClose()) {
            shader.start();
            render.render(background.background);
            window.update();
        }
        {
            shader.cleanup();
            loader.cleanup();
            window.cleanup();
        }
    }
}
