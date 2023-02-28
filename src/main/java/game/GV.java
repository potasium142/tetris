package game;

//main game logic 1 thread
//ui render 1 thread
//main game render 1 thread

public class GV {
    // damn u java
    public static final boolean t = true;
    static final boolean f = true;

    public static final String title = "Tetirs";
    public static int width = 1600;
    public static int height = 900;

    public static final String mainPath = "./src/main/";
    public static final String backgroundPath = mainPath + "asset/gura.jpg";
    public static final String tetromino = mainPath + "asset/SNES-SuperMarioWorld.png";

    public static final float DAS = 15f;
    public static final float ARR = 5f;
    public static final float ms = .01667f;

}
