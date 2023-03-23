package game.object.tetromino;

public class Tetromino {
    static int f = 0;
    public int t;
    public int gridSize;
    public int[][][] rotation;
    public float xOffsetRatio = -1.5f;
    public float yOffsetRatio = -2f;

    public final int[][][] rotationDefault = {
            {
                    { 1 },

            },
            {
                    { 1 },

            },
            {
                    { 1 },

            },
            {
                    { 0 },

            }
    };

    public Tetromino() {
        this.gridSize = 0;
        this.rotation = rotationDefault;
    }

    public int[][][] offsetData = {
            { { 0, 0 }, { 0, 0 }, { 0, 0 }, { 0, 0 }, { 0, 0 }, }, // 0
            { { 0, 0 }, { 1, 0 }, { 1, -1 }, { 0, 2 }, { 1, 2 }, }, // R
            { { 0, 0 }, { 0, 0 }, { 0, 0 }, { 0, 0 }, { 0, 0 }, }, // 2
            { { 0, 0 }, { -1, 0 }, { -1, -1 }, { 0, 2 }, { -1, 2 }, },// L

    };
    public int offsetData_180[][][] = {
            { { 1, 0 }, { 2, 0 }, { 1, 1 }, { 2, 1 }, { -1, 0 }, { -2, 0 }, { -1, 1 }, { -2, 1 }, { 0, -1 }, { 3, 0 },
                    { -3, 0 } }, // 0>>2─┐
            { { 0, 1 }, { 0, 2 }, { -1, 1 }, { -1, 2 }, { 0, -1 }, { 0, -2 }, { -1, -1 }, { -1, -2 }, { 1, 0 },
                    { 0, 3 }, { 0, -3 } }, // 1>>3─┼┐
            { { -1, 0 }, { -2, 0 }, { -1, -1 }, { -2, -1 }, { 1, 0 }, { 2, 0 }, { 1, -1 }, { 2, -1 }, { 0, 1 },
                    { -3, 0 }, { 3, 0 } }, // 2>>0─┘│
            { { 0, 1 }, { 0, 2 }, { 1, 1 }, { 1, 2 }, { 0, -1 }, { 0, -2 }, { 1, -1 }, { 1, -2 }, { -1, 0 }, { 0, 3 },
                    { 0, -3 } }, // 3>>1──┘
    };
}
