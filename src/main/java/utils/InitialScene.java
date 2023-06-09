package utils;

import com.fast_food.demo.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;

public class InitialScene extends Application {

    @Override
    public void start(Stage stage) throws Exception {
<<<<<<< HEAD
        Parent root = FXMLLoader.load(getClass().getResource("/com/fast_food/demo/Login.fxml"));
=======
        Parent root = FXMLLoader.load(getClass().getResource("/com/fast_food/demo/Dashboard.fxml"));
>>>>>>> c254860ce43eea2d95a207dd64c17b32c3139f29
        Scene scene = new Scene(root);
        Image icon = new Image(getClass().getResourceAsStream("/images/logo.png"));
        stage.getIcons().add(icon);

        stage.setTitle("QUẢN LÝ CỬA HÀNG THỨC ĂN NHANH");

        stage.setScene(scene);
        stage.show();
    }
}