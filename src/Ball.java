/**
 * The type Ball. This class is used to create a ball object.
 *
 */
public class Ball {
    private final double ballLevel;
    private double vX;
    private double vY;
    private double x;
    private double y;
    private double x0;
    private double y0;
    private long timePassedX = 0;
    private long timePassedY = 0;
    private boolean isFirstDrop = true;
    private boolean isHit = false;
    private final long startTime;
    private final double radiusX = 34.5;   // Found experimentally by drawing a ball and using the function at the bottom for ballLevel = 1
    private final double radiusY = 47.5;   // Found experimentally by drawing a ball and using the function at the bottom for ballLevel = 1

    /**
     * Instantiates a new Ball. This constructor is used when the ball is created at the beginning of the game
     *
     * @param v            the v
     * @param theta_degree the theta degree
     * @param ballLevel    the ball level
     * @param x0           the x 0
     * @param y0           the y 0
     * @param startTime    the start time
     */
    public Ball(double v, double theta_degree, double ballLevel, double x0, double y0, long startTime) {
        double theta = Math.toRadians(theta_degree);
        vX = v*Math.cos(theta);
        vY = v*Math.sin(theta);
        this.ballLevel = ballLevel * 0.2;
        this.x0 = x0;
        this.y0 = y0;
        this.startTime = startTime;
    }

    /**
     * Display ball. This method is used to draw the ball on the canvas
     * The ball is not a circle, it is an ellipse, so we need to scale the width and height of the ball
     * We can do this by multiplying the width and height of the ball by the ratio of the canvas width and height
     * The ball is drawn by using the picture method of StdDraw class
     * The picture method takes 5 parameters, the first one is the x coordinate of the ball
     * The second one is the y coordinate of the ball
     * The third one is the path of the image of the ball
     * The fourth one is the width of the ball
     * The fifth one is the height of the ball
     * The width and height of the ball are calculated by multiplying the ballLevel*Environment.ballWidth
     *
     */
    public void displayBall() {
        int ballWidth = 81;     // 81 is the width of the ball
        int ballHeight = 81;    // 81 is the height of the ball

        StdDraw.picture(x, y, "images/ball.png",
                ballLevel* ballWidth *Environment.scaleY2/Environment.scaleX2,
                ballLevel* ballHeight *Environment.scaleX2/Environment.scaleY2);
        //To draw the ball, we need to know the radius of the ball, which is the ballLevel*Environment.ballWidth
        //But the ball is not a circle in the canvas, it is an ellipse, so we need to scale the width and height of the ball
        //We can do this by multiplying the width and height of the ball by the ratio of the canvas width and height
    }

    /**
     * Check collision with arrow. This method is used to check if the ball is hit by an arrow
     * It is done by checking if the arrow is fired and if the arrow is inside the ellipse of the ball
     * The ellipse equation is (x - x0)^2 / (a^2) + (y - y0)^2 / (b^2) = 1
     * Where x0 and y0 are the coordinates of the center of the ellipse
     * a is the radius of the ellipse in the x direction and b is the radius of the ellipse in the y direction
     *
     * @param arrow the arrow
     */
    public void checkCollisionWithArrow(Arrow arrow){
        double X = arrow.getX();
        double maxY = arrow.getSize();
        double minY = Environment.barHeight;
        if (arrow.isFired()){
            for (double Y = minY; Y <= maxY; Y += 0.1){
                if ((X - x) *(X -x) / (radiusX * ballLevel * radiusX * ballLevel) +
                        (Y - y) * (Y -y) / (radiusY * ballLevel * radiusY * ballLevel) < 1){ // Ellipse equation
                    arrow.setFired(false);
                    isHit = true;
                }
            }
        }
    }

    /**
     * Check collision with player. This method is used to check if the ball is hit by the player
     * It is done by checking if the player is inside the ellipse of the ball
     * It checks the intersection of the ellipse of the ball and the player's top left, top right, middle left, middle right, top middle
     * Instead of checking all the points of the player, we check the intersection of the ellipse of the ball and the above points
     * Because that is enough to check if the player is inside the ellipse of the ball or not
     *
     * @param player the player
     */
    public void checkCollisionWithPlayer(Player player){
        double mostLeftX = player.getX() - Environment.playerBackWidth/2.0;
        double middleX = player.getX();
        double mostRightX = player.getX() + Environment.playerBackWidth/2.0;
        double mostTopY = player.getY() + Environment.playerBackHeight/2.0;
        double middleY = player.getY();
        checkEllipseIntersection(mostLeftX, middleY, player);
        checkEllipseIntersection(mostLeftX, mostTopY, player);
        checkEllipseIntersection(middleX, mostTopY, player);
        checkEllipseIntersection(mostRightX, mostTopY, player);
        checkEllipseIntersection(mostRightX, middleY, player);
    }

