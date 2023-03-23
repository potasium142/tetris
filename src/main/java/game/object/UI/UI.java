package game.object.UI;

import engine.Object;
import engine.ObjectLoader;
import game.GV;
import game.UI.UIRender;
import game.UI.UIShader;

public class UI {

    UIShader shader;

    public UIRender renderer;

    Object UI;

    public UI(ObjectLoader objectLoader) throws Exception {
        shader = new UIShader("./src/main/java/game/object/UI/uiVert.vert",
                "./src/main/java/game/object/UI/uiFrag.frag");
        UI = objectLoader.loadMesh3D(vertices, indices, textureCoordinates);
        UI.textureID = objectLoader.loadTexture(GV.queuePath)[0];
        renderer = new UIRender(shader, UI);
    }

    protected final float h = (GV.height * 2);
    protected final float w = (GV.width * 2);
    protected final float r = 1 / (w * 20);

    protected float titleScale = .7f;

    protected final float xCoord = (h * r) * titleScale;
    protected final float yCoord = (w * r) * titleScale;

    protected final float xOffset = xCoord * 16;
    protected final float yOffset = yCoord * 22;

    protected float[] textureCoordinates = {
            .93f, 0,
            .93f, .15f,
            0, .15f,
            0, 0,

            .93f, .157f,
            .93f, .1835f,
            0f, .1835f,
            0f, .157f,

            .93f, .1855f,
            .93f, .2773f,
            0, .2773f,
            0, .1855f,

            .93f, .289f,
            .93f, .15f + .289f,
            0, .15f + .289f,
            0, .289f,

            .93f, .157f + .289f,
            .93f, .1835f + .289f,
            0f, .1835f + .289f,
            0f, .157f + .289f,

            .93f, .1855f + .289f,
            .93f, .2773f + .289f,
            0, .2773f + .289f,
            0, .1855f + .289f,

    };

    protected final int[] indices = {
            0, 1, 3, // first Triangle
            1, 2, 3, // second Triangle
            4, 5, 7, // first Triangle
            5, 6, 7, // second Triangle
            8, 9, 11,
            9, 10, 11,

            12, 13, 15, // first Triangle
            13, 14, 15, // second Triangle
            16, 17, 19, // first Triangle
            17, 18, 19, // second Triangle
            20, 21, 23,
            21, 22, 23
    };

    protected final float[] queueVertex = {
            xCoord * 6, xCoord * 12 * .16f, xCoord * 12 * .1f,
    };

    protected final float[] vertices = {
            // Queue
            // Next slot
            queueVertex[0] + xOffset, queueVertex[1] + yOffset, // top right
            queueVertex[0] + xOffset, -queueVertex[1] + yOffset, // bottom left
            -queueVertex[0] + xOffset, -queueVertex[1] + yOffset, // bottom right
            -queueVertex[0] + xOffset, queueVertex[1] + yOffset, // top left

            // Next border
            queueVertex[0] + xOffset, -queueVertex[1] + yOffset, // top right
            queueVertex[0] + xOffset, -queueVertex[1] + yOffset - xCoord * 55, // bottom left
            -queueVertex[0] + xOffset, -queueVertex[1] + yOffset - xCoord * 55, // bottom right
            -queueVertex[0] + xOffset, -queueVertex[1] + yOffset, // top left

            // Next border
            queueVertex[0] + xOffset, -queueVertex[1] + yOffset - xCoord * 55, // top right
            queueVertex[0] + xOffset, -queueVertex[2] * 4 + yOffset - xCoord * 55, // bottom left
            -queueVertex[0] + xOffset, -queueVertex[2] * 4 + yOffset - xCoord * 55, // bottom right
            -queueVertex[0] + xOffset, -queueVertex[1] + yOffset - xCoord * 55, // top left

            // Hold
            // Next slot
            queueVertex[0] - xOffset, queueVertex[1] + yOffset, // top right
            queueVertex[0] - xOffset, -queueVertex[1] + yOffset, // bottom left
            -queueVertex[0] - xOffset, -queueVertex[1] + yOffset, // bottom right
            -queueVertex[0] - xOffset, queueVertex[1] + yOffset, // top left

            // Next border
            queueVertex[0] - xOffset, -queueVertex[1] + yOffset, // top right
            queueVertex[0] - xOffset, -queueVertex[1] + yOffset - xCoord * 10, // bottom left
            -queueVertex[0] - xOffset, -queueVertex[1] + yOffset - xCoord * 10, // bottom right
            -queueVertex[0] - xOffset, -queueVertex[1] + yOffset, // top left

            // Next border
            queueVertex[0] - xOffset, -queueVertex[1] + yOffset - xCoord * 10, // top right
            queueVertex[0] - xOffset, -queueVertex[2] * 3 + yOffset - xCoord * 10, // bottom left
            -queueVertex[0] - xOffset, -queueVertex[2] * 3 + yOffset - xCoord * 10, // bottom right
            -queueVertex[0] - xOffset, -queueVertex[1] + yOffset - xCoord * 10, // top left
    };
}
