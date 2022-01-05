package ecobike.view;

import ecobike.common.exception.BikeAlreadyRentedException;
import ecobike.common.exception.DockFullException;
import ecobike.common.exception.UserAlreadyRentingException;
import ecobike.controller.RentBikeController;
import ecobike.controller.ReturnBikeController;
import ecobike.subsystem.InterbankSubsystem;
import ecobike.utils.Configs;
import ecobike.view.base.BaseScreenHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

/**
 * This class handles displaying list of docks for returning bike
 */
public class ReturnDockListHandler extends BaseScreenHandler {
    @FXML
    private ListView dockListView;

    private ReturnDockListHandler(Stage stage, String screenPath) throws IOException {
        super(stage, screenPath);
    }

    public ReturnDockListHandler(ReturnBikeController returnBikeController, Stage stage, String screenPath) throws IOException {
        this(stage, screenPath);
        setBController(returnBikeController);
        initializeDockListView();
    }

    /**
     * Handle request to return bike after selecting a dock
     * @throws IOException
     */
    public void requestToReturnBike() throws IOException {
        // Check if user has selected any dock to return bike
        if (this.dockListView.getSelectionModel().isEmpty()) {
            PopupScreen.error("You have to select a dock to return bike");
            return;
        }

        // Check if selected dock is full
        try {
            int selectedIndex = this.dockListView.getSelectionModel().getSelectedIndex();
            getBController().checkDockFull(selectedIndex);
        } catch (DockFullException e) {
            PopupScreen.error(e.getMessage());
            return;
        }

        // Set interbank for return bike controller
        getBController().setInterbank(new InterbankSubsystem());

        // Redirect to payment form
        PaymentFormHandler paymentFormHandler = new PaymentFormHandler(getBController(), this.stage, Configs.PAYMENT_FORM_PATH);
        paymentFormHandler.setResultScreenHandler(new ReturnBikeResultHandler(getBController(), this.stage, Configs.PAYMENT_RETURN_SUCCESS_PATH));
        paymentFormHandler.show();
    }


    /**
     * Populate view with dock list data
     */
    private void initializeDockListView() {
        List<String> dockNames = getBController().getDockNames();
        this.dockListView.getItems().addAll(dockNames);
    }

    /**
     * Get base controller
     * @return
     */
    public ReturnBikeController getBController() {
        return (ReturnBikeController) super.getBController();
    }
}
