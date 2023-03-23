package game.object.tetromino;

public class T extends Tetromino {
    // damn u again java, u piece of shiet

    // shifted by 7 value
    int t = 7;

    public T() {
        super.rotation = this.rotation;
        super.gridSize = this.gridSize;
        super.t = t;
    }

    public final int gridSize = 3;

    public final int[][][] rotation = {
            {
                    { f, t, f },
                    { t, t, t },
                    { f, f, f }
            },
            {
                    { f, t, f },
                    { f, t, t },
                    { f, t, f }
            },
            {
                    { f, f, f },
                    { t, t, t },
                    { f, t, f }
            },
            {
                    { f, t, f },
                    { t, t, f },
                    { f, t, f }
            }
    };
}
