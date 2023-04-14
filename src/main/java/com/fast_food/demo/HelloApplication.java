package com.fast_food.demo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        try{
            Parent root = FXMLLoader.load(getClass().getResource("Dashboard.fxml"));
            Scene scene = new Scene(root);
            Image icon = new Image(getClass().getResourceAsStream("/images/logo.png"));
            stage.getIcons().add(icon);
            stage.setTitle("QUẢN LÝ CỬA HÀNG THỨC ĂN NHANH");
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {e.printStackTrace();}
    }


    public static void main(String[] args) {
        launch();
    }
}