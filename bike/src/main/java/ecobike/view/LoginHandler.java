package ecobike.view;

import ecobike.controller.DockListController;
import ecobike.db.DbConnection;
import ecobike.entity.User;
import ecobike.repository.BikeRentalInfoRepo;
import ecobike.repository.UserRepo;
import ecobike.security.Authentication;
import ecobike.utils.Configs;
import ecobike.utils.StopWatch;
import ecobike.utils.Utils;
import ecobike.view.base.BaseScreenHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.io.IOException;
import java.text.ParseException;

/**
 * This class handles user login
 */
public class LoginHandler extends BaseScreenHandler {

    @FXML
    private TextField username;
    @FXML
    private TextField password;

    @FXML
    private Button loginButton;

    @FXML
    private Label status;

    /**
     * Validate login request and set up singleton {@link Authentication} object
     * After successful login, check if user is currently renting bike, if true, update duration
     * for real-time rent time tracking
     * @param event
     * @throws IOException
     * @throws NoResultException if no user is found with given credentials
     */
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

            //set principle
            Authentication.createInstance(username, user.getId());

            if (Authentication.isAlreadyRenting()) {
                // update latest duration for realtime stopwatch
                int latestDuration = BikeRentalInfoRepo.updateLatestDuration(user.getId());
                StopWatch.getInstance().startAt(latestDuration);
            }

            DockListHandler dockListHandler = new DockListHandler(this.stage, Configs.DOCK_LIST_PATH);
            dockListHandler.setBController(new DockListController());
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