    /**
     * Check ellipse intersection. This method is used to check if the player is inside the ellipse of the ball
     * The ellipse equation is (x - x0)^2 / (a^2) + (y - y0)^2 / (b^2) = 1
     * Where x0 and y0 are the coordinates of the center of the ellipse
     * a is the radius of the ellipse in the x direction and b is the radius of the ellipse in the y direction
     *
     * @param X      the x
     * @param Y      the y
     * @param player the player
     */
    public void checkEllipseIntersection(double X, double Y, Player player){
        if ((X - x) *(X -x) / (radiusX * ballLevel * radiusX * ballLevel) +
                (Y - y) * (Y -y) / (radiusY * ballLevel * radiusY * ballLevel) < 0.6){ // Ellipse equation
            player.setIsHit(true);                           // 0.6 is a constant that is found experimentally
        }                                                    // To show the ball, player intersection more clearly
    }                                                        // We can change this constant to 1 to see the actual intersection
    // However, the png file of the ball is not a perfect circle
    // and the png of the player is not a perfect rectangle.
    // So the intersection is not perfect
    /**
     * Move ball. This method is used to move the ball in the canvas
     * The ball is moved by calculating the new x and y coordinates of the ball
     * The new x and y coordinates are calculated by using the equation of motion in the x and y direction
     * The equation of motion in the x direction is x = x0 + v*t
     * The equation of motion in the y direction is y = y0 + v*t - 0.5*g*t^2
     * Where x0 and y0 are the initial coordinates of the ball and v is the velocity of the ball
     * g is the gravity of the ball and t is the time passed
     * The ball bounces off the walls of the canvas
     *
     * @param currentTime the current time
     */

    public void moveBall(long currentTime){
        long moveTimeForX = currentTime - startTime - timePassedX;
        x = x0 + vX*moveTimeForX;
        if (x <= radiusX*ballLevel){
            x0 = radiusX*ballLevel;
            vX = -vX;
            timePassedX = currentTime - startTime;
        }
        if (x >= Environment.backgroundWidth-radiusX*ballLevel){
            x0 = Environment.backgroundWidth-radiusX*ballLevel;
            vX = -vX;
            timePassedX = currentTime - startTime;
        }
        long moveTimeForY = currentTime - startTime - timePassedY;
        double gravity = 0.000003 * 10;
        y = y0 + vY*moveTimeForY - 0.5* gravity *moveTimeForY*moveTimeForY;
        if (y < Environment.barHeight + ballLevel*radiusY){
            if (isFirstDrop){
                long initialDropTime = currentTime - startTime;
                isFirstDrop = false;
                vY = initialDropTime * gravity - vY;
            }
            y0 = Environment.barHeight + ballLevel*radiusY;
            timePassedY = currentTime - startTime;
        }
    }

    /**
     * Gets ball level. This method is used to get the ball level
     * The ball level is used to determine the size of the ball
     *
     * @return the ball level
     */
    public double getBallLevel() {
        return ballLevel;
    }

    /**
     * Is hit boolean. This method is used to check if the ball is hit by the player
     * It is used to determine if the ball should be removed from the canvas
     * If the ball is hit, it will be removed from the canvas
     *
     * @return the boolean
     */
    public boolean isHit() {
        return isHit;
    }

    /**
     * Gets x. This method is used to get the x coordinate of the ball
     *
     * @return the x
     */
    public double getX() {
        return x;
    }

    /**
     * Sets hit. This method is used to set the ball to be hit
     *
     * @param hit the hit
     */
    public void setHit(boolean hit) {
        isHit = hit;
    }

    /**
     * Gets y. This method is used to get the y coordinate of the ball
     *
     * @return the y
     */
    public double getY() {
        return y;
    }

    /**
     * Ball move test. This is used to test the ball movement
     * The first time this method is called, it will set the ball to the most left position that ball can be
     * and print out the time that the ball is set
     * When this function is called, it will move the ball to the right by the speed
     * If the ball hits the wall, it will stop moving and print out the time that the ball hit the wall
     * To calculate the period of the ball, we need to subtract the second time from the first time
     * This should be equal to the period of the ball which is 15000 milliseconds. If it is not, then the ball is not moving at the correct speed
     * When this function is called in the Environment class, the speed is set to 0.1 and the time passed is set to current time - start time
     * This will give a result about 15000*6/15 = 6000 milliseconds. So I multiplied the speed by 6/15 to get the correct speed
     *
     * @param timePassed the time passed
     * @param speed      the speed
     */
    public void ballMoveTest(double timePassed,double speed){
        x = ballLevel*radiusX;
        System.out.println(System.currentTimeMillis());
        x = x0 + speed*timePassed;
        if (x >= Environment.backgroundWidth-radiusX*ballLevel){
            x = Environment.backgroundWidth-radiusX*ballLevel;
            System.out.println(System.currentTimeMillis() + " " + "Ball hit the wall");
        }
    }

    /**
     * Ball radius test. This is used to test the ball radius
     * IMPORTANT: This method is not deleted because it is also used to compare the actual intersection
     * of the ball with the player and arrow since the png file of the ball is not a perfect circle.
     * Since the ball is an ellipse, we need to test to find the radius of the ball
     * It will draw 4 lines from the center of the ball to the edge of the ball in the x and y direction
     *
     * @param X the x
     * @param Y the y
     */
    public void ballRadiusTest(double X,double Y){                  //X = 34.5, Y = 47.5
        StdDraw.setPenColor(255, 255, 255);
        StdDraw.line(x, y , x+X*34.5, y);
        StdDraw.line(x, y , x, y+Y*47.5);
        StdDraw.line(x, y , x-X*34.5, y);
        StdDraw.line(x, y , x, y-Y*47.5);
    }
}
