package game.object.tetromino;

public class I extends Tetromino {
    // damn u again java, u piece of shiet

    // shifted by 7 value
    int t = 5;

    public I() {
        super.rotation = this.rotation;
        super.gridSize = this.gridSize;
        super.offsetData = this.offsetData;
        super.offsetData_180 = WALLKICK_I_180;
    }

    private final int[][][] offsetData = {
            { { 0, 0 }, { -1, 0 }, { 2, 0 }, { -1, 0 }, { 2, 0 } },
            { { -1, 0 }, { 0, 0 }, { 0, 0 }, { 0, 1 }, { 0, -2 } },
            { { -1, 1 }, { 1, 1 }, { -2, 1 }, { +1, 0 }, { -2, 0 } },
            { { 0, 1 }, { 0, 1 }, { 0, 1 }, { 0, -1 }, { 0, +2 } }, };

    private final int WALLKICK_I_180[][][] = {
            { { -1, 0 }, { -2, 0 }, { 1, 0 }, { 2, 0 }, { 0, 1 } }, // 0>>2─┐
            { { 0, 1 }, { 0, 2 }, { 0, -1 }, { 0, -2 }, { -1, 0 } }, // 1>>3─┼┐
            { { 1, 0 }, { 2, 0 }, { -1, 0 }, { -2, 0 }, { 0, -1 } }, // 2>>0─┘│
            { { 0, 1 }, { 0, 2 }, { 0, -1 }, { 0, -2 }, { 1, 0 } }, // 3>>1──┘
    };

    public final int gridSize = 4;

    public final int[][][] rotation = {
            {
                    { f, f, f, f },
                    { t, t, t, t },
                    { f, f, f, f },
                    { f, f, f, f },

            },
            {
                    { f, f, t, f },
                    { f, f, t, f },
                    { f, f, t, f },
                    { f, f, t, f },

            },
            {
                    { f, f, f, f },
                    { f, f, f, f },
                    { t, t, t, t },
                    { f, f, f, f },

            }, {
                    { f, t, f, f },
                    { f, t, f, f },
                    { f, t, f, f },
                    { f, t, f, f },

            }
            // {
            // { f, f, f, f, f },
            // { f, f, f, f, f },
            // { f, t, t, t, t },
            // { f, f, f, f, f },
            // { f, f, f, f, f },

            // },
            // {
            // { f, f, f, f, f },
            // { f, f, t, f, f },
            // { f, f, t, f, f },
            // { f, f, t, f, f },
            // { f, f, t, f, f },

            // },
            // {
            // { f, f, f, f, f },
            // { f, f, f, f, f },
            // { t, t, t, t, f },
            // { f, f, f, f, f },
            // { f, f, f, f, f },

            // },
            // {
            // { f, f, t, f, f },
            // { f, f, t, f, f },
            // { f, f, t, f, f },
            // { f, f, t, f, f },
            // { f, f, f, f, f },

            // }
    };
}
