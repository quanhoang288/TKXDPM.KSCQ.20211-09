package ecobike.view;

import ecobike.controller.RentBikeController;
import ecobike.utils.Configs;
import ecobike.view.base.BaseScreenHandler;
import javafx.stage.Stage;

import java.io.IOException;

public class ViewDockHandler extends BaseScreenHandler {
    public ViewDockHandler(Stage stage, String screenPath) throws IOException {
        super(stage, screenPath);
    }

    public void viewBikeList() throws IOException {
        //TODO: init controller
        RentBikeHandler<RentBikeController> rentBikeHandler = new RentBikeHandler<>(this.stage, Configs.RENT_BIKE_PATH);
        rentBikeHandler.setPreviousScreen(this);

        rentBikeHandler.setBController(new RentBikeController("e2cf4dca-4ea1-40a3-92c2-6b9c349938c0"));
        rentBikeHandler.initializeBikes();
        rentBikeHandler.setScreenTitle("Bike List Screen");
        rentBikeHandler.show();
    }
}
