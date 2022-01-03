package ecobike.controller;

import ecobike.common.exception.UserNotRentingException;
import ecobike.controller.base.BaseController;
import ecobike.entity.BikeRentalInfo;
import ecobike.entity.User;
import ecobike.repository.BikeRentalInfoRepo;
import ecobike.security.Authentication;
import ecobike.utils.StopWatch;

public class RentalInfoController extends BaseController {

    private BikeRentalInfo rentalInfo;

    public RentalInfoController() {
        if (!Authentication.isAlreadyRenting()) {
            throw new UserNotRentingException();
        }
        initializeRentalInfo();
    }


    public BikeRentalInfo getRentalInfo() {
        return rentalInfo;
    }

    private void initializeRentalInfo() {
        String userId = Authentication.getInstance().getUserId();
        this.rentalInfo = BikeRentalInfoRepo.findInProgressByUserId(userId);
    }

    public int calculateAmountToPay(int time) {
        return rentalInfo.calculateRentalFee(time);
    }

    public void requestToReturnBike() {
        // pause stop watch
        // calculate amount to pay
        // put amount to renting session

    }
}
