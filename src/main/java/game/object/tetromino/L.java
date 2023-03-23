package game.object.tetromino;

public class L extends Tetromino {
    int t = 2;

    public L() {
        super.rotation = this.rotation;
        super.gridSize = this.gridSize;
        super.t = t;

    }

    int gridSize = 3;

    int[][][] rotation = {
            {
                    { t, f, f },
                    { t, t, t },
                    { f, f, f }
            },
            {
                    { f, t, t },
                    { f, t, f },
                    { f, t, f }
            },
            {
                    { f, f, f },
                    { t, t, t },
                    { f, f, t }
            },
            {
                    { f, t, f },
                    { f, t, f },
                    { t, t, f }
            }
    };
}
