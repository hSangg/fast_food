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

    public EmployeesManagementController() {

    }

    public void set_Text_Visible(Text x, boolean y) {
        x.setManaged(y);
        x.setVisible(y);
    }



    public boolean check_is_error() {
        UtilityFunctions utilityFunctions = new UtilityFunctions();

        boolean is_ten_error = false;
        boolean is_sdt_error = false;
        boolean is_luong_error = false;
        boolean is_chuc_vu_error = false;
        boolean is_nguoi_ql_error = false;

        String s_ten = textfiel_ho_va_ten.getText();
        String s_sdt = textfield_sdt.getText();
        String s_luong = textfiel_luong.getText();
        String s_nguoi_ql = choicebox_nguoi_quan_ly.getValue();
        String s_chuc_vu = choicebox_chuc_vu.getValue();

        if (utilityFunctions.isEmptyString(s_nguoi_ql)) {
            is_nguoi_ql_error = true;
        }

        if (utilityFunctions.isEmptyString(s_chuc_vu)) {
            is_chuc_vu_error = true;
        }

        if (utilityFunctions.isEmptyString(s_ten)) {
            text_error_validator_name.setText("Tên đang bị bỏ trống");

            is_ten_error = true;
        } else {
            set_Text_Visible(text_error_validator_name, false);
        }

        if (utilityFunctions.isEmptyString(s_sdt)) {
            text_error_validator_sdt.setText("Số điện thoại đang bị bỏ trống");
            is_sdt_error = true;

        } else {
            if (!utilityFunctions.isPhoneNumber(s_sdt)) {
                text_error_validator_sdt.setText("Sai định dạng số điện thoại");
                is_sdt_error = true;
            } else {
                set_Text_Visible(text_error_validator_sdt, false);
            }

        }


        if (utilityFunctions.isEmptyString(s_luong)) {
            text_error_validator_luong.setText("Lương đang bị bỏ trống");
            is_luong_error = true;

        } else {
            if (!utilityFunctions.isNumericString(s_luong)) {
                text_error_validator_luong.setText("Sai định dạng lương");
                is_luong_error = true;
            } else {
                set_Text_Visible(text_error_validator_luong, false);

            }

        }

        set_Text_Visible(text_error_validator_name, is_ten_error);
        set_Text_Visible(text_error_validator_sdt, is_sdt_error);
        set_Text_Visible(text_error_validator_luong, is_luong_error);
        set_Text_Visible(text_error_validator_chuc_vu, is_chuc_vu_error);
        set_Text_Visible(text_error_validator_nguoi_ql, is_nguoi_ql_error);


        return is_ten_error || is_luong_error || is_sdt_error;
    }

    HashSet<Employee> ds_nguoi_quan_ly;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        DBHandler db = new DBHandler();
        ObservableList<Employee> employeeList = FXCollections.observableArrayList();
        UtilityFunctions uf = new UtilityFunctions();

        choicebox_chuc_vu.getItems().addAll("Đầu bếp", "Quản lý", "Thu ngân");

        set_Text_Visible(text_error_validator_luong, false);
        set_Text_Visible(text_error_validator_name, false);
        set_Text_Visible(text_error_validator_chuc_vu, false);
        set_Text_Visible(text_error_validator_nguoi_ql, false);
        set_Text_Visible(text_error_validator_sdt, false);

        button_submit.setDisable(true);


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
            textfield_delete_e.setVisible(false);
            textfield_delete_e.setManaged(false);
            button_submit.setDisable(false);

            text_ng_quan_ly.setVisible(true);
            text_ng_quan_ly.setManaged(true);

            choicebox_nguoi_quan_ly.setVisible(true);
            choicebox_nguoi_quan_ly.setManaged(true);
        });

        button_submit.setOnMouseClicked(e -> {
            boolean is_error = check_is_error();

            if (!is_error && rule_button_submit.equals("add")) {
                System.out.println("Thêm nhân viên");

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
                    set_Text_Visible(text_ng_quan_ly,true);

                    textfield_delete_e.setVisible(true);
                    textfield_delete_e.setManaged(true);


                    if (employee.getChuc_vu().equals("Quản lý")) {
                        choicebox_nguoi_quan_ly.setVisible(false);
                        choicebox_nguoi_quan_ly.setManaged(false);
                        set_Text_Visible(text_ng_quan_ly,false);


                    } else {

                        choicebox_nguoi_quan_ly.setVisible(true);
                        choicebox_nguoi_quan_ly.setManaged(true);
                        choicebox_nguoi_quan_ly.setValue(employee.getNguoi_quan_ly());
                    }


                    choicebox_chuc_vu.setValue(employee.getChuc_vu());
                }
            }

        });


    }
}
