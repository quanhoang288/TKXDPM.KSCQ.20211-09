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

    public void requestToReturnBike() throws IOException {
        if (this.dockListView.getSelectionModel().isEmpty()) {
            PopupScreen.error("You have to select a dock to return bike");
            return;
        }

        try {
            int selectedIndex = this.dockListView.getSelectionModel().getSelectedIndex();
            getBController().checkDockFull(selectedIndex);
        } catch (DockFullException e) {
            PopupScreen.error(e.getMessage());
            return;
        }

        getBController().setInterbank(new InterbankSubsystem());

        PaymentFormHandler paymentFormHandler = new PaymentFormHandler(getBController(), this.stage, Configs.PAYMENT_FORM_PATH);
        paymentFormHandler.setResultScreenHandler(new ReturnBikeResultHandler(getBController(), this.stage, Configs.PAYMENT_RETURN_SUCCESS_PATH));
        paymentFormHandler.show();
    }


    private void initializeDockListView() {
        List<String> dockNames = getBController().getDockNames();
        this.dockListView.getItems().addAll(dockNames);
    }

    public ReturnBikeController getBController() {
        return (ReturnBikeController) super.getBController();
    }
}
