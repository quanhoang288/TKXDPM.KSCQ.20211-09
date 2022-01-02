package ecobike.view;

import ecobike.controller.AbstractPaymentController;
import ecobike.controller.RentBikePaymentController;
import ecobike.view.base.BaseScreenHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class PaymentFormHandler extends BaseScreenHandler<AbstractPaymentController> {
    @FXML
    private Button submit;
    public PaymentFormHandler(Stage stage, String screenPath) throws IOException {
        super(stage, screenPath);
        componentDidMount();
    }
    private void componentDidMount(){
        submit.setOnMouseClicked((MouseEvent e) ->{
            getBController().performTransactions();
        });
    }
}
