import static java.lang.System.currentTimeMillis;

public class Main {
    public static void main(String[] args) {
        StdDraw.enableDoubleBuffering();
        StdDraw.setCanvasSize(Environment.canvasWidth, Environment.canvasHeight);
        StdDraw.setXscale(Environment.scaleX1, Environment.scaleX2);
        StdDraw.setYscale(Environment.scaleY1, Environment.scaleY2);
        new Bar((long) (currentTimeMillis()));
    }
}