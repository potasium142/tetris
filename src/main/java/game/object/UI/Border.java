package game.object.UI;

import engine.Object;
import engine.ObjectLoader;
import game.GV;
import game.UI.UIRender;
import game.UI.UIShader;

public class Border {

    UIShader shader;

    public UIRender renderer;

    Object UI;

    public Border(ObjectLoader objectLoader) throws Exception {
        shader = new UIShader("./src/main/java/game/object/UI/uiVert.vert",
                "./src/main/java/game/object/UI/uiFrag.frag");
        UI = objectLoader.loadMesh3D(vertices, indices, textureCoordinates);
        renderer = new UIRender(shader, UI);
    }

    protected final float h = (GV.height * 2);
    protected final float w = (GV.width * 2);
    protected final float r = 1 / (w * 20);

    protected float titleScale = .8f;

    protected final float xCoord = (h * r) * titleScale;
    protected final float yCoord = (w * r) * titleScale;

    protected final float xOffset = -xCoord * 10;
    protected final float yOffset = -yCoord * 20;

    protected float[] textureCoordinates = {
            0, 0,
            0, 1,
            1, 0,
            1, 1
    };

    protected final int[] indices = {
            0, 1, 2, 3, 2, 1, // bottom bar
            4, 5, 6, 7, 4, 6,
            8, 9, 10, 8, 11, 9,
            12, 13, 14, 12, 15, 13
    };

    protected final float[] vertices = {
            -xOffset - xCoord, yOffset - yCoord * 2.5f, 0, // 0
            -xOffset - xCoord, yOffset - yCoord * 1.5f, 0, // 1
            xOffset + xCoord, yOffset - yCoord * 2.5f, 0, // 2
            xOffset + xCoord, yOffset - yCoord * 1.5f, 0, // 3

            // 6-5
            // \|
            // 4

            -xOffset + yCoord * 1f, yOffset - yCoord * .5f, 0, // 4
            -xOffset + yCoord * 1f, -yOffset + yCoord * 2, 0, // 5
            -xOffset + yCoord * .5f, -yOffset + yCoord * 2, 0, // 6
            -xOffset + yCoord * .5f, yOffset - yCoord * .5f, 0, // 7

            // -xOffset - yCoord, -yOffset, 0,

            // 10 9
            // |
            // 8

            xOffset - yCoord * 1f, yOffset - yCoord * .5f, 0, // 8
            xOffset - yCoord * .5f, -yOffset + yCoord * 2, 0, // 9
            xOffset - yCoord * 1f, -yOffset + yCoord * 2, 0, // 10
            xOffset - yCoord * .5f, yOffset - yCoord * .5f, 0, // 11

            xOffset - yCoord * 7f, -yOffset - yCoord * 2f, 0, // 12
            xOffset - yCoord * 2f, -yOffset - yCoord * 1, 0, // 13
            xOffset - yCoord * 7f, -yOffset - yCoord * 1, 0, // 14
            xOffset - yCoord * 2f, -yOffset - yCoord * 2f, 0,// 15

    };
}
