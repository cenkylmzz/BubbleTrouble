import java.awt.event.KeyEvent;
public class Player {
    private double x = Environment.backgroundWidth/2.0;
    private final double y = Environment.playerBackHeight/2.0 + Environment.barHeight;
    static int PERIOD_OF_PLAYER = 6000;
    public void moveLeft() {
        if (x - Environment.playerBackWidth / 2.0 > 0) {
            x -= 11.1/3.0;              //Found experimentally by using the function below
        }
    }
    public void moveRight() {
        if (x + Environment.playerBackWidth/2.0 < Environment.backgroundWidth) {
            x += 11.1/3.0;            //Found experimentally by using the function below
        }
    }
    public void DisplayPlayer(Arrow arrow){
        if (StdDraw.isKeyPressed(KeyEvent.VK_LEFT)) {
            moveLeft();
        }
        if (StdDraw.isKeyPressed(KeyEvent.VK_RIGHT)) {
            moveRight();
        }
        if (StdDraw.isKeyPressed(KeyEvent.VK_SPACE)) {
            if (!arrow.isFired()) {
                arrow.setFired(true);
                arrow.setX(getX());
                arrow.setSize(Environment.playerBackHeight/2.0);
            }
        }
        arrow.DisplayMovingArrow();
        StdDraw.picture(getX(), getY() , "images/player_back.png"
                , Environment.playerBackWidth, Environment.playerBackHeight);
    }



    /**
     * Move left test. This function is used to test the speed of the player.
     * The first time the player moves left, this code will print out the current time.
     * After that, if the player is at the leftmost position, the code will print out the current time and "left max".
     * Subtract the second time from the first time, and you will get the time it takes for the player to move from
     * the middle position to the leftmost position. Since the period of the player is 6000ms, the subtraction of the
     * two times should be 3000ms. If it is not, then the player is moving too fast or too slow. First, I set the
     * speed of the player to 1, and the time it did take like 3000ms*11.1/3.0 =~ 11100ms So I multiplied the speed by
     * 11.1/3.0 to get the correct speed.
     */
    public void moveLeftTest(double speed) { // TODO: use PERIOD_OF_PLAYER to calculate speed
        if (x - Environment.playerBackWidth / 2.0 > 0) {
            System.out.println(System.currentTimeMillis());
            x -= speed;
        } else {
            System.out.println(System.currentTimeMillis() + " " + "left max");
        }
    }
    public double getX() {
        return x;
    }
    public double getY() {
        return y;
    }
}
