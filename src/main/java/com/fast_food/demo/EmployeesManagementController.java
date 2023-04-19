package com.fast_food.demo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import model.Employee;
import utils.DBHandler;

import java.net.URL;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicReference;



public class EmployeesManagementController implements Initializable {

    @FXML
    private TableView<Employee> employee_list;

    @FXML
    private TableColumn<?, ?> id;

    @FXML
    private TableColumn<?, ?> ten;

    @FXML
    private TableColumn<?, ?> chuc_vu;

    @FXML
    private TableColumn<?, ?> nguoi_quan_ly;

    @FXML
    private TableColumn<?, ?> sdt;

    @FXML
    private TableColumn<?, ?> luong;

    @FXML
    private Label text_tong_nv;

    @FXML
    private TextField textfield_tim_kiem_nv;

    @FXML
    private HBox button_submit;

    @FXML
    private HBox button_them_nv;

    @FXML
    private Label text_csua_them;

    @FXML
    private TextField textfiel_ho_va_ten;

    @FXML
    private TextField textfiel_luong;

    @FXML
    private TextField textfield_sdt;

    @FXML
    private ChoiceBox<String> choicebox_chuc_vu;

    @FXML
    private ChoiceBox<String> choicebox_nguoi_quan_ly;

    public String rule_button_submit;

    public EmployeesManagementController() {
    }

    HashSet<Employee> ds_nguoi_quan_ly;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        DBHandler db = new DBHandler();
        ObservableList<Employee> employeeList = FXCollections.observableArrayList();
        choicebox_chuc_vu.getItems().addAll("Đầu bếp", "Quản lý", "Thu ngân");


        try {
            HashSet<Employee> em = db.getAllEmployees();
            for (Employee e : em) {
                employeeList.add(e);
                if ("Quản lý".equals(e.getChuc_vu())) {
                    choicebox_nguoi_quan_ly.getItems().add(e.getTen());
                }
            }



            luong.setCellValueFactory(new PropertyValueFactory<>("luong"));
            ten.setCellValueFactory(new PropertyValueFactory<>("ten"));
            chuc_vu.setCellValueFactory(new PropertyValueFactory<>("chuc_vu"));
            sdt.setCellValueFactory(new PropertyValueFactory<>("sdt"));
            nguoi_quan_ly.setCellValueFactory(new PropertyValueFactory<>("nguoi_quan_ly"));

            employee_list.setItems(employeeList);


            text_tong_nv.setText(String.valueOf(em.size()));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        button_them_nv.setOnMouseClicked(e -> {
            rule_button_submit = "add";
            text_csua_them.setText("Thêm nhân viên");
            textfield_sdt.clear();
            textfiel_luong.clear();
            textfiel_ho_va_ten.clear();
            choicebox_chuc_vu.setValue(null);
            choicebox_nguoi_quan_ly.setValue(null);
        });

        button_submit.setOnMouseClicked(e -> {
            if (rule_button_submit.equals("add")) {
                System.out.println("add");
            } else {
                System.out.println("edit");

            }

        });


        employee_list.setOnMouseClicked(e -> {
            if (e.getButton().equals(MouseButton.PRIMARY) && e.getClickCount() == 2) {
                Employee employee = employee_list.getSelectionModel().getSelectedItem();
                if (employee != null) {
                    rule_button_submit = "edit";
                    text_csua_them.setText("Chỉnh sửa nhân viên");
                    textfiel_ho_va_ten.setText(employee.getTen());
                    textfiel_luong.setText(String.valueOf(employee.getLuong()));
                    textfield_sdt.setText(employee.getSdt());
                    if (employee.getChuc_vu().equals("Quản lý")) {
                        choicebox_nguoi_quan_ly.setVisible(false);
                    } else {
                        choicebox_nguoi_quan_ly.setVisible(true);
                        choicebox_nguoi_quan_ly.setValue(employee.getNguoi_quan_ly());
                    }
                    choicebox_chuc_vu.setValue(employee.getChuc_vu());
                }
            }

        });






    }
}
