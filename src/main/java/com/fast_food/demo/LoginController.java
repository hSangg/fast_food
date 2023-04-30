package com.fast_food.demo;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Account;
import model.LogedInUser;
import utils.DBHandler;
import utils.UtilityFunctions;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.ResourceBundle;

public class LoginController implements Initializable {


    @FXML
    private HBox button_login;

    @FXML
    private Text t_error_password;

    @FXML
    private Text t_error_username;

    @FXML
    private PasswordField tf_password;

    @FXML
    private TextField tf_username;

    public UtilityFunctions uf = new UtilityFunctions();

    public boolean check_is_error() {
        boolean is_username_error = uf.isEmptyString(tf_username.getText());
        boolean is_pasword_error = uf.isEmptyString(tf_password.getText());

        uf.setVisibleNode(t_error_username, is_username_error);
        uf.setVisibleNode(t_error_password, is_pasword_error);
        t_error_password.setText("Vui lòng điền đầy đủ thông tin");

        return is_username_error || is_pasword_error;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        uf.setVisibleNode(t_error_password,false);
        uf.setVisibleNode(t_error_username,false);

        DBHandler db = new DBHandler();

        button_login.setOnMouseClicked(e -> {
            String username = tf_username.getText();
            String password = tf_password.getText();
            if (!check_is_error()) {
                try {
                    LogedInUser userlogin = db.logIn(username, password);
                    if (userlogin == null) {
                        t_error_password.setText("Sai thông tin đăng nhập");
                        uf.setVisibleNode(t_error_password,true);
                    } else {

                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/fast_food/demo/Management.fxml"));
                        Parent root = fxmlLoader.load();

                        ManagementController managementController = fxmlLoader.getController();
                        managementController.setUserlogin(userlogin);


                        Scene scene = new Scene(root);
                        Stage stage = new Stage();

                        stage.setScene(scene);
                        stage.show();

                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }

            }

        });


    }

}

