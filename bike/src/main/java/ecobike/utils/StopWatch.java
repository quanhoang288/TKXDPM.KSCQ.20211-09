package ecobike.utils;

import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class StopWatch extends ScheduledService {
    private int elapsedTimeInSecond;
    private boolean isPaused;
    List<StopWatchObserver> observers = new ArrayList<>();
    private static StopWatch instance;

    private StopWatch() {
        setPeriod(Duration.seconds(1));
        setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                notifyObservers((Integer) event.getSource().getValue());
            }
        });
    }

    public static StopWatch getInstance() {
        if (instance == null) {
            instance = new StopWatch();
        }
        return instance;
    }

    public void attach(StopWatchObserver observer) {
        observers.add(observer);
    }

    public void notifyObservers(int time) {
        for (StopWatchObserver observer: observers) {
            observer.update(time);
        }
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



    public void startAt(int startTime) {
        this.elapsedTimeInSecond = startTime;
        start();
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
