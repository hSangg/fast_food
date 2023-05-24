package com.fast_food.demo;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Rectangle;
import model.Ingredient;
import model.Material;
import model.MenuItem;
import utils.DBHandler;

import java.io.ByteArrayInputStream;
import java.net.URL;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class MenuManagementController implements Initializable {
    @FXML
    private HBox button_chonanh;

    @FXML
    private HBox button_hoantat;

    @FXML
    private HBox button_themmon;

    @FXML
    private HBox button_themnguyenlieu;

    @FXML
    private HBox button_xoanguyenlieu;

    @FXML
    private ChoiceBox<String> choicebox_loai;

    @FXML
    private ChoiceBox<String> choicebox_tennguyenlieu;

    @FXML
    private Label label_mode;

    @FXML
    private TableView<MenuItem> table_mon_an;

    @FXML
    private TableView<Material> table_nguyen_lieu;

    @FXML
    private TableColumn<MenuItem, Image> tablecolumn_anh;

    @FXML
    private TableColumn<?, ?> tablecolumn_dv;

    @FXML
    private TableColumn<?, ?> tablecolumn_slnl;


    @FXML
    private TableColumn<?, ?> tablecolumn_tennl;

    @FXML
    private TableColumn<?, ?> tablecolumn_tenmon;

    @FXML
    private TextField text_field_gia;

    @FXML
    private TextField textfield_donvi;

    @FXML
    private TextField textfield_mota;

    @FXML
    private TextField textfield_soluong;

    @FXML
    private HBox button_xoamon;

    @FXML
    private TextField textfield_tenmon;

    public String mode = "";
    /*
    + EDIT_MON_AN (không sửa nguyên liệu)
    + EDIT_MON_AN_NL (có sửa nguyên liệu)
    + DELETE_NGUYEN_LIEU (xóa nguyên liệu)
    + ADD_NGUYEN_LIEU
    + ADD_MON_AN
    + DELETE_MON_AN
    *
    */

    HashSet<MenuItem> menuList;

    List<Material> materialList;
    List<Ingredient> ingredientList;
    List<String> nameIngredientList;

    DBHandler db = new DBHandler();

    public void clearChiTietMonAn() {
        textfield_mota.clear();
        textfield_tenmon.clear();
        choicebox_loai.setValue(null);
        text_field_gia.clear();
    }

    public void clearChiTietNguyenLieu() {
        choicebox_tennguyenlieu.setValue(null);
        textfield_soluong.clear();
        textfield_donvi.clear();
    }

    public void isDisableChiTietMonAn(boolean x) {
        textfield_mota.setDisable(x);
        textfield_tenmon.setDisable(x);
        choicebox_loai.setDisable(x);
        text_field_gia.setDisable(x);
    }

    public void isDisableChiTietNguyenLieu(boolean x) {
        choicebox_tennguyenlieu.setDisable(x);
        textfield_soluong.setDisable(x);
        textfield_donvi.setDisable(x);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            menuList = db.getAllMenuItems();
            //-------RENDER DEFAULT VALUE -----------------
            choicebox_loai.getItems().add("do an");
            choicebox_loai.getItems().add("do uong");

            ingredientList = db.getAllIngredients();


            for (Ingredient x : ingredientList) {
                String tenNguyenLieu = x.getTen();
                if (!choicebox_tennguyenlieu.getItems().contains(tenNguyenLieu)) {
                    choicebox_tennguyenlieu.getItems().add(tenNguyenLieu);
                }
            }
            isDisableChiTietMonAn(true);
            isDisableChiTietNguyenLieu(true);
            button_xoanguyenlieu.setDisable(true);
            button_xoamon.setDisable(true);
            button_themnguyenlieu.setDisable(true);


            //-------RENDER DEFAULT VALUE -----------------


            // ------XU LY BUTTON-------------------
            button_themnguyenlieu.setOnMouseClicked(e -> {
                clearChiTietNguyenLieu();
                this.mode = "ADD_NGUYEN_LIEU";
            });

            button_themmon.setOnMouseClicked(e -> {
                isDisableChiTietMonAn(false);
                clearChiTietMonAn();
                clearChiTietNguyenLieu();
                table_nguyen_lieu.getItems().clear();
                this.mode = "ADD_MON_AN";
            });

            button_hoantat.setOnMouseClicked(e -> {
                System.out.println(this.mode);
            });

            button_xoanguyenlieu.setOnMouseClicked(e -> {
                this.mode = "DELETE_NGUYEN_LIEU";
            });

            // ------XU LY BUTTON-------------------


            // ------RENDER TABLE MON AN-------------------
            ObservableList<MenuItem> monan = FXCollections.observableArrayList();
            tablecolumn_anh.setCellFactory(column -> new TableCell<MenuItem, Image>() {
                private final ImageView imageView = new ImageView();

                @Override
                protected void updateItem(Image image, boolean empty) {
                    super.updateItem(image, empty);
                    if (empty || image == null) {
                        setGraphic(null);
                    } else {

                        imageView.setImage(image);
                        imageView.setFitWidth(50);  // Set the desired width of the image
                        imageView.setFitHeight(50); // Set the desired height of the image

                        Rectangle clip = new Rectangle(imageView.getFitWidth(), imageView.getFitHeight());
                        clip.setArcWidth(40);
                        clip.setArcHeight(40);
                        imageView.setClip(clip);


                        setGraphic(imageView);
                    }
                }
            });

            tablecolumn_anh.setCellValueFactory(cellData -> {
                byte[] imageData = cellData.getValue().getImage(); // Assuming "getHinhAnh()" returns the byte array
                if (imageData != null) {
                    ByteArrayInputStream bis = new ByteArrayInputStream(imageData);
                    Image image = new Image(bis);
                    return new SimpleObjectProperty<>(image);
                } else {
                    return new SimpleObjectProperty<>(null);
                }
            });

            tablecolumn_tenmon.setCellValueFactory(new PropertyValueFactory<>("name"));
            for (MenuItem x : menuList) {
                monan.add(x);
            }

            table_mon_an.setItems(monan);
            // ------RENDER TABLE MON AN-------------------
            table_mon_an.setRowFactory(tv -> {


                TableRow<MenuItem> row = new TableRow<>();
                row.setOnMouseClicked(event -> {
                    if (event.getClickCount() == 2 && !row.isEmpty()) {
                        MenuItem selectedItem = row.getItem();
                        //-----------XU LY KHI DOUBLE CLICK VAO 1 HANG CUA TABLE MON AN
                        isDisableChiTietMonAn(false);
                        button_xoamon.setDisable(false);
                        button_themnguyenlieu.setDisable(false);
                        this.mode = "EDIT_MON_AN";

                        textfield_tenmon.setText(selectedItem.getName());
                        text_field_gia.setText(String.valueOf(selectedItem.getPrice()));
                        textfield_mota.setText(selectedItem.getDescription());
                        choicebox_loai.setValue(selectedItem.getCategory());

                        //-----------THEM NGUYEN LIEU TUONG UNG VOI MON
                        ObservableList<Material> nguyenLieu = FXCollections.observableArrayList();

                        try {
                            materialList = db.getMaterialsForDish(selectedItem.getId());
                            for (Material x : materialList) {
                                nguyenLieu.add(x);
                            }
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }


                        //-----------THEM NGUYEN LIEU TUONG UNG VOI MON
                        tablecolumn_tennl.setCellValueFactory(new PropertyValueFactory<>("name"));
                        tablecolumn_dv.setCellValueFactory(new PropertyValueFactory<>("unit"));
                        tablecolumn_slnl.setCellValueFactory(new PropertyValueFactory<>("quantity"));

                        table_nguyen_lieu.setItems(nguyenLieu);

                        //-----------XU LY KHI DOUBLE CLICK VAO 1 HANG CUA TABLE MON AN

                        this.mode = "EDIT_MON_AN";
                    }
                });
                return row;
            });

            table_nguyen_lieu.setRowFactory(tv -> {

                TableRow<Material> row = new TableRow<>();
                row.setOnMouseClicked(event -> {
                    if (event.getClickCount() == 2 && !row.isEmpty()) {
                        Material selectedItem = row.getItem();
                        isDisableChiTietNguyenLieu(false);
                        button_xoanguyenlieu.setDisable(false);
                        this.mode = "EDIT_MON_AN_NL";


                        //---------RENDER VALUE-------------
                        textfield_donvi.setText(selectedItem.getUnit());
                        textfield_soluong.setText(String.valueOf(selectedItem.getQuantity()));
                        choicebox_tennguyenlieu.setValue(selectedItem.getName());
                        //---------RENDER VALUE-------------
                    }
                });
                return row;

            });


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
