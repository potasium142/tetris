package engine.font;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.FloatBuffer;

import org.joml.Matrix4f;
import org.lwjgl.opengl.GL40;

import engine.Object;
import engine.ObjectLoader;
import engine.Shader;
import game.GV;

//this code bad, dont use it

public class Font {
    private File glyphFile;

    private float scale = .5f;

    private Shader shader;

    private float[][] UV;
    private float[][] meshVertices;
    private float[] advanced;

    private Object[] character;

    private int textureID;

    ObjectLoader objectLoader;
    FloatBuffer buffer;
    int posLoc;
    float posX;
    float posY;
    int visLoc;

    public Font(ObjectLoader objectLoader) throws Exception {
        glyphFile = new File(GV.fontPath + "fnt");
        this.objectLoader = objectLoader;

        loadFile();
        textureID = objectLoader.loadTexture(GV.fontPath + "png")[0];
        shader = new Shader("./src/main/java/game/UI/Shader/fontVert.vert",
                "./src/main/java/game/UI/Shader/fontFrag.frag") {

            @Override
            public void bindAttribute() {
            }

            @Override
            public void initUnifrom() {
                posLoc = super.createUnifrom("pos");
                visLoc = super.createUnifrom("visibility");
            }

        };

    }

    public void loadFile() throws Exception {
        BufferedReader br = new BufferedReader(new FileReader(glyphFile));

        br.readLine();
        br.readLine();
        br.readLine();

        int charAmount = Integer.parseInt(br.readLine().split("[^\\-0-9]+")[1]);

        UV = new float[256][8];
        meshVertices = new float[256][8];
        advanced = new float[256];
        character = new Object[256];

        for (int i = 0; i < charAmount; i++) {
            String[] curLine = br.readLine().split("[^\\-0-9]+");

            int Id = Integer.parseInt(curLine[1]);

            float x = Float.parseFloat(curLine[2]) / 2048;
            float y = Float.parseFloat(curLine[3]) / 2048;
            float width = Float.parseFloat(curLine[4]) / 2048;
            float height = Float.parseFloat(curLine[5]) / 2048;
            float xOffset = Float.parseFloat(curLine[6]) / 2048;
            float yOffset = Float.parseFloat(curLine[7]) / 2048;
            float advancedF = Float.parseFloat(curLine[8]) / 2048;

            UV[Id][0] = x;
            UV[Id][1] = y;

            UV[Id][2] = x + width;
            UV[Id][3] = y;

            UV[Id][4] = x;
            UV[Id][5] = y + height;

            UV[Id][6] = x + width;
            UV[Id][7] = y + height;

            meshVertices[Id][0] = -xOffset * scale;
            meshVertices[Id][1] = (height - yOffset) * scale;

            meshVertices[Id][2] = (width + xOffset) * scale;
            meshVertices[Id][3] = (height - yOffset) * scale;

            meshVertices[Id][4] = -xOffset * scale;
            meshVertices[Id][5] = (-height - yOffset) * scale;

            meshVertices[Id][6] = (width + xOffset) * scale;
            meshVertices[Id][7] = (-height - yOffset) * scale;

            advanced[Id] = (advancedF) * scale;

            character[Id] = objectLoader.loadMesh2D(meshVertices[Id], UV[Id]);
            character[Id].textureID = this.textureID;
        }
        br.close();
    }

    String text = "amogus";

    public void renderStart() {

        shader.start();

        GL40.glActiveTexture(GL40.GL_TEXTURE);
        GL40.glBindTexture(GL40.GL_TEXTURE_2D, textureID);
        GL40.glEnable(GL40.GL_BLEND);
        GL40.glBlendFunc(GL40.GL_SRC_ALPHA, GL40.GL_ONE_MINUS_SRC_ALPHA);

    }

    public void renderText(String text, float posX, float posY, float vis) {
        float advanceds = 0;
        int i = text.length();

        while (i-- != 0) {
            GL40.glBindVertexArray(character[text.charAt(i)].meshID);
            GL40.glEnableVertexAttribArray(0);
            GL40.glEnableVertexAttribArray(1);
            Matrix4f matrix4f = createUITransformationMatrix(-advanceds - posX, posY);
            advanceds += advanced[text.charAt(i)];
            shader.unifromMatrix(posLoc, matrix4f);
            shader.unifromFloat(visLoc, vis);
            GL40.glDrawArrays(GL40.GL_TRIANGLE_STRIP, 0, 4);

        }

    }

    public void renderStop() {

        GL40.glDisableVertexAttribArray(0);
        GL40.glDisableVertexAttribArray(1);
        GL40.glBindVertexArray(0);

        shader.stop();
    }

    public static Matrix4f createUITransformationMatrix(float x, float y) {
        Matrix4f matrix = new Matrix4f();

        matrix.identity().translate(x, y, 0);

        return matrix;
    }
}
