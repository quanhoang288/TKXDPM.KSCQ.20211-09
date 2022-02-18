package ecobike.view;

import ecobike.controller.RentalInfoController;
import ecobike.controller.ReturnBikeController;
import ecobike.utils.Configs;
import ecobike.utils.StopWatch;
import ecobike.utils.StopWatchObserver;
import ecobike.utils.Utils;
import ecobike.view.base.BaseScreenHandler;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * This class handles displaying bike rental info and update renting time real-time
 */
public class BikeRentalInfoHandler extends BaseScreenHandler implements StopWatchObserver {
    @FXML
    private TextField rentingTimeText;

    @FXML
    private TextField amountToPayText;

    @FXML
    private TextField bikeType;


    private BikeRentalInfoHandler(Stage stage, String screenPath) throws IOException {
        super(stage, screenPath);
    }

    /**
     * Public constructor for setting up controller, register for stopwatch event and initialize view
     *
     * @param rentalInfoController
     * @param stage
     * @param screenPath
     * @throws IOException
     */
    public BikeRentalInfoHandler(RentalInfoController rentalInfoController, Stage stage, String screenPath) throws IOException {
        this(stage, screenPath);
        StopWatch.getInstance().attach(this);
        setBController(rentalInfoController);
        populateData();
    }

    /**
     * Populate view with rental info data
     */
    private void populateData() {
        RentalInfoController rentalInfoController = getBController();
        int currentDuration = StopWatch.getInstance().getElapsedTimeInSecond();
        int amountToPay = rentalInfoController.calculateAmountToPay(currentDuration);
        this.bikeType.setText(rentalInfoController.getRentalInfo().getBike().getName());
        this.rentingTimeText.setText(Utils.formatTimerDisplay(currentDuration));
        this.amountToPayText.setText("" + Utils.getCurrencyFormat(amountToPay));

    }

    /**
     * Update renting time and rental fee when stopwatch ticks
     *
     * @param time
     */
    @Override
    public void update(int time) {
        this.rentingTimeText.setText(Utils.formatTimerDisplay(time));
        if (time % 60 == 0) {
            int amountToPay = getBController().calculateAmountToPay(time);
            this.amountToPayText.setText("" + Utils.getCurrencyFormat(amountToPay));
        }
    }

    /**
     * Handle request to return bike
     * Redirect to select dock to return bike screen
     *
     * @throws IOException
     */
    public void requestToReturnBike() throws IOException {
        assert getBController().getRentalInfo() != null;
        ReturnBikeController returnBikeController = new ReturnBikeController(getBController().getRentalInfo());
        ReturnDockListHandler returnDockListHandler = new ReturnDockListHandler(returnBikeController, this.stage, Configs.RETURN_BIKE_PATH);
        returnDockListHandler.setPreviousScreen(this);
        returnDockListHandler.setScreenTitle("Return dock list screen");
        returnDockListHandler.show();
    }

    /**
     * Pause renting
     */
    public void pauseRentalTime() {
        //TODO: pause rental info
    }


    /**
     * Get base controller for the class
     *
     * @return
     */
    @Override
    public RentalInfoController getBController() {
        return (RentalInfoController) super.getBController();
    }
}
