package utils;

import model.Employee;
import model.FastFood;
import model.Order;

import java.sql.*;
import java.time.LocalDateTime;
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

        return result;
    }

    public HashSet<Order> getAllOrders() throws SQLException {
        Statement sm = conn.createStatement();
        ResultSet rs = sm.executeQuery("SELECT * FROM XU_LY_DON_HANG");
        HashSet<Order> result = new HashSet<>();


        while (rs.next()) {
            int id = rs.getInt("ID");
            int customerId = rs.getInt("ID_KH");
            String customerName = rs.getString("TEN_KH");
            int cashierId = rs.getInt("ID_TN");
            String cashierName = rs.getString("TEN_TN");
            int addressId = rs.getInt("DIA_CHI_ID");
            String address = rs.getString("DIA_CHI");
            int numTables = rs.getInt("SO_BAN_TAO_DON");
            int total = rs.getInt("TONG_TIEN");
            String paymentMethod = rs.getString("HINH_THUC_THANH_TOAN");
            String status = rs.getString("TRANG_THAI");
            int isOnline = rs.getInt("DAT_ONLINE");
            Date orderDate = rs.getDate("NGAY_DAT");
            String notes = rs.getString("GHI_CHU");

            // create a new Order object using the retrieved data
            Order order = new Order(id, customerId, customerName, cashierId, cashierName,
                    addressId, address, numTables, total, paymentMethod,
                    status, isOnline, orderDate, notes);

            result.add(order);

        }

        return result;
    }

    public HashSet<FastFood> getFastFoodByIdOrder(int order_id) throws SQLException {
        Statement sm = conn.createStatement();
        ResultSet rs = sm.executeQuery("SELECT MON_AN.ID, MON_AN.TEN_MON, MON_AN.MO_TA, MON_AN.LOAI, MON_AN.HINH_ANH, CHITIET_DON.SOLUONG" +
                " FROM MON_AN" +
                " JOIN CHITIET_DON ON MON_AN.ID = CHITIET_DON.ID_MON" +
                " WHERE CHITIET_DON.ID_DON = " + order_id);
        HashSet<FastFood> result = new HashSet<>();
        while (rs.next()) {
            int id = rs.getInt("ID");
            String tenMon = rs.getString("TEN_MON");
            String moTa = rs.getString("MO_TA");
            String loai = rs.getString("LOAI");
            byte[] hinhAnh = rs.getBytes("HINH_ANH");
            int soLuong = rs.getInt("SOLUONG");
            FastFood fastFood = new FastFood(id, tenMon, moTa, loai, hinhAnh, soLuong);
            result.add(fastFood);
        }

        return result;
    }

}
