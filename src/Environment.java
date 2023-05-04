import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import static java.lang.System.currentTimeMillis;

/**
 * The type Environment. This class is the main class of the application.
 * It creates all the objects and runs the game. It also initializes the StdDraw library.
 * StdDraw library is used to draw the game. This class also contains the constants of the game.
 * It also contains the methods that are used in the playGame method. These methods are:
 * displayBackground, displayScore, displayTimeBar, displayPlayer, displayArrow, displayHit, displayGameOver.
 * These methods are used to display the game.
 *
 */
public class Environment {
    /**
     * The Background width.
     */
    static final int backgroundWidth = 640;    // 640 is the width of the background
    /**
     * The Background height.
     */
    static final int backgroundHeight = 480;   // 480 is the height of the background
    /**
     * The Bar height.
     */
    static final int barHeight = 66;           // 66 is the height of the bar
    /**
     * The Player back width.
     */
    static final int playerBackWidth = 23;     // 23 is the width of the player_back
    /**
     * The Player back height.
     */
    static final int playerBackHeight = 37;    // 37 is the height of the player_back
    /**
     * The Scale x 2.
     */
    static final float scaleX2 = 640;         // 640 is the width of the background
    /**
     * The Scale y 2.
     */
    static final float scaleY2 = backgroundHeight + barHeight;    // 66 is the height of the bar , 480 is the height of the background
    /**
     * The Total game duration.
     */
    static final long TOTAL_GAME_DURATION = 40000;

    /**
     * Instantiates a new Environment. This is the constructor of the class. It calls the initialize method and the playGame method.
     */
    Environment() {
        initialize();
        playGame();
    }
    /**
     * Initialize.
     * This method is used to initialize the game
     * It sets the canvas size, the scale of the background and enables double buffering
     */
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

    /**
     * Play game. This method is the main method of the game
     * It creates balls, player, arrow and bar objects and runs the game for 40 seconds (40000 milliseconds)
     * The parameters of the ball constructor are: speed, angle, type, x, y, startTime
     * The speed is the calculated by BallMoveTest method in the Ball class
     * The angle, x , y were given by assignment description
     * The type is the type of the ball. There are 3 types of balls in the game. Their level is 1, 2 and 4.
     * The startTime is the time when the game starts
     * It checks if the player is hit by the ball or not. If the player is hit, the game is over. If the player is not hit, the game continues.
     * It checks if the player has hit the ball or not. If the player has hit the ball, the ball is removed from the game.
     * If the player has not hit the ball, the game continues until the time is over. If the time is over, the game is over.
     * If the player has hit all the balls, the player wins the game.
     *
     */
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
                DisplayBackground();            // how the collision looks like when the player is hit.
                // Also could use the ballRadiusTest and displayPlayerTest methods to see the collision more clearly.
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

    /**
     * Display background.
     * This method is used to display the background and the bar
     *
     */
    public void DisplayBackground() {
        StdDraw.picture(backgroundWidth / 2.0, backgroundHeight / 2.0 + barHeight, "images/background.png",
                backgroundWidth, backgroundHeight);
        // 1045 is the width of the bar
        int barWidth = 1045;
        StdDraw.picture(backgroundWidth / 2.0, barHeight / 2.0, "images/bar.png",
                barWidth, barHeight);
    }

    /**
     * Display balls. This method is used to display the balls
     * It also checks if the ball is hit by the player or the arrow
     * It also checks if the ball is out of the screen
     *
     * @param balls  the balls
     * @param player the player
     * @param arrow  the arrow
     */
    public void DisplayBalls(ArrayList<Ball> balls, Player player, Arrow arrow){
        for (int i = 0; i< balls.size(); i++) {
            if (balls.get(i).getBallLevel() < 0.2){
                balls.remove(i);
                i--;
                continue;
            }
            balls.get(i).moveBall(currentTimeMillis());
            balls.get(i).checkCollisionWithPlayer(player);
            balls.get(i).checkCollisionWithArrow(arrow);
            if (balls.get(i).isHit()) {
                Ball newBall = new Ball(0.1 * 6 / 15*2, 60, balls.get(i).getBallLevel()* 5 / 2, balls.get(i).getX(), balls.get(i).getY(),currentTimeMillis());
                Ball newBall2 = new Ball(0.1 * 6 / 15*2, 120, balls.get(i).getBallLevel() *5 / 2, balls.get(i).getX(), balls.get(i).getY(),currentTimeMillis());
                balls.add(newBall);
                balls.add(newBall2);
                balls.remove(i);
                i--;
            }
            else {
                balls.get(i).displayBall();
            }
        }
    }

    /**
     * Display game screen.
     * This method is used to display the game screen
     * It displays the game over or the you won screen
     * It also displays the replay button
     * It also checks if the player has clicked the replay button
     * It also checks if the player has clicked the quit button
     *
     * @param win the win
     */
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
