package utils;

import model.Employee;
import model.Order;

import java.sql.*;
import java.util.HashSet;

public class DBHandler {
    public Connection conn;
     public DBHandler() {
        try{
            Class.forName("oracle.jdbc.driver.OracleDriver");
            String url = "jdbc:oracle:thin:@localhost:1521:ORCL";
            String user = "SYSTEM";
            String pass = "1652003Sang_";

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

}
