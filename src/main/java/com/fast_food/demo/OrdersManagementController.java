package com.fast_food.demo;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;

public class OrdersManagementController implements Initializable {
    @FXML
    private TableView<?> employee_list;

    @FXML
    private ChoiceBox<String> hinh_thuc_thanh_toan;

    @FXML
    private TableColumn<?, ?> so_ban_dat;

    @FXML
    private TableColumn<?, ?> ten_khach_hang;

    @FXML
    private TableColumn<?, ?> thoi_gian;

    @FXML
    private TableColumn<?, ?> tong_tien;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        hinh_thuc_thanh_toan.getItems().addAll("Tiền mặt", "Chuyển khoản");
    }
}
