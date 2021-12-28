package ecobike.controller;

import ecobike.controller.base.BaseController;
import ecobike.session.RentingSession;
import ecobike.utils.Configs;

public class RentalInfoController extends BaseController {

    public static double calculateAmountToPay(int time) {
        System.out.println("Renting time: " + time + "s");
        double dTime = (double) time;
        if (dTime <= 60) {
            return 0;
        }

        double amount = 10000;
        dTime -= 30 * 60;
        if (dTime > 0) {
            amount += 3000 * Math.ceil(dTime / (2 * 60));
        }

        String bikeType = RentingSession.getCurrentRentedBikeType();
        if (bikeType == Configs.BIKE_TYPES[1] || bikeType == Configs.BIKE_TYPES[2]) {
            amount *= 1.5;
        }

        return amount;
    }

    public void requestToReturnBike() {
        // pause stop watch
        // calculate amount to pay
        // put amount to renting session

    }
}
