package com.fast_food.demo;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    private ImageView image_background;


    @FXML
    private HBox button_login;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Rectangle clip = new Rectangle(
                image_background.getFitWidth(),
                image_background.getFitHeight()
        );
        clip.setArcWidth(50);
        clip.setArcHeight(50);
        image_background.setClip(clip);


        button_login.setOnMouseClicked(e -> {


            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/fast_food/demo/Management.fxml"));
            try {
                Parent root = loader.load();

                Stage stage = (Stage) button_login.getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();

            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

    }
}
