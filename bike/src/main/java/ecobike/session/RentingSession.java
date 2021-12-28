package ecobike.session;


import ecobike.entity.BikeRentalInfo;
import ecobike.exception.RentingSessionNotInitializedException;
import ecobike.utils.Configs;
import ecobike.utils.StopWatch;
import ecobike.utils.StopWatchListener;
import ecobike.view.BikeRentalInfoHandler;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;


public class RentingSession {
    private static BikeRentalInfo currentRentalInfo;
    private static String bikeType;
    private static BikeRentalInfoHandler bikeRentalInfoHandler;
    private static StopWatch stopWatch;
    private static RentingSession session;

    private RentingSession(BikeRentalInfo rentalInfo, BikeRentalInfoHandler rentalInfoHandler, String rentedBikeType) {
        currentRentalInfo = rentalInfo;
        bikeType = rentedBikeType;
        bikeRentalInfoHandler = rentalInfoHandler;
        stopWatch = new StopWatch();
    }

    public static void initialize(BikeRentalInfo rentalInfo, BikeRentalInfoHandler rentalInfoHandler, String rentedBikeType) {
        if (session == null) {
            session = new RentingSession(rentalInfo, rentalInfoHandler, rentedBikeType);
        }
    }

    public static RentingSession getCurrentSession() {
        if (session == null) {
            throw new RentingSessionNotInitializedException();
        }
        return session;
    }

    public void startSession() {
        stopWatch.setPeriod(Duration.seconds(1));
        stopWatch.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                bikeRentalInfoHandler.update((Integer) event.getSource().getValue());
            }
        });
        stopWatch.start();
    }

    public void pauseSession() {
        stopWatch.pause();
    }

    public void clearSession() {
        currentRentalInfo = null;
        bikeType = null;
        bikeRentalInfoHandler = null;
        stopWatch.cancel();
    }

    public static StopWatch getStopWatch() {
        if (stopWatch == null) {
            stopWatch = new StopWatch();
        }
        return stopWatch;
    }

    public static BikeRentalInfoHandler getBikeRentalInfoHandler() {
        return bikeRentalInfoHandler;
    }

    public static String getCurrentRentedBikeType() {
        return bikeType;
    }

    public static double calculateAmountToPay() {
        int elapsedTime = stopWatch.getElapsedTimeInSecond();
        System.out.println("Renting time: " + elapsedTime + "s");
        double dTime = (double) elapsedTime;
        if (dTime <= 60) {
            return 0;
        }

        double amount = 10000;
        dTime -= 30 * 60;
        if (dTime > 0) {
            amount += 3000 * Math.ceil(dTime / (2 * 60));
        }

        if (bikeType == Configs.BIKE_TYPES[1] || bikeType == Configs.BIKE_TYPES[2]) {
            amount *= 1.5;
        }

        return amount;
    }

}
