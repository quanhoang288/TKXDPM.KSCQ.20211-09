package ecobike.view;

import ecobike.controller.ReturnBikeController;
import ecobike.common.exception.DockFullException;
import ecobike.view.base.BaseScreenHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class ReturnBikeHandler extends BaseScreenHandler {
    @FXML
    private ListView dockListView;

    public ReturnBikeHandler(Stage stage, String screenPath) throws IOException {
        super(stage, screenPath);
    }

    @FXML
    public void requestToReturnBike() throws IOException {
        if (this.dockListView.getSelectionModel().isEmpty()) {
            PopupScreen.error("You have to select a dock to return bike");
            return;
        }
        try {
            int selectedIndex = this.dockListView.getSelectionModel().getSelectedIndex();
            getBController().returnBike(selectedIndex);
        } catch (DockFullException e) {
            PopupScreen.error(e.getMessage());
        }
    }

    public ReturnBikeController getBController() {
        return (ReturnBikeController) super.getBController();
    }

    public void initializeDockListView() {
//        List<String> dockNames = getBController().getDockNames();
//        this.dockListView.getItems().addAll(dockNames);
    }

}
