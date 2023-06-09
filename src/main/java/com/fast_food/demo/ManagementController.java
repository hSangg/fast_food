package com.fast_food.demo;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Account;
import model.LogedInUser;
import utils.UtilityFunctions;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ManagementController implements Initializable {

    private LogedInUser userlogin;


    @FXML
    private VBox changepassword;

    @FXML
    private VBox control_menu;

    @FXML
    private HBox control_menu1;

    @FXML
    private VBox dashboard;

    @FXML
    private VBox employees;

    @FXML
    private VBox ingredient;

    @FXML
    private VBox ingredient1;

    @FXML
    private VBox ingredient11;

    @FXML
    private VBox menu;

    @FXML
    private VBox orders;

    @FXML
    private VBox scene_root;

    @FXML
    private VBox supplier;

    @FXML
    private Text text_name;

    @FXML
    private VBox voucher;


    public LogedInUser getUserlogin() {
        return userlogin;
    }


    public void setUserlogin(LogedInUser userlogin) {
        this.userlogin = userlogin;
        text_name.setText(userlogin.getCurentAcc().getName());
        Account currentAccout = userlogin.getCurentAcc();

        UtilityFunctions uf = new UtilityFunctions();
        //phan quyen
        if (currentAccout.getps().equals("Quản lý")) {
            uf.setVisibleNode(orders, false);
            uf.setVisibleNode(ingredient, false);
        } else if (currentAccout.getps().equals("Đầu bếp")) {
            uf.setVisibleNode(menu, false);
            uf.setVisibleNode(employees, false);

        } else if (currentAccout.getps().equals("Thu ngân")) {
            uf.setVisibleNode(supplier, false);
            uf.setVisibleNode(ingredient, false);
            uf.setVisibleNode(orders, false);
            uf.setVisibleNode(employees, false);

        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        scene_root.setOnMouseClicked(e -> {
            scene_root.setAlignment(Pos.TOP_CENTER);

        });

        employees.setOnMouseClicked(e -> {
            control_menu.setManaged(false);
            control_menu.setVisible(false);


            FXMLLoader employee_management = new FXMLLoader();
            employee_management.setLocation(getClass().getResource("/com/fast_food/demo/EmployeesManagement.fxml"));

            try {
                scene_root.getStylesheets().add("/css/managementStyle.css");
                scene_root.getChildren().add(employee_management.load());
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        voucher.setOnMouseClicked(e -> {
            control_menu.setManaged(false);
            control_menu.setVisible(false);


            FXMLLoader fxml = new FXMLLoader();
            fxml.setLocation(getClass().getResource("/com/fast_food/demo/VoucherManagement.fxml"));

            try {
                scene_root.getChildren().add(fxml.load());
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        supplier.setOnMouseClicked(e -> {
            control_menu.setManaged(false);
            control_menu.setVisible(false);


            FXMLLoader fxml = new FXMLLoader();
            fxml.setLocation(getClass().getResource("/com/fast_food/demo/SuppilerManagement.fxml"));

            try {
                scene_root.getStylesheets().add("/css/managementStyle.css");
                scene_root.getChildren().add(fxml.load());
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        dashboard.setOnMouseClicked(e -> {
            //open new UI

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/fast_food/demo/Dashboard.fxml"));

            Scene scene = null;
            try {
                scene = new Scene(fxmlLoader.load());

            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            Stage stage = new Stage();

            stage.setScene(scene);
            stage.show();

        });

        ingredient.setOnMouseClicked(e -> {
            control_menu.setManaged(false);
            control_menu.setVisible(false);


            FXMLLoader fxml = new FXMLLoader();
            fxml.setLocation(getClass().getResource("/com/fast_food/demo/IngredientManagement.fxml"));


            try {
                scene_root.getChildren().add(fxml.load());
                IngredientManagementController IMC = fxml.getController();
                IMC.setCurrentUser(userlogin);
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
        menu.setOnMouseClicked(e->{
            control_menu.setManaged(false);
            control_menu.setVisible(false);
            FXMLLoader employee_management = new FXMLLoader();
            employee_management.setLocation(getClass().getResource("/com/fast_food/demo/MenuManagement.fxml"));

            try {
                scene_root.getChildren().add(employee_management.load());
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

}
