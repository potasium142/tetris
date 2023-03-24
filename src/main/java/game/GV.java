package game;

//main game logic 1 thread
//ui render 1 thread
//main game render 1 thread

public class GV {
    // damn u java

    public static final String title = "Tetris";
    public static int width = 1600;
    public static int height = 900;

    public static final String mainPath = "./src/main/";
    public static final String backgroundPath = mainPath + "asset/BIGG_YOSHI.jpg";
    public static final String tetromino = mainPath + "asset/SegaTetrisMD.png";
    public static final String queuePath = mainPath + "asset/_queue.png";
    public static final String fontPath = mainPath + "asset/Iosevka.";

    public static final float DAS = 10f;
    public static final float ARR = 2f;
    public static final float DCD = 1f;
    public static final float SDF = 5f;
    public static final float ms = .01667f;

    public static final float gravity = ms * 60f;
    public static final int softdropMultiplyer = 40;

    public static final float h = (GV.height * 2);
    public static final float w = (GV.width * 2);
    public static final float r = 1 / (w * 20);

    public static final float ratio = w / h;

    public static float titleScale = .7f;

    public static final float xCoord = (h * r) * titleScale;
    public static final float yCoord = (w * r) * titleScale;

    public static final float xOffset = -xCoord * 9;
    public static final float yOffset = -yCoord * 20;

}
