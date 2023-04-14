package com.fast_food.demo;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
public class DashboardController implements Initializable {

    @FXML
    private GridPane menu_layout;

    @FXML
    private GridPane menu_layout1;

    @FXML
    private VBox order_layout;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        int column = 0;
        int row = 1;

        // TODO Auto-generated method stub
        for(int i = 0; i < 10; i++) {
            FXMLLoader fxmlLoad = new FXMLLoader();
            fxmlLoad.setLocation(getClass().getResource("MenuItem.fxml"));

            FXMLLoader fxmlLoaderOrderLayout = new FXMLLoader();
            fxmlLoaderOrderLayout.setLocation(getClass().getResource("OrderItem.fxml"));



            try {
                VBox ItemBox = fxmlLoad.load();

                if (column == 2) {
                    column = 0;
                    ++row;
                }

                menu_layout.add(ItemBox, column++, row);
                GridPane.setMargin(ItemBox, new Insets(30));


                HBox OrderBox =fxmlLoaderOrderLayout.load();
                order_layout.getChildren().add(OrderBox);


            } catch(IOException e) {e.printStackTrace();}



        }
    }
}