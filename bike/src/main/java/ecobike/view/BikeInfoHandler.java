package ecobike.view;

import ecobike.common.exception.BikeAlreadyRentedException;
import ecobike.common.exception.UserAlreadyRentingException;
import ecobike.controller.BikeInfoController;
import ecobike.controller.RentBikeController;
import ecobike.entity.Bike;
import ecobike.entity.PaymentTransaction;
import ecobike.subsystem.InterbankSubsystem;
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
    private Button backButton;

    @FXML
    private Label type;
    @FXML
    private Label licensePlate;
    @FXML
    private Label batteryPercent;

    private BikeInfoHandler(Stage stage, String screenPath) throws IOException {
        super(stage, screenPath);

    }

    public BikeInfoHandler(BikeInfoController bikeInfoController, Stage stage, String screenPath) throws IOException {
        this(stage, screenPath);
        super.setBController(bikeInfoController);
        registerHandlers();
        initializeInfo();
    }


    public void initializeInfo() {
        BikeInfoController ctrl = getBController();
        Bike bike = ctrl.getBike();
        type.setText(bike.getType());
        licensePlate.setText(bike.getLicensePlate());
        batteryPercent.setText(bike.getBatteryPercent() + "%");
    }

    public void registerHandlers() {
        backButton.setOnMouseClicked((MouseEvent e) -> {
            getPreviousScreen().show();
        });

        rentBikeBtn.setOnMouseClicked((MouseEvent e) -> {
            RentBikeController rentBikeController = null;
            try {
                rentBikeController = new RentBikeController(getBController().getBike());
            } catch (BikeAlreadyRentedException | UserAlreadyRentingException ex) {
                PopupScreen.error(ex.getMessage());
                return;
            }

            rentBikeController.setInterbank(new InterbankSubsystem());
            rentBikeController.attach(this);

            try {
                PaymentFormHandler paymentFormHandler = new PaymentFormHandler(rentBikeController, this.stage, Configs.PAYMENT_FORM_PATH);
                paymentFormHandler.setResultScreenHandler(new RentBikeResultHandler(rentBikeController, this.stage, Configs.PAYMENT_RENT_SUCCESS_PATH));
                paymentFormHandler.show();
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        });
    }

    @Override
    public void update(List<PaymentTransaction> transactions) {
        System.out.println("start session");
//        try {
//            RentBikeResultHandler rentBikeResultHandler = new RentBikeResultHandler(this.stage, Configs.PAYMENT_RENT_SUCCESS_PATH);
//            Bike bike = getBController().getBike();
//
//            RentBikeResultController rentBikeResultController = RentBikeResultController.builder()
//                    .bikeType(bike.getType())
//                    .cardHolderName("Holder ...")
//                    .bikeType(bike.getType())
//                    .dockName(bike.getDock().getName())
//                    .depositAmount("Get in transaction")
//                    .transactionDate("Get in transaction")
//                    .transactionId("Get in transaction")
//                    .build();
//
//            rentBikeResultHandler.setBController(rentBikeResultController);
//            rentBikeResultHandler.populateData();
//            rentBikeResultHandler.show();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
}
