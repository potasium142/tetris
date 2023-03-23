package game.object.tetromino;

public class X extends Tetromino {
    int t = 2;

    public X() {
        super.rotation = this.rotation;
        super.gridSize = this.gridSize;
        super.t = t;

    }

    public final int gridSize = 3;

    public final int[][][] rotation = {
            {
                    { t, f, t },
                    { f, t, f },
                    { t, f, t },

            },
            {
                    { t, f, t },
                    { f, t, f },
                    { t, f, t },

            },
            {
                    { t, f, t },
                    { f, t, f },
                    { t, f, t },

            },
            {
                    { t, f, t },
                    { f, t, f },
                    { t, f, t },

            }
    };
}
