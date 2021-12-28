package ecobike.view.base;

import java.io.File;
import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class FXMLScreenHandler {

    protected FXMLLoader loader;
    protected AnchorPane content;
    protected Scene scene;

    public FXMLScreenHandler(String screenPath) throws IOException {
        this.loader = new FXMLLoader(getClass().getResource(screenPath));
        // Set this class as the controller
        this.loader.setController(this);
        this.content = (AnchorPane) loader.load();
    }

    public AnchorPane getContent() {
        return this.content;
    }

    public FXMLLoader getLoader() {
        return this.loader;
    }

    public void show(Stage stage) {
        if (this.scene == null) {
            this.scene = new Scene(this.content);
        }
        stage.setScene(this.scene);
        stage.show();
    }

    public void setImage(ImageView imv, String path){
        File file = new File(path);
        Image img = new Image(file.toURI().toString());
        imv.setImage(img);
    }
}
