package ecobike.view;

import ecobike.controller.DockInfoController;
import ecobike.controller.RentBikeController;
import ecobike.utils.Configs;
import ecobike.view.base.BaseScreenHandler;
import javafx.stage.Stage;

import java.io.IOException;

public class ViewDockHandler extends BaseScreenHandler<DockInfoController> {
    public ViewDockHandler(Stage stage, String screenPath) throws IOException {
        super(stage, screenPath);
    }

    public void viewBikeList() throws IOException {
        //TODO: init controller
        RentBikeHandler rentBikeHandler = new RentBikeHandler(this.stage, Configs.RENT_BIKE_PATH);
        rentBikeHandler.setPreviousScreen(this);

        rentBikeHandler.setBController(new RentBikeController(getBController().getDockId()));
        rentBikeHandler.initializeBikes();
        rentBikeHandler.setScreenTitle("Bike List Screen");
        rentBikeHandler.show();
    }
}
