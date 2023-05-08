package utils;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class InitialScene extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root;
        root = FXMLLoader.load(getClass().getResource("/com/fast_food/demo/Login.fxml"));
        Scene scene = new Scene(root);
        Image icon = new Image(getClass().getResourceAsStream("/images/logo.png"));
        stage.getIcons().add(icon);
        stage.setX(50);
        stage.setY(50);
        stage.setTitle("QUẢN LÝ CỬA HÀNG THỨC ĂN NHANH");
<<<<<<< HEAD
=======
        stage.setX(1);
        stage.setY(1);
>>>>>>> 9b84ed2add4b36cf7cfc62360fe950b00027380c
        stage.setScene(scene);
        stage.show();
    }
}