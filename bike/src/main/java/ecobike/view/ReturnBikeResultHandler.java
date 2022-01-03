package ecobike.view;

import ecobike.controller.DockListController;
import ecobike.controller.RentBikeController;
import ecobike.controller.RentBikeResultController;
import ecobike.controller.ReturnBikeController;
import ecobike.entity.BikeRentalInfo;
import ecobike.entity.PaymentTransaction;
import ecobike.utils.Configs;
import ecobike.utils.Utils;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class ReturnBikeResultHandler extends ResultScreenHandler {

    @FXML
    private Button back;
    @FXML
    private Label transactionId;
    @FXML
    private Label transactionDate;
    @FXML
    private Label transactionMethod;
    @FXML
    private Label cardHolder;
    @FXML
    private Label dock;
    @FXML
    private Label bikeType;
    @FXML
    private Label startTime;
    @FXML
    private Label duration;
    @FXML
    private Label depositAmount;
    @FXML
    private Label rentalFee;
    @FXML
    private Label total;

    private ReturnBikeResultHandler(Stage stage, String screenPath) throws IOException {
        super(stage, screenPath);
    }

    public ReturnBikeResultHandler(ReturnBikeController returnBikeController, Stage stage, String screenPath) throws IOException {
        this(stage, screenPath);
        setBController(returnBikeController);
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
    public ReturnBikeController getBController() {
        return (ReturnBikeController) super.getBController();
    }

    private void populateData(PaymentTransaction paymentTransaction, String owner){
        BikeRentalInfo rentalInfo = paymentTransaction.getBikeRentalInfo();

        transactionDate.setText(paymentTransaction.getCreateAt().toString());
        transactionId.setText(paymentTransaction.getId());
        transactionMethod.setText(paymentTransaction.getMethod().toString());
        cardHolder.setText(owner);
        dock.setText(rentalInfo.getBike().getDock().getName());
        bikeType.setText(rentalInfo.getBike().getType().toString());
        startTime.setText(rentalInfo.getStartAt().toString());
        duration.setText(Utils.formatTimerDisplay(rentalInfo.getDurationInSeconds()));
        depositAmount.setText(Utils.getCurrencyFormat(rentalInfo.getBike().getDepositAmount()));
        rentalFee.setText(Utils.getCurrencyFormat(rentalInfo.calculateRentalFee(rentalInfo.getDurationInSeconds())));
        total.setText(Utils.getCurrencyFormat(paymentTransaction.getAmount()));
    }
}
