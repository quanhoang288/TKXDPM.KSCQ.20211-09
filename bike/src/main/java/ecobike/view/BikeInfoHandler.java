package ecobike.view;

import ecobike.controller.BikeInfoController;
import ecobike.controller.PaymentController;
import ecobike.controller.RentBikeController;
import ecobike.entity.Bike;
import ecobike.utils.Configs;
import ecobike.view.base.BaseScreenHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class BikeInfoHandler extends BaseScreenHandler<BikeInfoController> {

    @FXML
    private Button rentBikeBtn;

    @FXML
    private Label type;
    @FXML
    private Label licensePlate;
    @FXML
    private Label batteryPercent;

    public BikeInfoHandler(Stage stage, String screenPath) throws IOException {
        super(stage, screenPath);
        componentDidMount();
    }

    public void initializeInfo(){
        BikeInfoController ctrl = getBController();
        Bike bike = ctrl.getBike();
        type.setText(bike.getType());
        licensePlate.setText(bike.getLicensePlate());
        batteryPercent.setText(bike.getBatteryPercent()+"%");


    }
    public void componentDidMount(){
        rentBikeBtn.setOnMouseClicked((MouseEvent e) ->{
            try {
                PaymentFormHandler paymentFormHandler = new PaymentFormHandler(this.stage, Configs.PAYMENT_FORM_PATH);
                PaymentController paymentController = new PaymentController();
                paymentFormHandler.setBController(paymentController);
                paymentController.attach(getBController());
                paymentFormHandler.show();

            } catch (IOException ex) {
                ex.printStackTrace();
            }

        });
    }

}
