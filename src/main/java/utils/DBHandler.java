package utils;

import com.fast_food.demo.ManagementController;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Account;
import model.Employee;
import model.Order;
import org.w3c.dom.Text;

import java.io.IOException;
import java.sql.*;
import java.util.HashSet;

public class DBHandler {
    public Connection conn;
     public DBHandler() {
        try{
            Class.forName("oracle.jdbc.driver.OracleDriver");
            String url = "jdbc:oracle:thin:@192.168.56.1:1521:ORCL";
            String user = "SYSTEM";
            String pass = "thanhcong";

            this.conn = DriverManager.getConnection(url, user, pass);

        } catch(Exception e) {e.printStackTrace();}
    }

    public HashSet<Employee> getAllEmployees() throws SQLException {
        Statement sm = conn.createStatement();
        ResultSet rs = sm.executeQuery("SELECT X1.ID, X1.TEN, X1.CHUC_VU, X1.SO_DIEN_THOAI, X1.LUONG, NVL(X2.TEN, '') AS NGUOI_QUAN_LY, x2.ID AS ID_QUAN_LY FROM NHAN_VIEN X1 LEFT JOIN NHAN_VIEN X2 ON X1.ID_QUAN_LY = X2.ID");
        HashSet<Employee> result = new HashSet<>();


        while (rs.next()) {
            int id = rs.getInt("ID");
            String ten = rs.getString("TEN");
            String chuc_vu = rs.getString("CHUC_VU");
            String sdt = rs.getString("SO_DIEN_THOAI");
            int luong = rs.getInt("LUONG");
            String nguoi_quan_ly = rs.getString("NGUOI_QUAN_LY");
            int id_quan_ly = rs.getInt("ID_QUAN_LY");

            result.add(new Employee(id, sdt, id_quan_ly, chuc_vu, ten, luong, nguoi_quan_ly ));

        }
        conn.close();
        return result;
    }

    public HashSet<Order> getAllOrders() throws SQLException {
        Statement sm = conn.createStatement();
        ResultSet rs = sm.executeQuery("");
        HashSet<Order> result = new HashSet<>();


        while (rs.next()) {
            int id = rs.getInt("ID");
            String ten = rs.getString("TEN");
            String chuc_vu = rs.getString("CHUC_VU");
            String sdt = rs.getString("SO_DIEN_THOAI");
            int luong = rs.getInt("LUONG");
            String nguoi_quan_ly = rs.getString("NGUOI_QUAN_LY");
            int id_quan_ly = rs.getInt("ID_QUAN_LY");

//            result.add(new Employee(id, sdt, id_quan_ly, chuc_vu, ten, luong, nguoi_quan_ly ));

        }
        conn.close();
        return result;
    }
    public HashSet<Account> LoginUser() throws SQLException{
        Statement sm = conn.createStatement();
        ResultSet rs = sm.executeQuery("SELECT USERNAME, PASSWORD FROM TAIKHOAN_NV");
        HashSet<Account> result = new HashSet<>();
        while(rs.next()){
            String name = rs.getString("USERNAME");
            String password = rs.getString("PASSWORD");
            result.add(new Account(name,password));
        }
        return result;
    }
}
