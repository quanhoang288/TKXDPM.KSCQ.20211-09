package ecobike.controller;

import ecobike.controller.base.BaseController;
import ecobike.entity.Bike;
import ecobike.repository.BikeRepo;
import ecobike.repository.IBikeRepo;
import ecobike.subsystem.barcode.BarcodeConverter;
import ecobike.subsystem.barcode.IBarcode;

import java.util.List;

/**
 * This class is responsible for fetching bike list data from database and perform search based on barcode
 */
public class BikeListController extends BaseController {
    private String dockId;
    private IBikeRepo bikeRepo;


    public BikeListController(String dockId, IBikeRepo bikeRepo) {
        super();
        this.dockId = dockId;
        this.bikeRepo = bikeRepo;
    }

    /**
     * Fetch bike list of a dock with given id with pagination
     * @param start position of the first record to get from database
     * @param count number of records to fetch
     * @return {@link List<Bike>}
     */
    public List<Bike> loadBikeFromDB(int start, int count) {
        assert dockId != null;
        List<Bike> bikes = bikeRepo.getPagingByDock(start, count, dockId);
        return bikes;

    }

    /**
     * Convert from barcode to bike id
     * @param barcode
     * @return {@link String} bike id converted from barcode
     */
    public String convertBarcodeToId(String barcode){
        IBarcode barcodeConverter = new BarcodeConverter();
        return barcodeConverter.convertBarcode2Id(barcode);
    }

}
