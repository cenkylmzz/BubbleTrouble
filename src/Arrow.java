public class Arrow {
    private boolean isFired ;
    private double x ;
    private double size;
    static final int PERIOD_OF_ARROW = 1500;
    public Arrow(boolean isFired, double x) {
        this.isFired = isFired;
        this.x = x;
    }
    public void DisplayMovingArrow(){
        if (isFired) {
            StdDraw.picture(x, Environment.barHeight + size / 2, "images/arrow.png"
                    , Environment.arrowWidth, size);
            size += 10.6;                  //Found experimentally by using the function at the bottom of this class
            if (size > Environment.backgroundHeight + 10) {
                                            //The last size of the arrow will be 2 pixel more than the height of the screen
                isFired = false;            //To fit the last appearance of arrow in the screen, I added 10 to the height
            }                               //not 11 because the arrow is incremented by 10.6 every time.
        }                                   //This ensures that the arrow will reach at the top of the screen when it is fired
    }                                       //and prevents the arrow to go more than 2 pixel above of the screen.
    public boolean isFired() {
        return isFired;
    }
    public void setFired(boolean fired) {
        isFired = fired;
    }
    public double getX() {
        return x;
    }
    public void setX(double x) {
        this.x = x;
    }
    public double getSize() {
        return size;
    }
    public void setSize(double size) {
        this.size = size;
    }
    /**
     * Moving arrow test. This function is used to test the speed of the arrow.
     * The first time the arrow is fired, this code will print out the current time.
     * After that, if the arrow is at the top of the screen, the code will print out the current time and "arrow max reached".
     * Subtract the second time from the first time, and you will get the time it takes for the arrow to move from
     * the bottom of the screen to the top of the screen. Since the period of the arrow is 1500ms, the subtraction of the
     * two times should be 1500ms. If it is not, then the arrow is moving too fast or too slow. First, I set the
     * speed of the arrow to 1, and the time it did take like 1500ms*10.6 =~ 15.900ms So I multiplied the speed by
     * 10.6 to get the correct speed.
     *
     * @param speed the speed
     */
    private void MovingArrowTest(double speed){ //Used to test the speed of the arrow
        if (isFired){
            System.out.println(System.currentTimeMillis());
            StdDraw.picture(x, Environment.barHeight+size/2, "images/arrow.png"
                    , Environment.arrowWidth, size);
            size += speed;
            if (size > Environment.backgroundHeight + speed - 1){
                isFired = false;
                System.out.println(System.currentTimeMillis() + " " + "arrow max reached");
            }
        }
    }
}
