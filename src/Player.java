import java.awt.event.KeyEvent;
public class Player {
    private double x = Environment.backgroundWidth/2.0;
    private final double y = Environment.playerBackHeight/2.0 + Environment.barHeight;
    static int PERIOD_OF_PLAYER = 6000;
    public void moveLeft() {
        if (x - Environment.playerBackWidth/2.0 > 0) {
            x -= 31;
        }
    }
    public void moveRight() {
        if (x + Environment.playerBackWidth/2.0 < Environment.backgroundWidth) {
            x += 31;
        }
    }
    public double getX() {
        return x;
    }
    public void setX(double x) {
        this.x = x;
    }
    public double getY() {
        return y;
    }
}
