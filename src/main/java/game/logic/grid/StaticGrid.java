package game.logic.grid;

import org.joml.Matrix4f;
import org.lwjgl.opengl.GL40;

import engine.ObjectLoader;
import engine.Renderer;
import engine.font.Font;
import game.GV;
import game.object.grid.GridRender;

public class StaticGrid extends GridRender implements Runnable {
    Thread thread = new Thread(this);
    public DynamicGrid dynamicGrid;

    Font font;

    public StaticGrid(ObjectLoader objectLoader, Renderer renderer, Font font) throws Exception {
        super(objectLoader, renderer);
        this.font = font;
    }

    int gridLogic[][] = new int[40][10];

    public void reset() {
        gridLogic = new int[40][10];
        score = 0;
        B2B = false;
        TSpin = false;
    }

    private int score = 0;
    private int[] scoreTable = {
            100, // 0 single,mini tspin
            200, // 1 mini tspin single
            300, // 2 double
            400, // 3 tspin, mini tspin double
            500, // 4 triple
            600, // 5 b2b mini t spin double
            800, // 6 tspin single, tetris
            1200, // 7 B2B T-Spin Single/B2B Tetris/T-Spin Double
            1600, // 8 T-Spin Triple
            1800, // 9 B2B T-Spin Double
            2400, /// 10 B2B T-Spin Triple
            1, // 11 soft drop
            2, // 12 hard drop

    };

    float visibility;
    String clearText = "";
    float visibilityText;

    @Override
    protected void renderGrid() {
        for (int y = 0; y < 24; y++) {
            for (int x = 0; x < 10; x++) {
                Matrix4f matrix4f = createUITransformationMatrix(GV.xOffset + x * GV.xCoord * 2,
                        GV.yOffset + y * GV.yCoord * 2);

                shader.setVisibility(1f);
                if (gridLogic[y][x] == 0)
                    continue;
                shader.setVisibility(1 - visibility);
                shader.setTitleIndex(gridLogic[y][x] - 1);

                shader.setUnifromDataMatrix(matrix4f);
                GL40.glDrawArrays(GL40.GL_TRIANGLE_STRIP, 0, grid.vertexCount);
            }
        }
        font.renderStart();
        font.renderText(clearText, GV.xCoord * 12, GV.yCoord, visibilityText);
        font.renderText("Score", GV.xCoord * 12, -GV.yCoord * 16, 1);
        font.renderText(score + "", GV.xCoord * 12, -GV.yCoord * 19, 1);
        font.renderStop();
    }

    boolean TSpin;
    boolean miniTSpin;
    boolean B2B = false;

    public void softDrop() {
        score -= (dynamicGrid.yDynamicCoord - dynamicGrid.yStaticCoord) *
                scoreTable[11];
    }

    public void hardDrop() {
        score += (dynamicGrid.yStaticCoord - dynamicGrid.staticGhostHeight) *
                scoreTable[12];
    }

    @Override
    public void run() {
        int i = 0;
        int offset = 0;
        int clearAmount = 0;
        if (dynamicGrid.tetromino.t == 7) {
            TSpin = (checkTSpin());
        } else {
            TSpin = false;
        }
        do {

            if (rowAmount(gridLogic[i + offset]) == 10) {
                offset++;
                clearAmount++;
                while (rowAmount(gridLogic[i + offset]) == 10) {
                    offset++;
                    clearAmount++;
                }
            }
            gridLogic[i] = gridLogic[i + offset].clone();

        } while (i++ < 22);

        scoreCalculate(clearAmount);
        dynamicGrid.nextPiece();
        dynamicGrid.resetPiece();
    }

    public void scoreCalculate(int clearAmount) {
        if (TSpin) {
            visibilityText = 2;

            if (B2B && clearAmount != 0)
                clearAmount += 3;
            switch (clearAmount) {
                case 0:
                    clearText = ("Mini TSpin");
                    score += scoreTable[0];
                    B2B = true;
                    return;
                case 1:
                    clearText = ("TSpin Single");
                    score += scoreTable[6];
                    B2B = true;
                    return;
                case 4:
                    clearText = ("B2B TSpin Single");
                    score += scoreTable[7];
                    B2B = true;
                    return;
                case 2:
                    score += scoreTable[7];
                    B2B = true;
                    clearText = ("TSpin Double");
                    return;
                case 3:
                    score += scoreTable[8];
                    B2B = true;
                    clearText = ("TSpin Triple");
                    return;
                case 5:
                    score += scoreTable[9];
                    B2B = true;
                    clearText = ("B2B TSpin Double");
                    return;
                case 6:
                    score += scoreTable[10];
                    B2B = true;
                    clearText = ("B2B TSpin Triple");
                    return;

            }
            B2B = true;
            TSpin = false;

            return;
        }

        switch (clearAmount) {
            case 1:
                score += scoreTable[0];
                B2B = false;
                clearText = ("Single");
                visibilityText = 2;

                return;
            case 2:
                score += scoreTable[2];
                B2B = false;
                clearText = ("Double");
                visibilityText = 2;

                return;
            case 3:
                score += scoreTable[4];
                B2B = false;
                clearText = ("Triple");
                visibilityText = 2;

                return;
            case 4:
                if (B2B) {
                    clearText = ("B2B TETRIS");
                    score += scoreTable[7];
                    visibilityText = 2;
                    return;
                }
                clearText = ("TETRIS");
                visibilityText = 2;

                score += scoreTable[6];
                B2B = true;
                return;

        }
    }

    private int rowAmount(int[] row) {
        int amount = 0;
        for (int tile : row) {
            if (tile != 0)
                amount += 1;
        }
        return amount;
    }

    private boolean checkTSpin() {
        int amount = 0;
        try {
            if (gridLogic[dynamicGrid.yStaticCoord + 2][dynamicGrid.xStaticCoord] != 0)
                amount++;
            if (gridLogic[dynamicGrid.yStaticCoord + 2][dynamicGrid.xStaticCoord + 2] != 0)
                amount++;
            if (gridLogic[dynamicGrid.yStaticCoord][dynamicGrid.xStaticCoord + 2] != 0)
                amount++;
            if (gridLogic[dynamicGrid.yStaticCoord][dynamicGrid.xStaticCoord] != 0)
                amount++;
        } catch (Exception e) {
            amount++;
        }

        return amount >= 3;
    }

    public void startLogic() {
        thread.run();
    }

}
