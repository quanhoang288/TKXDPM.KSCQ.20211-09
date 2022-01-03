package ecobike.view;

import ecobike.common.exception.InvalidCardException;
import ecobike.common.exception.PaymentException;
import ecobike.entity.PaymentTransaction;
import ecobike.view.base.BaseScreenHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import ecobike.controller.AbstractPaymentController;
import javafx.scene.input.MouseEvent;

import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class PaymentFormHandler extends BaseScreenHandler {

    @FXML
    private TextField cardHolder;
    @FXML
    private TextField cardNumber;
    @FXML
    private TextField issuingBank;
    @FXML
    private TextField expirationDate;
    @FXML
    private TextField cvv;
    @FXML
    private TextArea desc;

    @FXML
    private Button submitButton;

    private ResultScreenHandler resultScreenHandler;

    private PaymentFormHandler(Stage stage, String screenPath) throws IOException {
        super(stage, screenPath);

//        submitButton.setOnMouseClicked(event -> {
//            try {
//                HashMap<String, String> paymentInfo = new HashMap<>();
//
//                paymentInfo.put("cardHolder", cardHolder.getText());
//                paymentInfo.put("cardNumber", cardNumber.getText());
//                paymentInfo.put("instructions", issuingBank.getText());
//                paymentInfo.put("expirationDate", expirationDate.getText());
//                paymentInfo.put("cvv", cvv.getText());
//                paymentInfo.put("desc", desc.getText());
//
//                validateRequiredInput(paymentInfo);
//            } catch (InvalidCardException e) {
//                System.out.println(e.getMessage());
//            }
//
//            try {
//                confirmToRentBike();
//                BaseScreenHandler resultScreenHandler = new ResultScreenHandler(this.stage, Configs.PAYMENT_SUCCESS_PATH);
//                resultScreenHandler.setPreviousScreen(this);
//                resultScreenHandler.show();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        });
    }

    public PaymentFormHandler(AbstractPaymentController paymentController, Stage stage, String screenPath) throws IOException {
        this(stage, screenPath);
        setBController(paymentController);
        componentDidMount();
    }


    private void componentDidMount(){
        submitButton.setOnMouseClicked((MouseEvent e) ->{
            HashMap<String, String> paymentInfo = new HashMap<>();
            paymentInfo.put("cardHolder", cardHolder.getText());
            paymentInfo.put("cardNumber", cardNumber.getText());
            paymentInfo.put("expirationDate", expirationDate.getText());
            paymentInfo.put("cvv", cvv.getText());
            paymentInfo.put("desc", desc.getText());

            System.out.println(paymentInfo);

            try {
                validateRequiredInput(paymentInfo);
                getBController().performTransactions(paymentInfo);
            } catch (PaymentException ex) {
                PopupScreen.error(ex.getMessage());
                return;
            }

            resultScreenHandler.displayResult(paymentInfo.get("cardHolder"));
        });
    }

    private void validateRequiredInput(HashMap<String, String> inputs) {
        String errMsg = "";
        HashMap<String, String> validationRules = new HashMap<>();
        validationRules.put("cardHolder", "Card holder field is required");
        validationRules.put("cardNumber", "Card number field is required");
        validationRules.put("expirationDate", "Expiration date field is required");
        validationRules.put("cvv", "Security code field is required");
//        validationRules.put("desc", "Description field is required");

        for (Map.Entry<String, String> rule : validationRules.entrySet()) {
            if (inputs.get(rule.getKey()).equals("")) {
                errMsg += rule.getValue() + "\n";
            }
        }

        if (errMsg != "") throw  new InvalidCardException(errMsg);
    }

    public AbstractPaymentController getBController() {
        return (AbstractPaymentController) super.getBController();
    }

    public void setResultScreenHandler(ResultScreenHandler resultScreenHandler) {
        this.resultScreenHandler = resultScreenHandler;
    }
}
