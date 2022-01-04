package ecobike.controller;

import ecobike.common.exception.UserNotRentingException;
import ecobike.controller.base.BaseController;
import ecobike.entity.BikeRentalInfo;
import ecobike.entity.User;
import ecobike.repository.BikeRentalInfoRepo;
import ecobike.security.Authentication;
import ecobike.utils.StopWatch;

/**
 * This class handles bike rental info operations: fetch and store in class instance, calculate rental fee real-time
 */
public class RentalInfoController extends BaseController {

    /**
     * Instance of {@link BikeRentalInfo} object containing current authenticated user's rental info
     */
    private BikeRentalInfo rentalInfo;

    public RentalInfoController() {
        if (!Authentication.isAlreadyRenting()) {
            throw new UserNotRentingException();
        }
        initializeRentalInfo();
    }


    /**
     * Get rental info of current authenticated user
     * @return {@link BikeRentalInfo}
     */
    public BikeRentalInfo getRentalInfo() {
        return rentalInfo;
    }

    /**
     * Fetch and store bike rental info of current authenticated user
     */
    private void initializeRentalInfo() {
        String userId = Authentication.getInstance().getUserId();
        this.rentalInfo = BikeRentalInfoRepo.findInProgressByUserId(userId);
    }

    /**
     * Calculate rental fee real-time
     * @param time current rental time in seconds
     * @return rental fee
     */
    public int calculateAmountToPay(int time) {
        return rentalInfo.calculateRentalFee(time);
    }


}
