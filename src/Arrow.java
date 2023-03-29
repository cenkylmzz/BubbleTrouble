public class Arrow {
    private boolean isFired ;
    private double x ;

    private double size;
    static final int PERIOD_OF_ARROW = 1500;
    public Arrow(boolean isFired, double x) {
        this.isFired = isFired;
        this.x = x;
        this.size = Environment.playerBackHeight/2.0;
    }
    public void DisplayMovingArrow(){
        if (isFired){
            StdDraw.picture(x, Environment.barHeight+size/2, "images/arrow.png"
                    , Environment.arrowWidth, size);
            size += 3;
            if (size > Environment.backgroundHeight){
                isFired = false;
            }
        }
    }
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
}
