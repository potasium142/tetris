package game.object.tetromino;

public class Amogus extends Tetromino {
    int t = 1;

    public Amogus() {
        super.rotation = this.rotation;
        super.gridSize = this.gridSize;
    }

    public final int gridSize = 4;

    public final int[][][] rotation = {
            {
                    { f, t, t, t },
                    { t, t, 5, 5 },
                    { t, t, t, t },
                    { f, t, f, t }

            },
            {
                    { f, t, t, f },
                    { t, t, t, t },
                    { f, t, 5, t },
                    { t, t, 5, t }

            },
            {
                    { t, f, t, f },
                    { t, t, t, t },
                    { 5, 5, t, t },
                    { t, t, t, f },

            },
            {
                    { t, t, 5, t },
                    { f, t, 5, t },
                    { t, t, t, t },
                    { f, t, t, f },

            }
    };
}
