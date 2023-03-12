package game.object.background;

import engine.Object;
import engine.ObjectLoader;
import engine.Renderer;
import engine.Shader;
import game.GV;

public class TopOutScreen {
    private Renderer renderer;
    private Shader shader = new BackgroundShader("./src/main/java/game/object/background/vert.vert",
            "./src/main/java/game/object/background/topoutFrag.frag");

    public TopOutScreen(ObjectLoader objLoader, Renderer renderer) throws Exception {
        this.renderer = renderer;
        int[] textureAttribute = objLoader.loadTexture(GV.mainPath + "asset/reset.png");

        float w = (GV.width * 2) / (float) textureAttribute[1];
        float h = (GV.height * 2) / (float) textureAttribute[2];

        float finalRatio = w > h ? w : h;

        w /= finalRatio;
        h /= finalRatio;

        vertices = new float[] {
                -h, w,
                -h, -w,
                h, w,
                h, -w,
        };

        this.background = objLoader.loadMesh2D(vertices, textureCoordinates);
        background.textureID = textureAttribute[0];
    }

    private float[] vertices;

    private final float[] textureCoordinates = {
            0, 0,
            0, 1,
            1, 0,
            1, 1
    };

    public void render() {
        renderer.render(shader, background);
    }

    public Object background;

}
