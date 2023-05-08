package com.fast_food.demo;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.stream.Collectors;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import model.MenuItem;
import model.OrderDetail;
import utils.DBHandler;
import utils.UtilityFunctions;


public class DashboardController implements Initializable, Callbacks {

    @FXML
    private GridPane menu_layout;

    @FXML
    private VBox order_layout;

    @FXML
    private VBox bt_drink;


    @FXML
    private Text total;

    @FXML
    private VBox bt_food;

    @FXML
    private Text total_bill;

    public String render_type;

    public HashSet<OrderDetail> order_list;

    public HashSet<MenuItem> menu_list;

    public DBHandler db;

    public int totalBill = 0;

    public double km = 0.1;

    public UtilityFunctions uf = new UtilityFunctions();

    public DashboardController() throws SQLException {
        this.render_type = "do an";
        order_list = new HashSet<OrderDetail>();
        db = new DBHandler();
        menu_list = db.getAllMenuItems();
    }

    public void updateTotalBill() {
        this.totalBill = 0;
        for (OrderDetail od : this.order_list) {
            this.totalBill += od.getCount() * od.getOrder().getPrice();
        }
        total_bill.setText("$" + String.valueOf(totalBill));

        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        this.total.setText("$" + decimalFormat.format(this.totalBill * (1 - this.km)));
    }

    public void render_order_list(OrderDetail orders) throws IOException {

        FXMLLoader fxmlLoad = new FXMLLoader();
        fxmlLoad.setLocation(getClass().getResource("/com/fast_food/demo/OrderItem.fxml"));

        HBox ItemBox = fxmlLoad.load();
        OrderItemOnDashboardController odc = fxmlLoad.getController();

        odc.setCallbacks(this);


        odc.updateDisplay(orders.getOrder().getName(), orders.getOrder().getPrice(), orders.getOrder().getImage(), orders.getCount());


        order_layout.getChildren().add(ItemBox);

    }

    public void renderOrder(OrderDetail od) throws IOException {
        order_layout.getChildren().removeIf(node -> {
            Label mylb = (Label) node.lookup("#name");
            return mylb.getText().equals(od.getOrder().getName());
        });

        FXMLLoader fxmlLoad = new FXMLLoader();
        fxmlLoad.setLocation(getClass().getResource("/com/fast_food/demo/OrderItem.fxml"));

        HBox ItemBox = fxmlLoad.load();
        OrderItemOnDashboardController odc = fxmlLoad.getController();
        odc.setCallbacks(this);
        odc.updateDisplay(od.getOrder().getName(), od.getOrder().getPrice(), od.getOrder().getImage(), od.getCount());

        order_layout.getChildren().add(ItemBox);
    }

    public boolean is_contain(MenuItem item) {
        boolean result = false;
        for (OrderDetail i : this.order_list) {
            if (i.getOrder().getName().equals(item.getName())) result = true;
        }
        return result;
    }


    public void render_menu_list(HashSet<MenuItem> menu) {
        menu_layout.getChildren().clear();
        int column = 0;
        int row = 1;

        try {

            for (MenuItem item : menu) {
                FXMLLoader fxmlLoad = new FXMLLoader();
                fxmlLoad.setLocation(getClass().getResource("/com/fast_food/demo/MenuItem.fxml"));

                VBox ItemBox = fxmlLoad.load();
                MenuItemController mic = fxmlLoad.getController();

                ItemBox.setOnMouseClicked(e -> {
                    if (this.is_contain(item)) {

                        order_list.forEach(orderDetail -> {
                            if (orderDetail.getOrder().getName().equals(item.getName())) {
                                orderDetail.setCount(orderDetail.getCount() + 1);
                                try {
                                    renderOrder(orderDetail);
                                } catch (IOException ex) {
                                    throw new RuntimeException(ex);
                                }
                            }
                        });


                    } else {
                        order_list.add(new OrderDetail(item, 1));
                        try {
                            render_order_list(new OrderDetail(item, 1));

                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    }

                    updateTotalBill();

                });

                mic.setData(item);

                if (column == 3) {
                    column = 0;
                    ++row;
                }
                menu_layout.add(ItemBox, column++, row);
                GridPane.setMargin(ItemBox, new Insets(9));

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.render_menu_list(menu_list);

        bt_drink.setOnMouseClicked(e -> {

            this.render_menu_list(menu_list.stream().filter(x -> {
                return x.getCategory().equals("do uong");
            }).collect(Collectors.toCollection(HashSet::new)));
        });
        bt_food.setOnMouseClicked(e -> {

            this.render_menu_list(menu_list.stream().filter(x -> {
                return x.getCategory().equals("do an");
            }).collect(Collectors.toCollection(HashSet::new)));
        });


    }

    @Override
    public void updateCounter(String ten_mon, int count) {
        this.order_list.forEach(x -> {
            if (x.getOrder().getName().equals(ten_mon)) {
                x.setCount(count);
            }
        });
        updateTotalBill();

        System.out.println("--------------ORDER--------------");
        for (OrderDetail x : this.order_list) {

            System.out.println(x.getOrder().getName() + " co so luong la:  " + x.getCount());
        }
        System.out.println("---------------------------------");


    }

    @Override
    public void deleteItem(String ten_mon, HBox box) {
        this.order_list = this.order_list.stream().filter(mon_an -> {
            return !mon_an.getOrder().getName().equals(ten_mon);
        }).collect(Collectors.toCollection(HashSet::new));
        uf.setVisibleNode(box, false);
        updateTotalBill();
    }
}