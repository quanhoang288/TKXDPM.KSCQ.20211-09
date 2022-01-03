package ecobike.view;

import ecobike.view.base.BaseScreenHandler;
import javafx.stage.Stage;

import java.io.IOException;

public abstract class ResultScreenHandler extends BaseScreenHandler {
    public ResultScreenHandler(Stage stage, String screenPath) throws IOException {
        super(stage, screenPath);
    }

    public abstract void displayResult(String owner);
}
