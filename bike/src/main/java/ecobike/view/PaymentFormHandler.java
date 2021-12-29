package ecobike.view;

import ecobike.controller.PaymentController;
import ecobike.view.base.BaseScreenHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class PaymentFormHandler extends BaseScreenHandler {
    @FXML
    private Button submit;
    public PaymentFormHandler(Stage stage, String screenPath) throws IOException {
        super(stage, screenPath);
        componentDidMount();
    }
    private void componentDidMount(){
        submit.setOnMouseClicked((MouseEvent e) ->{
            PaymentController paymentController = (PaymentController) getBController();
            paymentController.pay();
        });
    }
}
