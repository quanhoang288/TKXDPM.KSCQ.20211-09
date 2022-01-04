package ecobike.view;

import ecobike.view.base.BaseScreenHandler;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Abstract class for display payment transaction result
 */
public abstract class ResultScreenHandler extends BaseScreenHandler {
    public ResultScreenHandler(Stage stage, String screenPath) throws IOException {
        super(stage, screenPath);
    }

    /**
     * Display result
     * @param owner card-holder name of user performing payment transaction
     */
    public abstract void displayResult(String owner);
}
