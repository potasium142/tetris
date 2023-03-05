package game.object.tetromino;

public class O extends Tetromino {
    // damn u again java, u piece of shiet

    // shifted by 7 value
    int t = 3;

    public O() {
        super.rotation = this.rotation;
        super.gridSize = this.gridSize;
    }

    public final int gridSize = 3;

    public final int[][][] rotation = {
            {
                    { f, t, t },
                    { f, t, t },
                    { f, f, f }
            },
            {
                    { f, f, f },
                    { f, t, t },
                    { f, t, t }
            },
            {
                    { f, f, f },
                    { t, t, f },
                    { t, t, f }
            },
            {
                    { t, t, f },
                    { t, t, f },
                    { f, f, f }
            }
    };
}
