package ecobike.view;

import ecobike.common.exception.BikeAlreadyRentedException;
import ecobike.common.exception.UserAlreadyRentingException;
import ecobike.controller.BikeInfoController;
import ecobike.controller.RentBikeController;
import ecobike.entity.Bike;
import ecobike.entity.PaymentTransaction;
import ecobike.subsystem.InterbankSubsystem;
import ecobike.utils.Configs;
import ecobike.utils.Utils;
import ecobike.view.base.BaseScreenHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;


/**
 * This class handle user interactions with bike detail screen and display bike detail information
 */
public class BikeInfoHandler extends BaseScreenHandler<BikeInfoController> {

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
    @FXML
    private Label value;

    private BikeInfoHandler(Stage stage, String screenPath) throws IOException {
        super(stage, screenPath);

    }

    /**
     * Public constructor for initializing view data, event handlers and setting base controller
     * @param bikeInfoController
     * @param stage
     * @param screenPath
     * @throws IOException
     */
    public BikeInfoHandler(BikeInfoController bikeInfoController, Stage stage, String screenPath) throws IOException {
        this(stage, screenPath);
        super.setBController(bikeInfoController);
        registerHandlers();
        initializeInfo();
    }


    /**
     * Populate view with bike information
     */
    public void initializeInfo() {
        BikeInfoController ctrl = getBController();
        Bike bike = ctrl.getBike();
        type.setText(bike.getType().toString());
        licensePlate.setText(bike.getLicensePlate());
        batteryPercent.setText(bike.getBatteryPercent() + "%");
        value.setText(Utils.getCurrencyFormat(bike.getValue()));
    }

    /**
     * Register user event handlers
     */
    public void registerHandlers() {
        backButton.setOnMouseClicked((MouseEvent e) -> {
            getPreviousScreen().show();
        });

        rentBikeBtn.setOnMouseClicked((MouseEvent e) -> {
            RentBikeController rentBikeController;
            // Check if selected bike has already been rented or user is currently renting another bike
            // If false initialize rent bike controller
            try {
                rentBikeController = new RentBikeController(getBController().getBike());
            } catch (BikeAlreadyRentedException | UserAlreadyRentingException ex) {
                PopupScreen.error(ex.getMessage());
                return;
            }

            // set interbank for rent bike controller
            rentBikeController.setInterbank(new InterbankSubsystem());

            // redirect to payment form
            try {
                PaymentFormHandler paymentFormHandler = new PaymentFormHandler(rentBikeController, this.stage, Configs.PAYMENT_FORM_PATH);
                paymentFormHandler.setResultScreenHandler(new RentBikeResultHandler(rentBikeController, this.stage, Configs.PAYMENT_RENT_SUCCESS_PATH));
                paymentFormHandler.show();
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        });
    }

}
