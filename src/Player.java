import java.awt.event.KeyEvent;
public class Player {
    private double x = Environment.backgroundWidth/2.0;
    private final double y = Environment.playerBackHeight/2.0 + Environment.barHeight;
    private boolean isHit;
    public void displayPlayer() {
        StdDraw.picture(x, y , "images/player_back.png"
                , Environment.playerBackWidth, Environment.playerBackHeight);
    }
    public void moveLeft() {
        if (x - Environment.playerBackWidth / 2.0 > 0) {
            x -= 11.1/3.0;              //Found experimentally by using the function at the bottom of this class
        }
    }
    public void moveRight() {
        if (x + Environment.playerBackWidth/2.0 < Environment.backgroundWidth) {
            x += 11.1/3.0;            //Found experimentally by using the function at the bottom of this class
        }
    }
    public void playerControls(Arrow arrow){
        if (StdDraw.isKeyPressed(KeyEvent.VK_LEFT)) {
            moveLeft();
        }
        if (StdDraw.isKeyPressed(KeyEvent.VK_RIGHT)) {
            moveRight();
        }
        if (StdDraw.isKeyPressed(KeyEvent.VK_SPACE)) {
            if (!arrow.isFired()) {
                arrow.setFired(true);
                arrow.setX(x);
                arrow.setSize(Environment.playerBackHeight);
            }
        }
    }
    public double getX() {
        return x;
    }
    public double getY() {
        return y;
    }
    public boolean isHit() {
        return isHit;
    }
    public void setIsHit(boolean isHit) {
        this.isHit = isHit;
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
     * @param speed the speed
     */
    private void moveLeftTest(double speed) { //Used to test the speed of the player
        if (x - Environment.playerBackWidth / 2.0 > 0) {
            System.out.println(System.currentTimeMillis());
            x -= speed;
        } else {
            System.out.println(System.currentTimeMillis() + " " + "left max");
        }
    }
    /**
     * Move right test. This function is used to test the speed of the player.
     *  IMPORTANT: NOT DELETED FOR THE PURPOSE OF SHOWING THE COLLISION DETECTION
     *  The following code is for the white box around the player
     *  It is used for ball collision detection to check the equality of the coordinates
     *  of the ball and the player. It holds. However, because player image is not a perfect rectangle,
     *  the final result is not as good as the one with the white box. Therefore, the white box is
     *  commented out. However, it is still kept here for the purpose of showing the collision detection.
     */
    private void displayPlayerTest() {
        StdDraw.setPenColor(255, 255, 255);
        StdDraw.line(x-Environment.playerBackWidth/2.0, y + Environment.playerBackHeight/2.0,
                x+Environment.playerBackWidth/2.0, y + Environment.playerBackHeight/2.0);
        StdDraw.line(x-Environment.playerBackWidth/2.0, y+Environment.playerBackHeight/2.0,
                x-Environment.playerBackWidth/2.0, y-Environment.playerBackHeight/2.0);
        StdDraw.line(x+Environment.playerBackWidth/2.0, y+Environment.playerBackHeight/2.0,
                x+Environment.playerBackWidth/2.0, y-Environment.playerBackHeight/2.0);
        StdDraw.line(x-Environment.playerBackWidth/2.0, y-Environment.playerBackHeight/2.0,
                x+Environment.playerBackWidth/2.0, y-Environment.playerBackHeight/2.0);
    }
}
