package game.object.tetromino;

public class S extends Tetromino {
    // damn u again java, u piece of shiet

    // shifted by 7 value
    int t = 4;

    public S() {
        super.rotation = this.rotation;
        super.gridSize = this.gridSize;
    }

    public int gridSize = 3;

    public int[][][] rotation = {
            {
                    { f, t, t },
                    { t, t, f },
                    { f, f, f }
            },
            {
                    { f, t, f },
                    { f, t, t },
                    { f, f, t }
            },
            {
                    { f, f, f },
                    { f, t, t },
                    { t, t, f }
            },
            {
                    { t, f, f },
                    { t, t, f },
                    { f, t, f }
            }
    };
}
