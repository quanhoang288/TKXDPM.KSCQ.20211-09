package ecobike.view;

import ecobike.controller.PaymentController;
import ecobike.utils.Configs;
import ecobike.view.base.BaseScreenHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;

public class DockListHandler extends BaseScreenHandler {
    private String[] dockList = {"Dock 1", "Dock 2", "Dock 3", "Dock 4", "Dock 5", "Dock 6"};

    @FXML
    private ListView dockListView;

    public DockListHandler(Stage stage, String screenPath) throws IOException {
        super(stage, screenPath);
        this.initDockList();
    }

    private void initDockList() {
        this.dockListView.getItems().addAll(dockList);
    }

    @FXML
    public void viewDockDetail() throws IOException {
        //TODO: initialize controller for dock detail handler
        System.out.println("asdfasdfasdfadf");
//        BaseScreenHandler dockDetailHandler = new ViewDockHandler(this.stage, Configs.DOCK_DETAIL_PATH);
//        dockDetailHandler.setPreviousScreen(this);
//        dockDetailHandler.setScreenTitle("Dock Detail Screen");
//        dockDetailHandler.show();

        BaseScreenHandler paymentFormHandler = new PaymentFormHandler(this.stage, Configs.PAYMENT_FORM_PATH);
        paymentFormHandler.setPreviousScreen(this);
        paymentFormHandler.setBController(new PaymentController());
        paymentFormHandler.setScreenTitle("Dock Detail Screen");
        paymentFormHandler.show();
    }
}
