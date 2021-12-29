package ecobike.view;

import ecobike.controller.BikeInfoController;
import ecobike.entity.Bike;
import ecobike.view.base.BaseScreenHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class BikeInfoHandler extends BaseScreenHandler {

    @FXML
    private Label type;
    @FXML
    private Label licensePlate;
    @FXML
    private Label batteryPercent;

    public BikeInfoHandler(Stage stage, String screenPath) throws IOException {
        super(stage, screenPath);

    }
    public void initializeInfo(){
        BikeInfoController ctrl = ((BikeInfoController) getBController());
        Bike bike = ctrl.getBike();
        type.setText(bike.getType());
        licensePlate.setText(bike.getLicensePlate());
        batteryPercent.setText(bike.getBatteryPercent()+"%");


    }

}
