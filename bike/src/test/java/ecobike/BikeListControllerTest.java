package ecobike;

import ecobike.controller.BikeListController;
import ecobike.controller.RentBikeController;
import ecobike.entity.Bike;
import ecobike.repository.IBikeRepo;
import ecobike.subsystem.barcode.IBarcode;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


class StubBikeRepo implements IBikeRepo {

    @Override
    public List<Bike> getPagingByDock(int start, int count, String dockId) {
        return List.of(new Bike(), new Bike());
    }

    @Override
    public Bike findById(String id) {
        return new Bike();
    }
}

class StubBarCodeConverter implements IBarcode {

    @Override
    public String convertBarcode2Id(String barcode) {
        return barcode;
    }
}

public class BikeListControllerTest {
    @Test
    public void shouldPaginate() {
        IBikeRepo stubBikeRepo = new StubBikeRepo();
        BikeListController bikeListController = new BikeListController("1", stubBikeRepo);
        List bike = bikeListController.loadBikeFromDB(1,2 );
        assertEquals(bike.size(), 2);
    }
    @Test
    public void shouldConvertBarcode(){
        IBikeRepo stubBikeRepo = new StubBikeRepo();
        BikeListController bikeListController = new BikeListController("1", stubBikeRepo);
        String fakeBarCode = "123";
        String convertedBarCode = bikeListController.convertBarcodeToId(fakeBarCode);
        assertEquals(convertedBarCode, fakeBarCode);
    }
}
