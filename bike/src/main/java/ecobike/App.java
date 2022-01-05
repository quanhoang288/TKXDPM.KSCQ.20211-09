package ecobike;

import ecobike.controller.LoginController;
import ecobike.utils.Configs;
import ecobike.view.LoginHandler;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        LoginHandler loginHandler = new LoginHandler(stage, Configs.LOGIN_PATH);
        loginHandler.setBController(new LoginController());
        loginHandler.setScreenTitle("Home Screen");
        loginHandler.show();

    }

    public static void main(String[] args) {
        launch();
    }
}