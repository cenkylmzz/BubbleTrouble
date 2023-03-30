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
    public Ball(double v, double theta_degree, double ballLevel, double x0, double y0, long startTime) {
        double theta = Math.toRadians(theta_degree);
        vX = v*Math.cos(theta);
        vY = v*Math.sin(theta);
        this.ballLevel = ballLevel * 0.2;
        this.x0 = x0;
        this.y0 = y0;
        this.startTime = startTime;
    }
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
    public void checkEllipseIntersection(double X, double Y, Player player){
        if ((X - x) *(X -x) / (radiusX * ballLevel * radiusX * ballLevel) +
                (Y - y) * (Y -y) / (radiusY * ballLevel * radiusY * ballLevel) < 0.6){ // Ellipse equation
            player.setIsHit(true);                           // 0.6 is a constant that is found experimentally
        }                                                    // To show the ball, player intersection more clearly
    }                                                        // We can change this constant to 1 to see the actual intersection
                                                             // However, the png file of the ball is not a perfect circle
                                                             // and the png of the player is not a perfect rectangle.
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
    public double getBallLevel() {
        return ballLevel;
    }
    public boolean isHit() {
        return isHit;
    }
    public double getX() {
        return x;
    }

    public void setHit(boolean hit) {
        isHit = hit;
    }

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
    public void ballRadiusTest(double X,double Y){                  //X = 34.5, Y = 47.5
        StdDraw.setPenColor(255, 255, 255);
        StdDraw.line(x, y , x+X*34.5, y);
        StdDraw.line(x, y , x, y+Y*47.5);
        StdDraw.line(x, y , x-X*34.5, y);
        StdDraw.line(x, y , x, y-Y*47.5);
    }
}
