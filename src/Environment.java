import static java.lang.System.currentTimeMillis;

public class Environment {
    static final int backgroundWidth = 640;    // 640 is the width of the background
    static final int backgroundHeight = 480;   // 480 is the height of the background
    static final int barWidth = 1045;          // 1045 is the width of the bar
    static final int barHeight = 66;           // 66 is the height of the bar
    static final int playerBackWidth = 23;     // 23 is the width of the player_back
    static final int playerBackHeight = 37;    // 37 is the height of the player_back
    static final int canvasWidth = 640;        // 640 is the width of the background (canvas being the background)
    static final int canvasHeight = backgroundHeight+barHeight;    // 66 is the height of the bar , 480 is the height of the background
    static final float scaleX1 = 0;           // 0 is the left of the background
    static final float scaleX2 = 640;         // It would be meaningless to have a width of barWidth(1045) (since the nearly half of canvas would be empty)
    static final float scaleY1 = 0;           // 0 is the bottom of the background
    static final float scaleY2 = backgroundHeight+barHeight;    // 66 is the height of the bar , 480 is the height of the background
    static final int TOTAL_GAME_DURATION = 40000;
    static final int PAUSE_DURATION = 15;
    Environment () {
        DisplayBackground();
        DisplayPlayer();
    }
    public void initializeCanvas() {
        StdDraw.enableDoubleBuffering();
        StdDraw.setCanvasSize(canvasWidth, canvasHeight);
        StdDraw.setXscale(scaleX1, scaleX2);
        StdDraw.setYscale(scaleY1, scaleY2);
    }
    public void DisplayBackground() {
        StdDraw.picture(backgroundWidth/2.0, backgroundHeight/2.0+ barHeight , "images/background.png");
    }
    public void DisplayPlayer() {
        StdDraw.picture(backgroundWidth/2.0, playerBackHeight/2.0 + barHeight , "images/player_back.png");
    }
}
