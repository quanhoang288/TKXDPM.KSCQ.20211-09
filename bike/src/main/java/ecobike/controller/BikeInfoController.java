package ecobike.controller;

import ecobike.controller.base.BaseController;
import ecobike.entity.Bike;
import ecobike.entity.PaymentTransaction;
import ecobike.repository.IBikeRepo;
import ecobike.utils.PaymentObserver;

import javax.persistence.NoResultException;
import java.util.List;

public class BikeInfoController extends BaseController {
    private Bike bike;

    public BikeInfoController(String bikeId, IBikeRepo bikeRepo) throws NoResultException {
        this.bike = bikeRepo.findById(bikeId);
    }

    public Bike getBike() {
        return bike;
    }
}
