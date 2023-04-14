package com.fast_food.demo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Dashboard.fxml"));
        Scene scene = new Scene(root);
//        Image icon = new Image(getClass().getResourceAsStream("resource/images/logo.png"));
//        stage.getIcons().add(icon);
        stage.setTitle("QUẢN LÝ CỬA HÀNG THỨC ĂN NHANH");


        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}