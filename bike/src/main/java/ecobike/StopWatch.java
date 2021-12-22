package ecobike;

import java.util.Timer;
import java.util.TimerTask;

class DemoTask extends TimerTask {
    private int elapsedTimeInSeconds;

    public DemoTask() {
        this.elapsedTimeInSeconds = 0;
    }

    @Override
    public void run() {
        this.elapsedTimeInSeconds++;
        System.out.println("Elapsed time in seconds: " + this.elapsedTimeInSeconds);
    }

}

public class StopWatch {
    private Timer timer;
    private TimerTask scheduledTask;

    public StopWatch() {
        this.timer = new Timer();
        this.scheduledTask = new DemoTask();
    }

    public void start() {
        timer.schedule(this.scheduledTask, 1000, Integer.MAX_VALUE);
    }

    public void pause() {
        timer.cancel();
    }

    public void reset() {
        timer.cancel();
    }

    public static void main(String[] args) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
    }
}
