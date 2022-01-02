package ecobike.view;

import ecobike.controller.DockController;
import ecobike.controller.RentBikeResultController;
import ecobike.utils.Configs;
import ecobike.view.base.BaseScreenHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class RentBikeResultHandler extends BaseScreenHandler<RentBikeResultController> {

    @FXML
    private Button back;

    @FXML
    private Label transactionId;

    @FXML
    private Label transactionDate;

    @FXML
    private Label cardHolder;

    @FXML
    private Label dock;
    @FXML
    private Label bikeType;

    @FXML
    private Label deposit;



    public RentBikeResultHandler(Stage stage, String screenPath) throws IOException {
        super(stage, screenPath);
        componentDidMount();
    }
    private void componentDidMount(){
        back.setOnMouseClicked((MouseEvent e) ->{
            try {
                DockListHandler dockListHandler = new DockListHandler(this.stage, Configs.DOCK_LIST_PATH);
                dockListHandler.setBController(new DockController());
                dockListHandler.initDockList();
                dockListHandler.show();
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        });
    }
    public void populateData(){
        RentBikeResultController rentBikeResultController = getBController();
        transactionDate.setText(rentBikeResultController.getTransactionDate());
        transactionId.setText(rentBikeResultController.getTransactionId());
        cardHolder.setText(rentBikeResultController.getCardHolderName());
        dock.setText(rentBikeResultController.getDockName());
        bikeType.setText(rentBikeResultController.getBikeType());
        deposit.setText(rentBikeResultController.getDepositAmount());
    }
}
