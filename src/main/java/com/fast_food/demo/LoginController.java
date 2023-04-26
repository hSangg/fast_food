package com.fast_food.demo;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import model.Account;
import org.w3c.dom.Text;
import utils.DBHandler;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    private ImageView image_background;


    @FXML
    private HBox button_login;
    @FXML
    private TextField tf_username;
    @FXML
    private TextField tf_password;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Rectangle clip = new Rectangle(
                image_background.getFitWidth(),
                image_background.getFitHeight()
        );
        clip.setArcWidth(50);
        clip.setArcHeight(50);
        image_background.setClip(clip);
        DBHandler DB = new DBHandler();
        button_login.setOnMouseClicked(e -> {
            try{
                String username = tf_username.getText();
                String password = tf_password.getText();
                int userfound=0,passwdfound=0;
                HashSet<Account> accs = DB.LoginUser();
                for(Account acc: accs){
                    if(acc.getName().equals(username)){
                        userfound=1;
                        passwdfound=0;
                        if(acc.getPassword().equals(password)) {
                            passwdfound=1;
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
                        }
                    }
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });

    }
}