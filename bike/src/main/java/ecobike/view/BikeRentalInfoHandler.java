package ecobike.view;

import ecobike.controller.RentalInfoController;
import ecobike.controller.base.BaseController;
import ecobike.session.RentingSession;
import ecobike.utils.StopWatchListener;
import ecobike.utils.Utils;
import ecobike.view.base.BaseScreenHandler;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class BikeRentalInfoHandler extends BaseScreenHandler implements StopWatchListener {
    @FXML
    private TextField rentingTimeText;

    @FXML
    private TextField amountToPayText;

    public BikeRentalInfoHandler(Stage stage, String screenPath) throws IOException {
        super(stage, screenPath);
    }

    @Override
    public void update(int time) {
        this.rentingTimeText.setText(Utils.formatTimerDisplay(time));
        if (time % 60 == 0) {
            double amountToPay = getBController().calculateAmountToPay(time);
            this.amountToPayText.setText("" + amountToPay);
        }
    }


    public void pauseRentalTime() {
        RentingSession.getStopWatch().pause();
    }

    public void cancelRentalTime() {
        RentingSession.getStopWatch().cancel();
    }

    @Override
    public RentalInfoController getBController() {
        return (RentalInfoController) super.getBController();
    }
}
