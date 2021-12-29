package ecobike.controller;

import ecobike.controller.base.BaseController;
import ecobike.entity.Bike;
import ecobike.utils.PaymentObserver;

public class BikeInfoController extends BaseController implements PaymentObserver {
    private Bike bike;

    public BikeInfoController(Bike bike){
        this.bike = bike;
    }

    public Bike getBike() {
        return bike;
    }

    @Override
    public void update() {
        System.out.println("callback");
    }
}
