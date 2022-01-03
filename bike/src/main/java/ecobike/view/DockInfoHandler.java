package ecobike.view;

import ecobike.controller.BikeListController;
import ecobike.controller.DockInfoController;
import ecobike.controller.RentBikeController;
import ecobike.entity.Dock;
import ecobike.repository.BikeRepo;
import ecobike.utils.Configs;
import ecobike.view.base.BaseScreenHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class DockInfoHandler extends BaseScreenHandler<DockInfoController> {

    @FXML
    private Label nameText;
    @FXML
    private Label addressText;
    @FXML
    private Label areaText;
    @FXML
    private Label numBikesText;

    private DockInfoHandler(Stage stage, String screenPath) throws IOException {
        super(stage, screenPath);
    }

    public DockInfoHandler(DockInfoController dockInfoController, Stage stage, String screenPath) throws IOException {
        this(stage, screenPath);
        setBController(dockInfoController);
        initialize();
    }

    private void initialize() {
        Dock dock = getBController().getDock();
        nameText.setText(dock.getName());
        addressText.setText(dock.getAddress());
        areaText.setText("" + (int) (dock.getArea()));
        numBikesText.setText("" + dock.getBikes().size());
    }

    public void viewBikeList() throws IOException {
        BikeListHandler bikeListHandler = new BikeListHandler(this.stage, Configs.RENT_BIKE_PATH);
        bikeListHandler.setPreviousScreen(this);

        bikeListHandler.setBController(new BikeListController(getBController().getDock().getId(), new BikeRepo()));
        bikeListHandler.initializeBikes();
        bikeListHandler.setScreenTitle("Bike List Screen");
        bikeListHandler.show();
    }
}
