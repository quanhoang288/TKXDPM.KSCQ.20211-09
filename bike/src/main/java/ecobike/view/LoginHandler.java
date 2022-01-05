package ecobike.view;

import ecobike.common.exception.InvalidCredentialException;
import ecobike.controller.DockListController;
import ecobike.controller.LoginController;
import ecobike.security.Authentication;
import ecobike.utils.Configs;
import ecobike.view.base.BaseScreenHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import javax.persistence.NoResultException;
import java.io.IOException;

/**
 * This class handles user login
 */
public class LoginHandler extends BaseScreenHandler<LoginController> {

    @FXML
    private TextField username;
    @FXML
    private TextField password;

    @FXML
    private Button loginButton;

    @FXML
    private Label status;

    public LoginHandler(Stage stage, String screenPath) throws IOException {
        super(stage, screenPath);
    }

    /**
     * Validate login request and set up singleton {@link Authentication} object
     * After successful login, check if user is currently renting bike, if true, update duration
     * for real-time rent time tracking
     *
     * @param event
     * @throws IOException
     * @throws NoResultException if no user is found with given credentials
     */
    @FXML
    private void login(ActionEvent event) throws IOException {
        status.setVisible(false);

        String username = this.username.getText();
        String password = this.password.getText();


        LoginController controller = getBController();
        try {
            controller.login(username, password);
            DockListHandler dockListHandler = new DockListHandler(this.stage, Configs.DOCK_LIST_PATH);
            dockListHandler.setBController(new DockListController());
            dockListHandler.setPreviousScreen(this);
            dockListHandler.initDockList();
            dockListHandler.show();
        } catch (InvalidCredentialException e) {
            status.setVisible(true);
            status.setText("Wrong username or password");
            status.setTextFill(Color.color(1, 0, 0));
        }


    }

}
