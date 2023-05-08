package com.fast_food.demo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import model.Employee;
import utils.DBHandler;
import utils.UtilityFunctions;


import java.net.URL;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.ResourceBundle;


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
    private Text text_ng_quan_ly;

    @FXML
    private TextField textfiel_luong;


    @FXML
    private TextField textfield_sdt;

    @FXML
    private ChoiceBox<String> choicebox_chuc_vu;

    @FXML
    private ChoiceBox<String> choicebox_nguoi_quan_ly;

    @FXML
    private TextField textfield_delete_e;

    @FXML
    private Text text_error_validator_chuc_vu;

    @FXML
    private Text text_error_validator_luong;

    @FXML
    private Text text_error_validator_name;

    @FXML
    private Text text_error_validator_nguoi_ql;

    @FXML
    private Text text_error_validator_sdt;


    public String rule_button_submit;

    UtilityFunctions uf = new UtilityFunctions();


    public EmployeesManagementController() {

    }



    public boolean check_is_error() {

        boolean hasError = false;

        hasError |= uf.isEmptyString(textfiel_ho_va_ten.getText()) ? uf.setErrorMsg(text_error_validator_name, "Vui lòng điền thông tin") : uf.hideErrorMsg(text_error_validator_name);
        hasError |= uf.isEmptyString(textfield_sdt.getText()) ? uf.setErrorMsg(text_error_validator_sdt, "Vui lòng điền thông tin") :
                (!uf.isPhoneNumber(textfield_sdt.getText()) ? uf.setErrorMsg(text_error_validator_sdt, "Sai định dạng số điện thoại") : uf.hideErrorMsg(text_error_validator_sdt));
        hasError |= uf.isEmptyString(textfiel_luong.getText()) ? uf.setErrorMsg(text_error_validator_luong, "Vui lòng điền thông tin") :
                (!uf.isNumericString(textfiel_luong.getText()) ? uf.setErrorMsg(text_error_validator_luong, "Sai định dạng lương") : uf.hideErrorMsg(text_error_validator_luong));
        hasError |= uf.isEmptyString(choicebox_chuc_vu.getValue()) ? uf.setErrorMsg(text_error_validator_chuc_vu, "Vui lòng điền thông tin") : uf.hideErrorMsg(text_error_validator_chuc_vu);
        hasError |= uf.isEmptyString(choicebox_nguoi_quan_ly.getValue()) ? uf.setErrorMsg(text_error_validator_nguoi_ql, "Vui lòng điền thông tin") : uf.hideErrorMsg(text_error_validator_nguoi_ql);

        return hasError;
    }




    HashSet<Employee> ds_nguoi_quan_ly;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        DBHandler db = new DBHandler();
        ObservableList<Employee> employeeList = FXCollections.observableArrayList();

        choicebox_chuc_vu.getItems().clear();
        choicebox_nguoi_quan_ly.getItems().clear();


        choicebox_chuc_vu.getItems().addAll("Đầu bếp", "Quản lý", "Thu ngân");
        uf.setVisibleNode(text_error_validator_luong, false);
        uf.setVisibleNode(text_error_validator_name, false);
        uf.setVisibleNode(text_error_validator_chuc_vu, false);
        uf.setVisibleNode(text_error_validator_nguoi_ql, false);
        uf.setVisibleNode(text_error_validator_sdt, false);

        button_submit.setDisable(true);


        try {
            HashSet<Employee> em = db.getAllEmployees();
            for (Employee e : em) {
                employeeList.add(e);
                if ("Quản lý".equals(e.getChuc_vu())) {
                    choicebox_nguoi_quan_ly.getItems().add(e.getTen());
                }
            }

            text_tong_nv.setText(String.valueOf(em.size()));


            luong.setCellValueFactory(new PropertyValueFactory<>("luong"));
            ten.setCellValueFactory(new PropertyValueFactory<>("ten"));
            chuc_vu.setCellValueFactory(new PropertyValueFactory<>("chuc_vu"));
            sdt.setCellValueFactory(new PropertyValueFactory<>("sdt"));
            nguoi_quan_ly.setCellValueFactory(new PropertyValueFactory<>("nguoi_quan_ly"));


            uf.addColumnHeaderIcon(ten, "/images/text-dynamic-gradient.png", "Tên");
            uf.addColumnHeaderIcon(luong, "/images/money-dynamic-gradient.png", "Lương");
            uf.addColumnHeaderIcon(chuc_vu, "/images/star-dynamic-gradient.png", "Chức vụ");
            uf.addColumnHeaderIcon(sdt, "/images/phone-only-dynamic-gradient.png", "SĐT");
            uf.addColumnHeaderIcon(nguoi_quan_ly, "/images/flag-dynamic-gradient.png", "Người quản lý");


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
            button_submit.setDisable(false);

            uf.setVisibleNode(text_ng_quan_ly, true);
            uf.setVisibleNode(textfield_delete_e, false);

            uf.setVisibleNode(choicebox_nguoi_quan_ly, true);


        });

        button_submit.setOnMouseClicked(e -> {
            boolean is_error = check_is_error();

            if (!is_error && rule_button_submit.equals("add")) {
                System.out.println("Thêm nhân viên");

                this.initialize(url, resourceBundle);

            }


            if (!is_error && rule_button_submit.equals("edit")) {

                String e_name = textfiel_ho_va_ten.getText();
                String e_name_delete = textfield_delete_e.getText();
                if (e_name_delete.equals(e_name)) {
                    //xóa nhân viên
                    System.out.println("Xóa nhân viên");

                } else {
                    // sửa những thay đổi
                    System.out.println("Sửa nhân viên");


                }

            }

        });

        employee_list.setOnMouseClicked(e -> {
            if (e.getButton().equals(MouseButton.PRIMARY) && e.getClickCount() == 2) {
                Employee employee = employee_list.getSelectionModel().getSelectedItem();
                if (employee != null) {
                    button_submit.setDisable(false);
                    rule_button_submit = "edit";
                    text_csua_them.setText("Chỉnh sửa nhân viên");
                    textfiel_ho_va_ten.setText(employee.getTen());
                    textfiel_luong.setText(String.valueOf(employee.getLuong()));
                    textfield_sdt.setText(employee.getSdt());

                    uf.setVisibleNode(text_ng_quan_ly, true);
                    uf.setVisibleNode(textfield_delete_e, true);


                    if (employee.getChuc_vu().equals("Quản lý")) {
                        uf.setVisibleNode(choicebox_nguoi_quan_ly, false);
                        uf.setVisibleNode(text_ng_quan_ly, false);


                    } else {

                        choicebox_nguoi_quan_ly.setValue(employee.getNguoi_quan_ly());
                        uf.setVisibleNode(choicebox_nguoi_quan_ly, true);
                    }


                    choicebox_chuc_vu.setValue(employee.getChuc_vu());
                }
            }

        });


    }
}
