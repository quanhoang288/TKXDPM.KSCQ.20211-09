package ecobike.controller;

import ecobike.controller.base.BaseController;
import ecobike.entity.Bike;
import ecobike.entity.PaymentTransaction;
import ecobike.utils.PaymentObserver;

import java.util.List;

public class BikeInfoController extends BaseController {
    private Bike bike;

    public BikeInfoController(Bike bike){
        this.bike = bike;
    }

    public Bike getBike() {
        return bike;
    }


}
