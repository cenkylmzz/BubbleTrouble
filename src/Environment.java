import java.awt.*;
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
    static final int ballWidth = 81;           // 81 is the width of the ball
    static final int ballHeight = 81;          // 81 is the height of the ball
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
        StdDraw.setCanvasSize(canvasWidth, canvasHeight);
        StdDraw.setXscale(scaleX1, scaleX2);
        StdDraw.setYscale(scaleY1, scaleY2);
    }

    public void playGame() {                  //This method is the main method of the game
        boolean win = false;
        Ball ball0 = new Ball(0.1*6/15,0,3,scaleX2/4, scaleY2/2);
        Ball ball1 = new Ball(0.1*6/15,180,2,scaleX2/3, scaleY2/3);
        Ball ball2 = new Ball(0.1*6/15,0,1,scaleX2/4, scaleY2/4);
        Player player = new Player();
        Arrow arrow = new Arrow(false, player.getX());
        final long startTime = currentTimeMillis();
        Bar bar = new Bar(startTime);
        while (bar.getRemainingTime() > 0) {            //Runtime of the game is 40 seconds
            StdDraw.clear();
            DisplayBackground();
            player.playerControls(arrow);
            DisplayBalls(ball0, ball1, ball2, startTime, player);
            arrow.DisplayMovingArrow();
            player.DisplayPlayer();
            bar.DisplayTimeBar();
            if (player.isHit()){
                StdDraw.clear();                // Comment out these lines to see
                DisplayBackground();            // how the collision looks like when the player is hit
                StdDraw.picture(player.getX(), player.getY() , "images/player_back.png"
                        , Environment.playerBackWidth, Environment.playerBackHeight);
                bar.DisplayTimeBar();
                DisplayGameScreen(win);
                break;
            }
            StdDraw.show();
            StdDraw.pause(PAUSE_DURATION);
            bar.setRemainingTime(currentTimeMillis());
        }
        if (bar.getRemainingTime() <= 0) {
            StdDraw.clear();
            DisplayBackground();
            StdDraw.picture(player.getX(), player.getY() , "images/player_back.png"
                    , Environment.playerBackWidth, Environment.playerBackHeight);
            DisplayGameScreen(win);
        }
    }

    public void DisplayBackground() {
        StdDraw.picture(backgroundWidth / 2.0, backgroundHeight / 2.0 + barHeight, "images/background.png",
                backgroundWidth, backgroundHeight);
        StdDraw.picture(backgroundWidth / 2.0, barHeight / 2.0, "images/bar.png",
                barWidth, barHeight);
    }
    public void DisplayBalls(Ball ball0, Ball ball1, Ball ball2,long startTime, Player player){
        ball0.moveBall(currentTimeMillis() , startTime);
        ball1.moveBall(currentTimeMillis() , startTime);
        ball2.moveBall(currentTimeMillis() , startTime);
        ball0.DisplayBall();
        ball1.DisplayBall();
        ball2.DisplayBall();
        ball0.checkCollusionWithPlayer(player);
        ball1.checkCollusionWithPlayer(player);
        ball2.checkCollusionWithPlayer(player);
    }
    public void DisplayGameScreen(boolean win){
        Font gameFont = new Font("Helvetica", Font.BOLD, 30);
        Font replayFont = new Font("Helvetica", Font.ITALIC, 15);
        StdDraw.picture(scaleX2 / 2.0, backgroundHeight/2.18 + barHeight, "images/game_screen.png",
                scaleX2/3.8, backgroundHeight/4.0);
        StdDraw.setPenColor(Color.BLACK);
        StdDraw.setFont(gameFont);
        if (win){
            StdDraw.text(scaleX2 / 2.0, backgroundHeight/2.0 + barHeight, "You Won!");
        }
        else {
            StdDraw.text(scaleX2 / 2.0, backgroundHeight/2.0 + barHeight, "Game Over!");
        }
        StdDraw.setFont(replayFont);
        StdDraw.text(scaleX2 / 2.0, backgroundHeight/2.3 + barHeight, "To Replay, Click \"Y\"");
        StdDraw.text(scaleX2 / 2.0, backgroundHeight/2.6 + barHeight, "To Quit, Click \"N\"");
        StdDraw.show();
        while (!StdDraw.isKeyPressed(KeyEvent.VK_Y) || !StdDraw.isKeyPressed(KeyEvent.VK_N)) {
            if (StdDraw.isKeyPressed(KeyEvent.VK_Y)) {
                playGame();
            }
            if (StdDraw.isKeyPressed(KeyEvent.VK_N)) {
                System.exit(0);
            }
        }
    }
}
