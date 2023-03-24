package game.object.UI;

import engine.Object;
import engine.ObjectLoader;
import engine.Shader;
import game.GV;
import game.UI.UIRender;

public class UI {

    Shader shader;

    public UIRender renderer;

    Object UI;

    public UI(ObjectLoader objectLoader) throws Exception {
        shader = new Shader("./src/main/java/game/object/UI/uiVert.vert",
                "./src/main/java/game/object/UI/uiFrag.frag") {

            @Override
            public void bindAttribute() {
                // TODO Auto-generated method stub
            }

            @Override
            public void initUnifrom() {
                // TODO Auto-generated method stub
            }

        };
        UI = objectLoader.loadMeshIndices(vertices, indices, textureCoordinates);
        UI.textureID = objectLoader.loadTexture(GV.queuePath)[0];
        renderer = new UIRender(shader, UI);
    }

    protected final float xOffset = GV.xCoord * 16;
    protected final float yOffset = GV.yCoord * 22;

    protected float[] textureCoordinates = {
            .93f, 0,
            .93f, .15f,
            0, .15f,
            0, 0,

            .93f, .157f,
            .93f, .1835f,
            0f, .1835f,
            0f, .157f,

            .93f, .1832f,
            .93f, .2773f,
            0, .2773f,
            0, .1832f,

            .93f, .289f,
            .93f, .15f + .289f,
            0, .15f + .289f,
            0, .289f,

            .93f, .157f + .289f,
            .93f, .1835f + .289f,
            0f, .1835f + .289f,
            0f, .157f + .289f,

            .93f, .1832f + .289f,
            .93f, .2773f + .289f,
            0, .2773f + .289f,
            0, .1832f + .289f,

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
            GV.xCoord * 6, GV.xCoord * 12 * .16f, GV.xCoord * 12 * .1f,
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
            queueVertex[0] + xOffset, -queueVertex[1] + yOffset - GV.yCoord * 32, // bottom left
            -queueVertex[0] + xOffset, -queueVertex[1] + yOffset - GV.yCoord * 32, // bottom right
            -queueVertex[0] + xOffset, -queueVertex[1] + yOffset, // top left

            // Next border
            queueVertex[0] + xOffset, -queueVertex[1] + yOffset - GV.yCoord * 32, // top right
            queueVertex[0] + xOffset, -queueVertex[2] * 4 + yOffset - GV.yCoord * 32, // bottom left
            -queueVertex[0] + xOffset, -queueVertex[2] * 4 + yOffset - GV.yCoord * 32, // bottom right
            -queueVertex[0] + xOffset, -queueVertex[1] + yOffset - GV.yCoord * 32, // top left

            // Hold
            // Next slot
            queueVertex[0] - xOffset, queueVertex[1] + yOffset, // top right
            queueVertex[0] - xOffset, -queueVertex[1] + yOffset, // bottom left
            -queueVertex[0] - xOffset, -queueVertex[1] + yOffset, // bottom right
            -queueVertex[0] - xOffset, queueVertex[1] + yOffset, // top left

            // Next border
            queueVertex[0] - xOffset, -queueVertex[1] + yOffset, // top right
            queueVertex[0] - xOffset, -queueVertex[1] + yOffset - GV.yCoord * 7, // bottom left
            -queueVertex[0] - xOffset, -queueVertex[1] + yOffset - GV.yCoord * 7, // bottom right
            -queueVertex[0] - xOffset, -queueVertex[1] + yOffset, // top left

            // Next border
            queueVertex[0] - xOffset, -queueVertex[1] + yOffset - GV.yCoord * 7, // top right
            queueVertex[0] - xOffset, -queueVertex[2] * 3 + yOffset - GV.yCoord * 7, // bottom left
            -queueVertex[0] - xOffset, -queueVertex[2] * 3 + yOffset - GV.yCoord * 7, // bottom right
            -queueVertex[0] - xOffset, -queueVertex[1] + yOffset - GV.yCoord * 7, // top left
    };
}
