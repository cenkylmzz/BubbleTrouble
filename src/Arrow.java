public class Arrow {
    private boolean isFired ;
    private double x ;
    private double y ;
    static final int PERIOD_OF_ARROW = 1500;
    public Arrow(boolean isFired, double x, double y) {
        this.isFired = isFired;
        this.x = x;
        this.y = y;
    }
    public void moveArrow() {
        while (isFired) {
            final long startTime = System.currentTimeMillis();
            while (System.currentTimeMillis() - startTime < PERIOD_OF_ARROW) {
                y += 1;
                if (y > Environment.backgroundHeight) {
                    isFired = false;
                }
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
    public double getY() {
        return y;
    }
}
