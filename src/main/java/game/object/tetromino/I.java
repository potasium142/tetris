package game.object.tetromino;

public class I extends Tetromino {
    // damn u again java, u piece of shiet

    // shifted by 7 value
    int t = 3;

    public I() {
        super.rotation = this.rotation;
        super.gridSize = this.gridSize;
    }

    public final int gridSize = 5;

    public final int[][][] rotation = {
            {
                    { f, f, f, f, f },
                    { f, f, f, f, f },
                    { f, t, t, t, t },
                    { f, f, f, f, f },
                    { f, f, f, f, f },

            },
            {
                    { f, f, f, f, f },
                    { f, f, t, f, f },
                    { f, f, t, f, f },
                    { f, f, t, f, f },
                    { f, f, t, f, f },

            },
            {
                    { f, f, f, f, f },
                    { f, f, f, f, f },
                    { t, t, t, t, f },
                    { f, f, f, f, f },
                    { f, f, f, f, f },

            },
            {
                    { f, f, t, f, f },
                    { f, f, t, f, f },
                    { f, f, t, f, f },
                    { f, f, t, f, f },
                    { f, f, f, f, f },

            }
    };
}