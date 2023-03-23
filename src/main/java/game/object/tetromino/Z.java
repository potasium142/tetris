package game.object.tetromino;

public class Z extends Tetromino {
    // damn u again java, u piece of shiet

    // shifted by 7 value
    int t = 1;

    public Z() {
        super.rotation = this.rotation;
        super.gridSize = this.gridSize;
        super.t = t;

    }

    public final int gridSize = 3;

    public final int[][][] rotation = {
            {
                    { t, t, f },
                    { f, t, t },
                    { f, f, f }
            },
            {
                    { f, f, t },
                    { f, t, t },
                    { f, t, f }
            },
            {
                    { f, f, f },
                    { t, t, f },
                    { f, t, t }
            },
            {
                    { f, t, f },
                    { t, t, f },
                    { t, f, f }
            }
    };
}
