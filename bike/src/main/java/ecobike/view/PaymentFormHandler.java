package ecobike.view;

import ecobike.controller.PaymentController;
import ecobike.utils.Configs;
import ecobike.view.base.BaseScreenHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
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

    private int amount = 10;

    @FXML
    private Button submitButton;

    public PaymentFormHandler(Stage stage, String screenPath) throws IOException {
        super(stage, screenPath);
        submitButton.setOnMouseClicked(event -> {
            try {
                HashMap<String, String> messages = new HashMap<>();

                messages.put("cardHolder", cardHolder.getText());
                messages.put("cardNumber", cardNumber.getText());
                messages.put("issuingBank", cardNumber.getText());
                messages.put("instructions", issuingBank.getText());
                messages.put("expirationDate", expirationDate.getText());
                messages.put("cvv", cvv.getText());
                messages.put("desc", desc.getText());

                confirmToPayOrder();

                BaseScreenHandler resultScreenHandler = new ResultScreenHandler(this.stage, Configs.PAYMENT_SUCCESS_PATH);
                resultScreenHandler.setPreviousScreen(this);
                resultScreenHandler.setScreenTitle("RentBike");
                resultScreenHandler.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    void confirmToPayOrder() throws IOException{
        PaymentController ctrl = (PaymentController) getBController();
        Map<String, String> response = ctrl.payOrder(10, "pay order", cardNumber.getText(), cardHolder.getText(),
                expirationDate.getText(), cvv.getText());

        System.out.println("RESULT " + response.get("RESULT"));
        System.out.println("MESSAGE " + response.get("MESSAGE"));
    }

}
