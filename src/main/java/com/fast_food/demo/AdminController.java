package com.fast_food.demo;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import model.ItemTopSeller;
import utils.DBHandler;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class AdminController implements Initializable {
    @FXML
    private LineChart<?, ?> chart_doanh_thu;

    @FXML
    private LineChart<?, ?> chart_doanh_thu_thang;

    @FXML
    private BarChart<?, ?> chart_tong_so_don_hang;

    @FXML
    private TextField num_best_seller_show;

    @FXML
    private VBox vbox_top_seller_layout;


    DBHandler dbh = new DBHandler();

    HashSet<ItemTopSeller> itsl = new HashSet<>();


    public void render_top_seller(int num) {
        // RENDER BẢNG BEST SELLER
        vbox_top_seller_layout.getChildren().clear();
        try {
            List<ItemTopSeller> x = dbh.getDemoSellerFollowingNum(num);

            Collections.sort(x, Comparator.comparingInt(ItemTopSeller::getGia).reversed());

            for (ItemTopSeller itemTopSeller : x) {
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("/com/fast_food/demo/AdminTopSeller.fxml"));
                    HBox itemBox = fxmlLoader.load();
                    AdminTopSellerController controller = fxmlLoader.getController();
                    controller.setData(itemTopSeller.getTenMon(), String.valueOf(itemTopSeller.getGia()), itemTopSeller.getHinhAnh());
                    vbox_top_seller_layout.getChildren().add(itemBox);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // -------------------------------
        // RENDER CHART DOANH THU TRONG NĂM

        XYChart.Series series = new XYChart.Series();
        series.setName("Doanh thu");

        double[] revenueData = {1000.0, 1200.0, 1300.0, 1500.0, 1400.0, 1600.0, 1800.0, 1900.0, 1700.0, 2000.0, 2100.0, 2200.0};

        for (int i = 0; i < revenueData.length; i++) {
            series.getData().add(new XYChart.Data(String.valueOf(i + 1), revenueData[i]));
        }

        chart_doanh_thu.getData().add(series);



        Tooltip tooltip = new Tooltip();
        tooltip.setShowDelay(Duration.ZERO);
        tooltip.setHideDelay(Duration.ZERO);

        tooltip.setStyle("-fx-font-size: 14px;");


        for (XYChart.Series<?, ?> series_ : chart_doanh_thu.getData()) {
            for (XYChart.Data<?, ?> data : series_.getData()) {
                Node node = data.getNode();
                String value = String.format("%.2f", data.getYValue());

                Tooltip.install(node, new Tooltip(value));

                node.setOnMouseEntered(event -> {
                    node.getStyleClass().add("chart-line-hover");
                    tooltip.setText(value);
                    tooltip.show(node, event.getScreenX(), event.getScreenY() + 10);
                });

                node.setOnMouseExited(event -> {
                    node.getStyleClass().remove("chart-line-hover");
                    tooltip.hide();
                });
            }
        }
        // ---------------------------------
        // RENDER CHART DOANH SỐ THU/CHI

        XYChart.Series line_thu = new XYChart.Series<>();
        line_thu.getData().add(new XYChart.Data("1", 132));
        line_thu.getData().add(new XYChart.Data("2", 341));
        line_thu.getData().add(new XYChart.Data("3", 452));


        XYChart.Series line_chi = new XYChart.Series<>();
        line_chi.getData().add(new XYChart.Data("1", 462));
        line_chi.getData().add(new XYChart.Data("2", 324));
        line_chi.getData().add(new XYChart.Data("3", 232));


        chart_doanh_thu_thang.getData().add(line_thu);
        chart_doanh_thu_thang.getData().add(line_chi);

        line_chi.getNode().lookup(".chart-series-line").setStyle("-fx-stroke: #ff8110;");
        line_thu.getNode().lookup(".chart-series-line").setStyle("-fx-stroke: #316aff;");


        String chartStyle = "-fx-background-color: #316aff, white;";
        String legendStyle = "-fx-text-fill: #316aff;";
        chart_doanh_thu_thang.setStyle(chartStyle);
        chart_doanh_thu_thang.lookup(".chart-legend").setStyle(legendStyle);


        // ---------------------------------

        // RENDER BẢNG BEST SELLER
        try {
            itsl = dbh.getDemoSeller();
            for (ItemTopSeller itemTopSeller : itsl) {
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("/com/fast_food/demo/AdminTopSeller.fxml"));
                    HBox itemBox = fxmlLoader.load();
                    AdminTopSellerController controller = fxmlLoader.getController();
                    controller.setData(itemTopSeller.getTenMon(), String.valueOf(itemTopSeller.getGia()), itemTopSeller.getHinhAnh());
                    vbox_top_seller_layout.getChildren().add(itemBox);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // ---------------------------------
        // XỬ LÝ SỐ LƯỢNG MÓN ĂN CẦN RENDER
        num_best_seller_show.setOnAction(event -> {
            String enteredText = num_best_seller_show.getText();
            render_top_seller(Integer.parseInt(enteredText));

        });

        // ---------------------------------
        // XỬ LÝ CHART TỔNG SỐ LƯỢNG ĐƠN HÀNG
        XYChart.Series series_dh = new XYChart.Series();
        series_dh.setName("Số đơn hàng theo ngày");

        series_dh.getData().add(new XYChart.Data("1", 10));
        series_dh.getData().add(new XYChart.Data("2", 15));
        series_dh.getData().add(new XYChart.Data("3", 8));

        chart_tong_so_don_hang.getData().add(series_dh);


    }

}

