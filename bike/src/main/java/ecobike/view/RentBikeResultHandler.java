package ecobike.view;

import ecobike.controller.DockListController;
import ecobike.controller.RentBikeController;
import ecobike.controller.RentBikeResultController;
import ecobike.entity.PaymentTransaction;
import ecobike.utils.Configs;
import ecobike.utils.Utils;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class RentBikeResultHandler extends ResultScreenHandler {

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



    private RentBikeResultHandler(Stage stage, String screenPath) throws IOException {
        super(stage, screenPath);

    }

    public RentBikeResultHandler(RentBikeController rentBikeController, Stage stage, String screenPath) throws IOException {
        this(stage, screenPath);
        setBController(rentBikeController);
        componentDidMount();
    }

    @Override
    public void displayResult(String owner) {
        PaymentTransaction paymentTransaction = getBController().getPaymentTransaction();
        populateData(paymentTransaction, owner);
        show();
    }

    private void componentDidMount(){
        back.setOnMouseClicked((MouseEvent e) ->{
            try {
                DockListHandler dockListHandler = new DockListHandler(this.stage, Configs.DOCK_LIST_PATH);
                dockListHandler.setBController(new DockListController());
                dockListHandler.initDockList();
                dockListHandler.show();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }

    @Override
    public RentBikeController getBController() {
        return (RentBikeController) super.getBController();
    }

    private void populateData(PaymentTransaction paymentTransaction, String owner){
        System.out.println("Payment transaction: " + paymentTransaction.getCreateAt().toString());
        transactionDate.setText(paymentTransaction.getCreateAt().toString());
        transactionId.setText(paymentTransaction.getId());
        cardHolder.setText(owner);
        dock.setText(paymentTransaction.getBikeRentalInfo().getBike().getDock().getName());
        bikeType.setText(paymentTransaction.getBikeRentalInfo().getBike().getType().toString());
        deposit.setText(Utils.getCurrencyFormat(paymentTransaction.getAmount()));
    }
}
