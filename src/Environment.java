import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import static java.lang.System.currentTimeMillis;
public class Environment {
    static final int backgroundWidth = 640;    // 640 is the width of the background
    static final int backgroundHeight = 480;   // 480 is the height of the background
    static final int barHeight = 66;           // 66 is the height of the bar
    static final int playerBackWidth = 23;     // 23 is the width of the player_back
    static final int playerBackHeight = 37;    // 37 is the height of the player_back
    static final float scaleX2 = 640;         // 640 is the width of the background
    static final float scaleY2 = backgroundHeight + barHeight;    // 66 is the height of the bar , 480 is the height of the background
    static final long TOTAL_GAME_DURATION = 40000;
    Environment() {
        initialize();
        playGame();
    }

    public void initialize() {
        StdDraw.enableDoubleBuffering();
        int canvasWidth = 800;   //Suggested constant
        int canvasHeight = 500;  //Suggested constant
        StdDraw.setCanvasSize(canvasWidth, canvasHeight);
        float scaleX1 = 0; // 0 is the left of the background
        StdDraw.setXscale(scaleX1, scaleX2);
        float scaleY1 = 0; // 0 is the bottom of the background
        StdDraw.setYscale(scaleY1, scaleY2);
    }

    public void playGame() {                    //This method is the main method of the game
        boolean win = false;
        ArrayList<Ball> balls = new ArrayList<>();
        balls.add(new Ball(0.1*6/15,0,4,scaleX2/4, scaleY2/2,currentTimeMillis()));
        balls.add(new Ball(0.1*6/15,180,2,scaleX2/3, scaleY2/3,currentTimeMillis()));
        balls.add(new Ball(0.1*6/15,0,1,scaleX2/4, scaleY2/4,currentTimeMillis()));
        Player player = new Player();
        Arrow arrow = new Arrow(false, player.getX());
        final long startTime = currentTimeMillis();
        Bar bar = new Bar(startTime);
        while (bar.getRemainingTime() > 0) {    //Runtime of the game is 40 seconds
            StdDraw.clear();
            DisplayBackground();
            player.playerControls(arrow);
            arrow.displayMovingArrow();
            DisplayBalls(balls, player, arrow);
            player.displayPlayer();
            bar.displayTimeBar();
            if (balls.size() == 0) {
                StdDraw.clear();
                DisplayBackground();
                StdDraw.picture(player.getX(), player.getY() , "images/player_back.png"
                        , Environment.playerBackWidth, Environment.playerBackHeight);
                StdDraw.picture(arrow.getX(), Environment.barHeight + arrow.getSize() / 2, "images/arrow.png"
                        , 8, arrow.getSize());
                bar.displayTimeBar();
                DisplayGameScreen(true);
                break;
            }
            if (player.isHit()){
                StdDraw.clear();                // Comment out these lines to see
                DisplayBackground();            // how the collision looks like when the player is hit
                StdDraw.picture(player.getX(), player.getY() , "images/player_back.png"
                        , Environment.playerBackWidth, Environment.playerBackHeight);
                bar.displayTimeBar();
                DisplayGameScreen(win);
                break;
            }
            StdDraw.show();
            int PAUSE_DURATION = 15;
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
        // 1045 is the width of the bar
        int barWidth = 1045;
        StdDraw.picture(backgroundWidth / 2.0, barHeight / 2.0, "images/bar.png",
                barWidth, barHeight);
    }
    public void DisplayBalls(ArrayList<Ball> balls, Player player, Arrow arrow){
        for (int i = 0; i< balls.size(); i++) {
            if (balls.get(i).getBallLevel() < 0.2){
                balls.remove(i);
                i--;
                continue;
            }
            balls.get(i).moveBall(currentTimeMillis());
            balls.get(i).displayBall();
            balls.get(i).checkCollisionWithPlayer(player);
            balls.get(i).checkCollisionWithArrow(arrow);
            if (balls.get(i).isHit()) {
                Ball newBall = new Ball(0.1 * 6 / 15*2, 60, balls.get(i).getBallLevel()* 5 / 2, balls.get(i).getX(), balls.get(i).getY(),currentTimeMillis());
                Ball newBall2 = new Ball(0.1 * 6 / 15*2, 120, balls.get(i).getBallLevel() *5 / 2, balls.get(i).getX(), balls.get(i).getY(),currentTimeMillis());
                balls.add(newBall);
                balls.add(newBall2);
                balls.get(i).setHit(false);
                balls.remove(i);
                i--;
            }
        }
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
            StdDraw.text(scaleX2 / 2.0, backgroundHeight/2.0 + barHeight, "GAME OVER!");
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
