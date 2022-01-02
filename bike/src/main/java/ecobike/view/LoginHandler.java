package ecobike.view;

import ecobike.controller.DockController;
import ecobike.db.DbConnection;
import ecobike.entity.User;
import ecobike.security.Authentication;
import ecobike.utils.Configs;
import ecobike.view.base.BaseScreenHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.io.IOException;

public class LoginHandler extends BaseScreenHandler {

    @FXML
    private TextField username;
    @FXML
    private TextField password;

    @FXML
    private Button loginButton;

    @FXML
    private Label status;
    @FXML
    private void login(ActionEvent event) throws IOException{
        status.setVisible(false);

        String username = this.username.getText();
        String password = this.password.getText();
        EntityManager em = DbConnection.getEntityManager();

        Query query = em.createQuery("select u from User u where u.name = :username and u.password = :password ");
        query.setParameter("username", username);
        query.setParameter("password", password);
        try {
            User user = (User) query.getSingleResult();
            System.out.println(user);

            //set principle
            Authentication.createInstance(username, user.getId());

            DockListHandler dockListHandler = new DockListHandler(this.stage, Configs.DOCK_LIST_PATH);
            dockListHandler.setBController(new DockController());
            dockListHandler.setPreviousScreen(this);
            dockListHandler.initDockList();
            dockListHandler.show();
        }
        catch (NoResultException e){
            status.setVisible(true);
            status.setText("Wrong username or password");
            status.setTextFill(Color.color(1,0,0));

        }
        finally {

        }





    }

    public LoginHandler(Stage stage, String screenPath) throws IOException {
        super(stage, screenPath);
    }

}
