package ecobike.controller;

import ecobike.controller.base.BaseController;
import ecobike.entity.Bike;
import ecobike.repository.BikeRepo;
import ecobike.repository.IBikeRepo;
import ecobike.subsystem.barcode.BarcodeConverter;
import ecobike.subsystem.barcode.IBarcode;

import java.util.List;

public class BikeListController extends BaseController {
    private String dockId;
    private IBikeRepo bikeRepo;


    public BikeListController(String dockId, IBikeRepo bikeRepo) {
        super();
        this.dockId = dockId;
        this.bikeRepo = bikeRepo;
    }
    public String getDockId(){
        return dockId;
    }

    public List<Bike> loadBikeFromDB(int start, int count) {
        assert dockId != null;
        List<Bike> bikes = bikeRepo.getPagingByDock(start, count, dockId);
        return bikes;

    }

    public String convertBarcodeToId(String barcode){
        IBarcode barcodeConverter = new BarcodeConverter();
        return barcodeConverter.convertBarcode2Id(barcode);
    }

}
