package ecobike.view;

import ecobike.controller.DockListController;
import ecobike.controller.DockInfoController;
import ecobike.entity.Dock;
import ecobike.utils.Configs;
import ecobike.view.base.BaseScreenHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class DockListHandler extends BaseScreenHandler<DockListController> {


    private List<Dock> docks;
    @FXML
    private ListView dockListView;

    public DockListHandler(Stage stage, String screenPath) throws IOException {
        super(stage, screenPath);
    }

    public void initDockList() {
        DockListController dockListController = getBController();
        List<Dock> docks = dockListController.findAll();
        this.docks = docks;
        this.dockListView.getItems().addAll(docks.stream().map((Dock dock) -> dock.getAddress()).collect(Collectors.toList()));

    }

    @FXML
    public void viewDockDetail() throws IOException {
        //TODO: initialize controller for dock detail handler
        int selectedItem = dockListView.getSelectionModel().getSelectedIndex();
        BaseScreenHandler dockDetailHandler = new DockInfoHandler(this.stage, Configs.DOCK_DETAIL_PATH);
        dockDetailHandler.setBController(new DockInfoController(docks.get(selectedItem).getId()));
        dockDetailHandler.setPreviousScreen(this);
        dockDetailHandler.setScreenTitle("Dock Detail Screen");
        dockDetailHandler.show();

    }
}
