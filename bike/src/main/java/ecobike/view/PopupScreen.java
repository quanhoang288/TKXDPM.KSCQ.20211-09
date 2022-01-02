package ecobike.view;

import ecobike.utils.Configs;
import ecobike.view.base.BaseScreenHandler;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class PopupScreen extends BaseScreenHandler {

    @FXML
    ImageView tickicon;

    @FXML
    Label message;

    public PopupScreen(Stage stage) throws IOException {
        super(stage, Configs.POPUP_PATH);
    }

    private static PopupScreen popup(String message, String imagepath, Boolean undecorated) throws IOException{
        PopupScreen popup = new PopupScreen(new Stage());
        if (undecorated) popup.stage.initStyle(StageStyle.UNDECORATED);
        popup.message.setText(message);
        popup.setImage(imagepath);
        return popup;
    }

    public static void success(String message){
        try{
            popup(message, Configs.IMAGE_PATH + "/" + "check.png", true).show(true);
        }
        catch (IOException e){
            e.printStackTrace();
        }

    }

    public static void error(String message) {
        try {
            popup(message, Configs.IMAGE_PATH + "/" + "times.png", false).show(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setImage(String path) {
//        super.setImage(tickicon, path);
        try{
            URI file = getClass().getResource(path).toURI();
            Image img = new Image(file.toString());
            tickicon.setImage(img);
        }
        catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public void show(Boolean autoclose) {
        super.show();
        if (autoclose) close(0.8);
    }

    public void show(double time) {
        super.show();
        close(time);
    }

    public void close(double time){
        PauseTransition delay = new PauseTransition(Duration.seconds(time));
        delay.setOnFinished( event -> stage.close() );
        delay.play();
    }

}
