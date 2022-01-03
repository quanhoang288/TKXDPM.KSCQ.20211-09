package ecobike.view;

import ecobike.controller.BikeListController;
import ecobike.controller.DockInfoController;
import ecobike.controller.RentBikeController;
import ecobike.repository.BikeRepo;
import ecobike.utils.Configs;
import ecobike.view.base.BaseScreenHandler;
import javafx.stage.Stage;

import java.io.IOException;

public class DockInfoHandler extends BaseScreenHandler<DockInfoController> {
    public DockInfoHandler(Stage stage, String screenPath) throws IOException {
        super(stage, screenPath);
    }

    public void viewBikeList() throws IOException {
        BikeListHandler bikeListHandler = new BikeListHandler(this.stage, Configs.RENT_BIKE_PATH);
        bikeListHandler.setPreviousScreen(this);

        bikeListHandler.setBController(new BikeListController(getBController().getDockId(), new BikeRepo()));
        bikeListHandler.initializeBikes();
        bikeListHandler.setScreenTitle("Bike List Screen");
        bikeListHandler.show();
    }
}
