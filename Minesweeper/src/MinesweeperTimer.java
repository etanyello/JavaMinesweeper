import java.util.Timer;
import java.util.TimerTask;

public class MinesweeperTimer {

    Timer timer;
    TimerTask stopwatch;
    private int centiseconds = 0;

    public MinesweeperTimer()
    {
        timer = new Timer();
        stopwatch = new TimerTask() {
            @Override
            public void run() {
                centiseconds++;
            }
        };

        timer.scheduleAtFixedRate(stopwatch, 0, 10);
    }

    public float GetTime() { return ((float)centiseconds/100f); }

    public void CloseTimer(){
        timer.cancel();
    }
}
