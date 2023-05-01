
    package com.fast_food.demo;

        import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.NL;
import model.Order;
import utils.DBHandler;

        import java.math.BigDecimal;
import java.net.URL;
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.sql.Types;
public class DailyReportController implements Initializable {

            @FXML
    private TextField Sumchi;
    @FXML
    private  TextField Sumthu;
    @FXML
    private TableView<NL> table_Nguyen_lieu;

    @FXML
    private TableColumn<?,?>ten_nl;
    @FXML
    private TableColumn<?,?>donvi;
    @FXML
    private TableColumn<?,?>soluong;
    @FXML
    private TableColumn<?,?>gia_nl;

            @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
                    ObservableList<NL> NL_list = FXCollections.observableArrayList();
                    DBHandler db=new DBHandler();
                    try {
                            HashSet<NL> result = db.getAllNl();
                            for (NL n : result) {
                                    NL_list.add(n);
                                }
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    Sumthu.setEditable(false);
                    Sumchi.setEditable(false);
                    ten_nl.setCellValueFactory(new PropertyValueFactory<>("ten"));
                    donvi.setCellValueFactory(new PropertyValueFactory<>("donvi"));
                    soluong.setCellValueFactory(new PropertyValueFactory<>("soluongtrongkho"));
                    gia_nl.setCellValueFactory(new PropertyValueFactory<>("gianl"));
                    table_Nguyen_lieu.setItems(NL_list);
                    //goi function sumthu_ngay
                            String sql = "{? = call SUMTHU_NGAY()}";
                        try{
                                CallableStatement stm = db.conn.prepareCall(sql);
                                stm.registerOutParameter(1,Types.NUMERIC);
                                stm.execute();
                                BigDecimal result1=stm.getBigDecimal(1);
                                Sumthu.setText(result1.toString());
                            }catch (Exception e){
                                Sumthu.setText("No order have been made!");
                            }
                    //goi function sum chi ngay
                            String sql1 = "{? = call SUMCHI_NGAY()}";
                    try{
                            CallableStatement stm1 = db.conn.prepareCall(sql1);
                            stm1.registerOutParameter(1,Types.NUMERIC);
                            stm1.execute();
                            BigDecimal result2=stm1.getBigDecimal(1);
                            Sumchi.setText(result2.toString());
                        }catch (Exception e){
                            Sumchi.setText("No order have been made!");
                        }
                }
}

