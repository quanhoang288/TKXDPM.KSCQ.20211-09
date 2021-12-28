package ecobike.utils;

import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;

public class StopWatch extends ScheduledService {
    private int elapsedTimeInSecond;
    private boolean isPaused;

    public StopWatch() {

    }

    @Override
    protected Task createTask() {
        return new Task() {
            @Override
            protected Integer call() throws Exception {
                incrementElapsedTime();
                return getElapsedTimeInSecond();
            }
        };
    }

    @Override
    public boolean cancel() {
        this.isPaused = false;
        return super.cancel();
    }

    public void pause() {
        this.isPaused = true;
    }

    public int getElapsedTimeInSecond() {
        return this.elapsedTimeInSecond;
    }

    private void setElapsedTimeInSecond(int value) {
        this.elapsedTimeInSecond = value;
    }

    private void incrementElapsedTime() {
        if (this.isPaused) return;
        this.setElapsedTimeInSecond(this.elapsedTimeInSecond + 1);
    }
}
