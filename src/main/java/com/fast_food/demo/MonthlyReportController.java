package com.fast_food.demo;

        import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.NL;
import model.Report;
import utils.DBHandler;

        import java.net.URL;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.ResourceBundle;

        public class MonthlyReportController implements Initializable {
    @FXML
    CategoryAxis thang_x;
    @FXML
    NumberAxis tien_y;
    @FXML
    BarChart<String,Integer> bieudo;
    @FXML
    private TableView<Report> report_table;
    @FXML
    private TableColumn<?, ?> month;
    @FXML
    private TableColumn<?,?> revenue;
    @FXML
    private TableColumn<?,?> cost;

            @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

                        DBHandler db = new DBHandler();
                ObservableList<Report> List = FXCollections.observableArrayList();
                try {
                        HashSet<Report> result = db.getDataFromFunction();
                        for (Report n : result) {
                                List.add(n);
                            }
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                month.setCellValueFactory(new PropertyValueFactory<>("thang"));
                revenue.setCellValueFactory(new PropertyValueFactory<>("doanhthu"));
                cost.setCellValueFactory(new PropertyValueFactory<>("kinhphi"));
                report_table.setItems(List);
                //ve bieu do
                        int x =0;
                int y=0;
                XYChart.Series<String, Integer> series1 = new XYChart.Series<>();
                series1.setName("Doanh thu");
                while(x<List.size()) {
                        series1.getData().add(new XYChart.Data<>("Thang " + List.get(x).getThang(),(int)List.get(x).getDoanhthu()));

                                x++;
                    }

                        XYChart.Series<String, Integer> series2 = new XYChart.Series<>();
                series2.setName("Kinh phi");
                while(y<List.size()) {
                        series2.getData().add(new XYChart.Data<>("Thang "+  List.get(y).getThang(),(int)List.get(y).getKinhphi()));

                                y++;
                    }
                bieudo.getData().addAll(series1,series2);

                    }

                }