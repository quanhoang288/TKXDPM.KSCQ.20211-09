package eco.bike;

import eco.bike.entity.User;
import eco.bike.utils.Configs;
import eco.bike.utils.MongoDB;
import eco.bike.utils.ResourcePaths;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class HelloController {
    public ImageView img;
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
        File file = new File(ResourcePaths.NOT_FOUND_IMG);
        Image image = new Image(file.toURI().toString());
        img.setImage(image);

        // See MongoDB & Configs

        MongoDB mongoDB = new MongoDB();

        mongoDB.ping_admin();

        List<String> collections = Arrays.asList(
                "_mario_games",
                "_luigi_games",
                "master_links"
        );
        mongoDB.init(Configs.DATABASE_NAME, collections);

        // Initialize new user
        User user = new User(1, "Brown Fox", "2", "City Light", "3 Green Str",
                "01-01-2022", "fox@gs.ctl", "sec_passwd", Arrays.asList(1, 3));

        System.out.printf("user  : %s%n", user);
        mongoDB.insertOneUser(user);

        User hacker = User.loadFromDatabase("fox@gs.ctl", "wer231@!");
        User fox = User.loadFromDatabase("fox@gs.ctl", "sec_passwd");

        if (fox != null) {
            fox.setBikeRentalIds(Arrays.asList(1, 2, 3));
        }
        // update new data
        User.saveToDatabase(fox);

        System.out.printf("hacker: %s%n", hacker == null ? "oops!!!" : hacker.toString());

        System.out.printf("fox   : %s%n", fox == null ? "oops!!!" : fox.toString());

        System.out.println("Fox should have his/her data updated at this point.");

        // Import your json to MongoDB
        mongoDB.importJsonData("/x-files/sec_file.json");
    }
}