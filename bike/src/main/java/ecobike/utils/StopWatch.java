package ecobike.utils;

import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

/**
 * This class handles ticking stopwatch real-time
 */
public class StopWatch extends ScheduledService {
    /**
     * Elapsed time tracked in seconds
     */
    private int elapsedTimeInSecond;

    /**
     * Indicate whether the stopwatch is paused or not
     */
    private boolean isPaused;

    /**
     * List of listeners for clock tick event
     */
    List<StopWatchObserver> observers = new ArrayList<>();

    /**
     * Singleton instance for accessing current elapsed time
     */
    private static StopWatch instance;

    /**
     * Initialize scheduled job interval and callback on job finished
     */
    private StopWatch() {
        setPeriod(Duration.seconds(1));
        setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                notifyObservers((Integer) event.getSource().getValue());
            }
        });
    }

    /**
     * Get singleton instance
     * @return
     */
    public static StopWatch getInstance() {
        if (instance == null) {
            instance = new StopWatch();
        }
        return instance;
    }

    /**
     * Add clock-tick event listener
     * @param observer
     */
    public void attach(StopWatchObserver observer) {
        observers.add(observer);
    }

    /**
     * Notify listeners with latest elapsed time
     * @param time
     */
    public void notifyObservers(int time) {
        for (StopWatchObserver observer: observers) {
            observer.update(time);
        }
    }

    /**
     * Create clock tick task
     * @return
     */
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

    /**
     * Cancel all scheduled tasks and stop the stopwatch
     * @return
     */
    @Override
    public boolean cancel() {
        this.elapsedTimeInSecond = 0;
        this.isPaused = false;
        return super.cancel();
    }


    /**
     * Start the stopwatch at a specific time
     * @param startTime
     */
    public void startAt(int startTime) {
        this.elapsedTimeInSecond = startTime;
        start();
    }

    /**
     * Pause the stopwatch
     */
    public void pause() {
        this.isPaused = true;
    }

    /**
     * Get elapsed time in second
     * @return
     */
    public int getElapsedTimeInSecond() {
        return this.elapsedTimeInSecond;
    }

    /**
     * Set elapsed time
     * @param value new value to be set
     */
    private void setElapsedTimeInSecond(int value) {
        this.elapsedTimeInSecond = value;
    }

    /**
     * Increment elapsed time by one if not paused
     */
    private void incrementElapsedTime() {
        if (this.isPaused) return;
        this.setElapsedTimeInSecond(this.elapsedTimeInSecond + 1);
    }
}
