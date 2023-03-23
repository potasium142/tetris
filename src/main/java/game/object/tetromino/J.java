package game.object.tetromino;

public class J extends Tetromino {
    int t = 6;

    public J() {
        super.rotation = this.rotation;
        super.gridSize = this.gridSize;
        super.t = t;

    }

    public final int gridSize = 3;

    public final int[][][] rotation = {
            {
                    { f, f, t },
                    { t, t, t },
                    { f, f, f }
            },
            {
                    { f, t, f },
                    { f, t, f },
                    { f, t, t }
            },
            {
                    { f, f, f },
                    { t, t, t },
                    { t, f, f }
            },
            {
                    { t, t, f },
                    { f, t, f },
                    { f, t, f }
            }
    };
}
