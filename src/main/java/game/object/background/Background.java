package game.object.background;

import engine.Object;
import engine.ObjectLoader;

public class Background {
        private ObjectLoader loader;

        public Background(ObjectLoader objLoader) throws Exception {
                this.loader = objLoader;
                this.background = loader.loadMesh(vertices, indices, textureCoordinates);
                background.textureID = loader.loadTexture(backgroundPath);

        }

        private final float[] vertices = {
                        -1f, 1f, -1f, // v0
                        -1f, -1f, -1f, // v1
                        1f, -1f, -1f, // v2
                        1f, 1f, -1f,// v3
        };

        private final int[] indices = {
                        0, 1, 3, // top left triangle (v0, v1, v3)
                        3, 1, 2// bottom right triangle (v3, v1, v2)
        };

        private final float[] textureCoordinates = {
                        0, 0,
                        0, 1,
                        1, 1,
                        1, 0
        };

        private final String backgroundPath = "./src/main/asset/background.png";
        public Object background;

}
