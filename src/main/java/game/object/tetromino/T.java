package game.object.tetromino;

public class T {
    // damn u again java, u piece of shiet
    static final int t = -1;
    static final int f = 0;

    // shifted by 7 value

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
