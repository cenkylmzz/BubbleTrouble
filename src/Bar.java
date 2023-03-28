public class Bar {
    private final long startTime;
    private long remainingTime;
    Bar(long startTime){
        StdDraw.picture(Environment.backgroundWidth/2.0, Environment.barHeight/2.0 ,
                "images/bar.png", Environment.backgroundWidth,
                Environment.barHeight);
        this.startTime = startTime;
        remainingTime = Environment.TOTAL_GAME_DURATION;
        DisplayRemainingTime(this);
    }
    public void DisplayRemainingTime(Bar bar){
        long currentTime = System.currentTimeMillis();
        remainingTime = Environment.TOTAL_GAME_DURATION - (currentTime - bar.startTime);
        int r = 255;
        int b = 0;
        while (remainingTime > 0) {
            StdDraw.clear();
            new Environment();
            int g = (int) (255 * ((float) remainingTime/Environment.TOTAL_GAME_DURATION));
            StdDraw.setPenColor(r, g, b);
            StdDraw.filledRectangle(0, Environment.barHeight/2.0,
                    ((remainingTime / (float) Environment.TOTAL_GAME_DURATION) * Environment.backgroundWidth),
                    Environment.barHeight/4.0);
            StdDraw.show();
            currentTime = System.currentTimeMillis();
            remainingTime = Environment.TOTAL_GAME_DURATION - (currentTime - bar.startTime);
        }
    }


//        if (remainingTime > 0) {
//            StdDraw.setPenColor(r, g, b);
//            StdDraw.filledRectangle(Environment.backgroundWidth/2.0, Environment.barHeight/2.0,
//                    remainingTime/Environment.TOTAL_GAME_DURATION * Environment.backgroundWidth/2.0,
//                    Environment.barHeight/4.0);
//            StdDraw.show();
//            DisplayRemainingTime(bar);
//        }
//        else {
//           //TODO Game Over
//        }

}
