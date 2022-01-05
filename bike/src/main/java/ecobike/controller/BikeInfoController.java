package ecobike.controller;

import ecobike.controller.base.BaseController;
import ecobike.entity.Bike;
import ecobike.entity.PaymentTransaction;
import ecobike.repository.IBikeRepo;


import javax.persistence.NoResultException;
import java.util.List;

/**
 * This class handle fetch currently selected bike from database for displaying information in view
 */
public class BikeInfoController extends BaseController {
    private Bike bike;

    public BikeInfoController(String bikeId, IBikeRepo bikeRepo) throws NoResultException {
        this.bike = bikeRepo.findById(bikeId);
    }

    public Bike getBike() {
        return bike;
    }
}
