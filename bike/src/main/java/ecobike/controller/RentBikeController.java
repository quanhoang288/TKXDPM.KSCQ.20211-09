package ecobike.controller;

import ecobike.controller.base.BaseController;
import ecobike.entity.Bike;
import ecobike.repository.BikeRepo;

import java.util.List;

public class RentBikeController extends BaseController {
    private String dockId;

    public RentBikeController(String dockId) {
        this.dockId = dockId;
    }

    public List<Bike> loadBikeFromDB(int start, int count) {
        assert dockId != null;
        List<Bike> bikes = BikeRepo.getPagingByDock(start, count, dockId);
        return bikes;

    }


}
