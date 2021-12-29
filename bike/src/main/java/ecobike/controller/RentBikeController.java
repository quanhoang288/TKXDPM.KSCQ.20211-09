package ecobike.controller;

import ecobike.controller.base.BaseController;
import ecobike.entity.Bike;
import ecobike.repository.BikeRepo;
import ecobike.subsystem.barcode.BarcodeConverter;
import ecobike.subsystem.barcode.IBarcode;

import java.util.List;

public class RentBikeController extends BaseController {
    private String dockId;

    public RentBikeController(String dockId) {
        super();
        this.dockId = dockId;
    }
    public String getDockId(){
        return dockId;
    }


    public List<Bike> loadBikeFromDB(int start, int count) {
        assert dockId != null;
        List<Bike> bikes = BikeRepo.getPagingByDock(start, count, dockId);
        return bikes;

    }
    public Bike findById(String id) {
        Bike bike = BikeRepo.findById(id);
        return bike;
    }
    public String convertBarcodeToId(String barcode){
        IBarcode barcodeConverter = new BarcodeConverter();
        return barcodeConverter.convertBarcode2Id(barcode);
    }



}
