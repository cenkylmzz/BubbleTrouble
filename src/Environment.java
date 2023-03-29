import java.awt.event.KeyEvent;

import static java.lang.System.currentTimeMillis;

public class Environment {
    static final int backgroundWidth = 640;    // 640 is the width of the background
    static final int backgroundHeight = 480;   // 480 is the height of the background
    static final int barWidth = 1045;          // 1045 is the width of the bar
    static final int barHeight = 66;           // 66 is the height of the bar
    static final int playerBackWidth = 23;     // 23 is the width of the player_back
    static final int playerBackHeight = 37;    // 37 is the height of the player_back
    static final int arrowWidth = 8;          // 8 is the width of the arrow
    static final int arrowHeight = 480;        // 480 is the height of the arrow
    static final int canvasWidth = 800;            //Suggested constant
    static final int canvasHeight = 500;            //Suggested constant
    static final float scaleX1 = 0;           // 0 is the left of the background
    static final float scaleX2 = 640;         // It would be meaningless to have a width of barWidth(1045) (since the nearly half of canvas would be empty)
    static final float scaleY1 = 0;           // 0 is the bottom of the background
    static final float scaleY2 = backgroundHeight + barHeight;    // 66 is the height of the bar , 480 is the height of the background
    static final long TOTAL_GAME_DURATION = 40000;
    static final int PAUSE_DURATION = 15;

    Environment() {
        initialize();
        playGame();
    }

    public void initialize() {
        StdDraw.enableDoubleBuffering();
        StdDraw.setCanvasSize(Environment.canvasWidth, Environment.canvasHeight);
        StdDraw.setXscale(Environment.scaleX1, Environment.scaleX2);
        StdDraw.setYscale(Environment.scaleY1, Environment.scaleY2);
    }

    public void playGame() {
        Player player = new Player();
        Arrow arrow = new Arrow(false, player.getX());
        Bar bar = new Bar(currentTimeMillis());
        bar.setRemainingTime(currentTimeMillis() - bar.getStartTime());
        while (bar.getRemainingTime() > 0) {
            StdDraw.clear();
            DisplayBackground();
            player.DisplayPlayer(arrow);
            bar.DisplayTimeBar();
            StdDraw.show();
            bar.setRemainingTime(currentTimeMillis() - bar.getStartTime());
            StdDraw.pause(PAUSE_DURATION);

        }
        if (bar.getRemainingTime() <= 0) {
            //TODO Game Over
        }
    }

    public void DisplayBackground() {
        StdDraw.picture(backgroundWidth / 2.0, backgroundHeight / 2.0 + barHeight, "images/background.png",
                backgroundWidth, backgroundHeight);
        StdDraw.picture(backgroundWidth / 2.0, barHeight / 2.0, "images/bar.png",
                barWidth, barHeight);
    }
}
