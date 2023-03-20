package game;

//main game logic 1 thread
//ui render 1 thread
//main game render 1 thread

public class GV {
    // damn u java

    public static final String title = "Tetris";
    public static int width = 800;
    public static int height = 800;

    public static final String mainPath = "./src/main/";
    public static final String backgroundPath = mainPath + "asset/2d1ea2d8af1f72412b0e.jpg";
    public static final String tetromino = mainPath + "asset/SNES-SuperMarioWorld.png";

    public static final float DAS = 10f;
    public static final float ARR = 2f;
    public static final float DCD = 1f;
    public static final float SDF = 5f;
    public static final float ms = .01667f;

    public static final float gravity = ms * 60f;
    public static final int softdropMultiplyer = 40;

}
