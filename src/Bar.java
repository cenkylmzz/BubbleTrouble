/**
 * The type Bar. This class is used to display the time bar. It also calculates the remaining time.
 * The time bar is displayed at the bottom of the screen.
 * The time bar is yellow at the beginning and turns red as the time passes.
 *
 */
public class Bar {
    private final long startTime;
    private long remainingTime;

    /**
     * Instantiates a new Bar.
     *
     * @param startTime the start time
     */
    Bar(long startTime){
        this.startTime = startTime;
        remainingTime = Environment.TOTAL_GAME_DURATION;
    }

    /**
     * Display time bar. This method is used to display the time bar.
     * It calculates the remaining time and the color of the time bar.
     * It draws the time bar.
     */
    public void displayTimeBar(){
        int r = 255;
        int g = (int) (255 * ((double) remainingTime/Environment.TOTAL_GAME_DURATION));
        int b = 0;
        StdDraw.setPenColor(r, g, b);
        StdDraw.filledRectangle(0, Environment.barHeight/2.0,
                ((remainingTime / (double) Environment.TOTAL_GAME_DURATION) * Environment.backgroundWidth),
                Environment.barHeight/4.0);
    }

    /**
     * Gets remaining time. This method is used to get the remaining time.
     *
     * @return the remaining time
     */
    public long getRemainingTime() {
        return remainingTime;
    }

    /**
     * Sets remaining time. This method is used to set the remaining time.
     *
     * @param currentTime the current time
     */
    public void setRemainingTime(long currentTime) {
        this.remainingTime = Environment.TOTAL_GAME_DURATION - (currentTime - startTime);
    }
}
