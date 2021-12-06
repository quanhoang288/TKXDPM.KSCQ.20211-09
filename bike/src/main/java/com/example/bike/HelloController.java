package com.example.bike;

import com.example.bike.utils.ResourcePaths;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;

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
    }
}