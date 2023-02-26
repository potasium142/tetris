package game;

//main game logic 1 thread
//ui render 1 thread
//main game render 1 thread
//input 1 thread

public class GV {
    // damn u java
    public static final boolean t = true;
    static final boolean f = true;

    public static final String title = "Tetirs";
    public static int width = 1600;
    public static int height = 900;

    public static final String mainPath = "./src/main/";
    public static final String backgroundPath = mainPath + "asset/1444119-4k-games-wallpaper-3840x216.jpg";
    public static final String tetromino = mainPath + "asset/SNES-SuperMarioWorld.png";
}
