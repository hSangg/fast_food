package com.fast_food.demo;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import model.ItemTopSeller;
import utils.DBHandler;
import javafx.util.Pair;
import java.util.ArrayList;
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
    @FXML
    private Label label_tong_thu;
    @FXML
    private Label label_tong_chi;

    DBHandler dbh = new DBHandler();

    HashSet<ItemTopSeller> itsl = new HashSet<>();



    class ItemBoxInfo implements Comparable<ItemBoxInfo> {
        private HBox itemBox;
        private int sl;

        public ItemBoxInfo(HBox itemBox, int sl) {
            this.itemBox = itemBox;
            this.sl = sl;
        }

        public HBox getItemBox() {
            return itemBox;
        }

        public int getSl() {
            return sl;
        }

        @Override
        public int compareTo(ItemBoxInfo other) {
            // Sắp xếp giảm dần theo sl
            return Integer.compare(other.getSl(), this.sl);
        }
    }
    public void render_top_seller(int num) {
        // RENDER BẢNG BEST SELLER
        vbox_top_seller_layout.getChildren().clear();
        try {
            List<ItemTopSeller> x = dbh.getDemoSellerFollowingNum(num);



            for (ItemTopSeller itemTopSeller : x) {
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("/com/fast_food/demo/AdminTopSeller.fxml"));
                    HBox itemBox = fxmlLoader.load();
                    AdminTopSellerController controller = fxmlLoader.getController();
                    controller.setData(itemTopSeller.getTenMon(), String.valueOf(itemTopSeller.getTongSl()), itemTopSeller.getHinhAnh());
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

        double[] revenueData = new double[12];
        try {
            dbh.getYearRevenu(revenueData);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        for (int i = 0; i < revenueData.length; i++) {
            series.getData().add(new XYChart.Data(String.valueOf(i + 1), revenueData[i]));
        }

        chart_doanh_thu.getData().add(series);


        // DUNG DE HIEN DU LIEU CU THE KHI HOVER CHUOT VAO DUONG LINE
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
        // -----------------------------------------------------------------

        // RENDER CHART DOANH SỐ THU/CHI

        XYChart.Series line_thu = new XYChart.Series<>();
        ArrayList<Pair<Integer,Integer>> thu = new ArrayList<>();
        int tongThu;
        try {
            tongThu = dbh.getTongThu(thu);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        for(Pair<Integer,Integer> x : thu){
            int thang = x.getKey();
            int value = x.getValue();
            line_thu.getData().add(new XYChart.Data(String.valueOf(thang),value));
        }
        label_tong_thu.setText(String.valueOf(tongThu));
        ArrayList<Pair<Integer,Integer>> chi = new ArrayList<>();
        int tongChi;
        try {
            tongChi= dbh.getTongChi(chi);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        XYChart.Series line_chi = new XYChart.Series<>();
        for(Pair<Integer,Integer> y : chi){
            int thang = y.getKey();
            int value = y.getValue();
            line_chi.getData().add(new XYChart.Data(String.valueOf(thang),value));
        }
        label_tong_chi.setText(String.valueOf(tongChi));


        chart_doanh_thu_thang.getData().add(line_thu);
        chart_doanh_thu_thang.getData().add(line_chi);


        //CSS CHO CHART DOANH THU

        line_chi.getNode().lookup(".chart-series-line").setStyle("-fx-stroke: #ff8110;");
        line_thu.getNode().lookup(".chart-series-line").setStyle("-fx-stroke: #316aff;");

        String chartStyle = "-fx-background-color: #316aff, white;";
        String legendStyle = "-fx-text-fill: #316aff;";
        chart_doanh_thu_thang.setStyle(chartStyle);
        chart_doanh_thu_thang.lookup(".chart-legend").setStyle(legendStyle);


        // ---------------------------------

        // RENDER BẢNG BEST SELLER
        render_top_seller(10);
        // ---------------------------------
        // XỬ LÝ SỐ LƯỢNG MÓN ĂN BEST SELLER CẦN RENDER
        num_best_seller_show.setOnAction(event -> {
            String enteredText = num_best_seller_show.getText();
            render_top_seller(Integer.parseInt(enteredText));
        });

        // ---------------------------------
        // XỬ LÝ CHART TỔNG SỐ LƯỢNG ĐƠN HÀNG
        XYChart.Series series_dh = new XYChart.Series();
        series_dh.setName("Số đơn hàng theo ngày");
        ArrayList<Pair<Integer,Integer>> xy = new ArrayList<>();
        try {
            dbh.getTongDon(xy);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        for(Pair<Integer,Integer> pair: xy){
            int x=pair.getKey();
            int y=pair.getValue();
            series_dh.getData().add(new XYChart.Data(String.valueOf(x),y));
        }
        chart_tong_so_don_hang.getData().add(series_dh);


    }

}

