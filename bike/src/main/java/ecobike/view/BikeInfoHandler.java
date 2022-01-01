package ecobike.view;

import ecobike.controller.BikeInfoController;
import ecobike.controller.RentBikePaymentController;
import ecobike.controller.RentBikeResultController;
import ecobike.entity.Bike;
import ecobike.entity.PaymentTransaction;
import ecobike.security.Authentication;
import ecobike.utils.Configs;
import ecobike.utils.PaymentObserver;
import ecobike.view.base.BaseScreenHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class BikeInfoHandler extends BaseScreenHandler<BikeInfoController> implements PaymentObserver {

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

    public void initializeInfo() {
        BikeInfoController ctrl = getBController();
        Bike bike = ctrl.getBike();
        type.setText(bike.getType());
        licensePlate.setText(bike.getLicensePlate());
        batteryPercent.setText(bike.getBatteryPercent() + "%");


    }

    public void componentDidMount() {
        rentBikeBtn.setOnMouseClicked((MouseEvent e) -> {
            try {
                PaymentFormHandler paymentFormHandler = new PaymentFormHandler(this.stage, Configs.PAYMENT_FORM_PATH);
                RentBikePaymentController rentBikePaymentController = new RentBikePaymentController(100);
                paymentFormHandler.setBController(rentBikePaymentController);
                rentBikePaymentController.attach(this);
                paymentFormHandler.show();

            } catch (IOException ex) {
                ex.printStackTrace();
            }

        });
    }

    @Override
    public void update(List<PaymentTransaction> transactions) {
        System.out.println("start session");
        try {
            RentBikeResultHandler rentBikeResultHandler = new RentBikeResultHandler(this.stage, Configs.PAYMENT_RENT_SUCCESS_PATH);
            Bike bike = getBController().getBike();

            RentBikeResultController rentBikeResultController = RentBikeResultController.builder()
                    .bikeType(bike.getType())
                    .cardHolderName("Holder ...")
                    .bikeType(bike.getType())
                    .dockName(bike.getDock().getName())
                    .depositAmount("Get in transaction")
                    .transactionDate("Get in transaction")
                    .transactionId("Get in transaction")
                    .build();

            rentBikeResultHandler.setBController(rentBikeResultController);
            rentBikeResultHandler.populateData();
            rentBikeResultHandler.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
