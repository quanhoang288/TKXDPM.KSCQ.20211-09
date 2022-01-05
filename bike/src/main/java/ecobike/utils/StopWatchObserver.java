package ecobike.utils;

/**
 * Interface for {@link StopWatch} event-listening classes
 */
public interface StopWatchObserver {
    /**
     * Perform update when stopwatch ticks
     * @param time elapsed time in seconds returned by stopwatch
     */
    void update(int time);
}
