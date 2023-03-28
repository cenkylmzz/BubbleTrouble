public class Ball {
    private final double GRAVITATION_CONSTANT = 9.8;
    private final double v;
    private final int ballSize; // radius,ballLevel
    private final double theta;

    public Ball(double v, double theta_degree, int ballSize) {
        this.v = v;
        this.ballSize = ballSize;
        theta = Math.toRadians(theta_degree);
    }


    private double getVx() {
        return v * Math.cos(theta);
    }
    private double getVy() {
        return v * Math.sin(theta);
    }

    public double getCurrentX(double x0, double time) {
        return x0 + getVx() * time;
    }

    public double getCurrentY(double y0, double time) {
        return y0 + getVy() * time - 9.8 * time * time / 2;
    }

    public int getBallSize() {
        return ballSize;
    }
}
