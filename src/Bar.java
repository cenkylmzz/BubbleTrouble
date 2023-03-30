public class Bar {
    private final long startTime;
    private long remainingTime;
    Bar(long startTime){
        this.startTime = startTime;
        remainingTime = Environment.TOTAL_GAME_DURATION;
    }
    public void displayTimeBar(){
        int r = 255;
        int g = (int) (255 * ((double) remainingTime/Environment.TOTAL_GAME_DURATION));
        int b = 0;
        StdDraw.setPenColor(r, g, b);
        StdDraw.filledRectangle(0, Environment.barHeight/2.0,
                ((remainingTime / (double) Environment.TOTAL_GAME_DURATION) * Environment.backgroundWidth),
                Environment.barHeight/4.0);
    }
    public long getRemainingTime() {
        return remainingTime;
    }
    public void setRemainingTime(long currentTime) {
        this.remainingTime = Environment.TOTAL_GAME_DURATION - (currentTime - startTime);
    }
}
