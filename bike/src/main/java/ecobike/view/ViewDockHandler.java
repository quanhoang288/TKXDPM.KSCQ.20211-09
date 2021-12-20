package ecobike.view;

import ecobike.utils.Configs;
import ecobike.view.base.BaseScreenHandler;
import javafx.fxml.FXML;
import javafx.stage.Stage;

import java.io.IOException;

public class ViewDockHandler extends BaseScreenHandler {
    public ViewDockHandler(Stage stage, String screenPath) throws IOException {
        super(stage, screenPath);
    }

    public void viewBikeList() throws IOException {
        //TODO: init controller
        BaseScreenHandler rentBikeHandler = new RentBikeHandler(this.stage, Configs.RENT_BIKE_PATH);
        rentBikeHandler.setPreviousScreen(this);
        rentBikeHandler.setScreenTitle("Bike List Screen");
        rentBikeHandler.show();
    }
}
