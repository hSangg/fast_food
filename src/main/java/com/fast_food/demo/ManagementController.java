package com.fast_food.demo;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ManagementController implements Initializable {

    @FXML
    private VBox employees;

    @FXML
    private VBox ingredient;

    @FXML
    private VBox menu;

    @FXML
    private VBox orders;

    @FXML
    private HBox control_menu;

    @FXML
    private VBox scene_root;
    @FXML
    private Label label_welcome;
    @FXML
    private HBox button_logout;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        employees.setOnMouseClicked(e -> {
            control_menu.setManaged(false);
            control_menu.setVisible(false);


            FXMLLoader employee_management = new FXMLLoader();
            employee_management.setLocation(getClass().getResource("/com/fast_food/demo/EmployeesManagement.fxml"));

            try {
                scene_root.getChildren().add(employee_management.load());
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        orders.setOnMouseClicked(e -> {
            control_menu.setManaged(false);
            control_menu.setVisible(false);


            FXMLLoader employee_management = new FXMLLoader();
            employee_management.setLocation(getClass().getResource("/com/fast_food/demo/OrdersManagement.fxml"));

            try {
                scene_root.getChildren().add(employee_management.load());
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        button_logout.setOnMouseClicked(e -> {
            control_menu.setManaged(false);
            control_menu.setVisible(false);
            FXMLLoader logout= new FXMLLoader();
            logout.setLocation(getClass().getResource("/com/fast_food/demo/Login.fxml"));
            try{
                scene_root.getChildren().add(logout.load());
            }catch (IOException ex){
                throw new RuntimeException(ex);
            }
        });
    }
    public void setUserInformation(String username){
        label_welcome.setText("Chào mừng "+ username + "!");
    }
}
