package utils;

import model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class DBHandler {
    public Connection conn;
    public int trang_thai_dang_nhap;

    public DBHandler() {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            String url = "jdbc:oracle:thin:@localhost:1521:ORCL";
            String user = "SYSTEM";
            String pass = "1652003Sang_";

            this.conn = DriverManager.getConnection(url, user, pass);

        } catch (Exception e) {
            e.printStackTrace();
        }
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

            result.add(new Employee(id, sdt, id_quan_ly, chuc_vu, ten, luong, nguoi_quan_ly));

        }

        return result;
    }

    public HashSet<Employee> getAllEmployeesFollowingName(String searchString) throws SQLException {
        Statement sm = conn.createStatement();
        String searchParam = "%" + searchString.toLowerCase() + "%";
        String query = "SELECT X1.ID, X1.TEN, X1.CHUC_VU, X1.SO_DIEN_THOAI, X1.LUONG, NVL(X2.TEN, '') AS NGUOI_QUAN_LY, x2.ID AS ID_QUAN_LY " +
                "FROM NHAN_VIEN X1 LEFT JOIN NHAN_VIEN X2 ON X1.ID_QUAN_LY = X2.ID " +
                "WHERE LOWER(X1.TEN) LIKE ?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setString(1, searchParam);
        ResultSet rs = ps.executeQuery();
        HashSet<Employee> result = new HashSet<>();

        while (rs.next()) {
            int id = rs.getInt("ID");
            String ten = rs.getString("TEN");
            String chuc_vu = rs.getString("CHUC_VU");
            String sdt = rs.getString("SO_DIEN_THOAI");
            int luong = rs.getInt("LUONG");
            String nguoi_quan_ly = rs.getString("NGUOI_QUAN_LY");
            int id_quan_ly = rs.getInt("ID_QUAN_LY");

            result.add(new Employee(id, sdt, id_quan_ly, chuc_vu, ten, luong, nguoi_quan_ly));
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

    public LogedInUser logIn(String username, String password) throws SQLException {
        Statement sm = conn.createStatement();
        ResultSet rs = sm.executeQuery("SELECT COUNT(*) AS COUNT FROM TAIKHOAN_NV WHERE USERNAME = '" + username + "'");
        int count = 0;

        if (rs.next()) {
            count = rs.getInt("COUNT");
        }

        if (count == 0) {
            // The username does not exist in the database
            return null;
        }

        // The username exists in the database, now check the password
        rs = sm.executeQuery("SELECT TEN, PASSWORD, CHUC_VU, NHAN_VIEN.ID as IDNHANVIEN, ID_QUAN_LY FROM TAIKHOAN_NV JOIN NHAN_VIEN ON TAIKHOAN_NV.ID_NV = NHAN_VIEN.ID WHERE USERNAME = '" + username + "'");
        LogedInUser logedReturn = null;

        if (rs.next()) {
            Account account = new Account(rs.getString("TEN"), rs.getString("PASSWORD"), rs.getString("CHUC_VU"), rs.getInt("IDNHANVIEN"), rs.getInt("ID_QUAN_LY"));
            if (account.getPassword().equals(password)) {
                logedReturn = new LogedInUser();
                logedReturn.setCurentAcc(account);
            }
        }

        return logedReturn;
    }

    public HashSet<MenuItem> getAllMenuItems() throws SQLException {
        Statement sm = conn.createStatement();
        ResultSet rs = sm.executeQuery("select ID, TEN_MON, MO_TA, LOAI, GIA, HINH_ANH from MON_AN");
        HashSet<MenuItem> result = new HashSet<MenuItem>();
        while (rs.next()) {
            int id = rs.getInt("ID");
            String tenMon = rs.getString("TEN_MON");
            String moTa = rs.getString("MO_TA");
            String loai = rs.getString("LOAI");
            int gia = rs.getInt("GIA");
            byte[] hinhAnh = rs.getBytes("HINH_ANH");
            MenuItem item = new MenuItem(id, tenMon, moTa, loai, gia, hinhAnh);
            result.add(item);
        }
        return result;
    }

    public HashSet<ItemTopSeller> getDemoSeller() throws SQLException {
        Statement sm = conn.createStatement();
        ResultSet rs = sm.executeQuery("SELECT TEN_MON, GIA, HINH_ANH FROM MON_AN FETCH FIRST 3 ROWS ONLY");
        HashSet<ItemTopSeller> result = new HashSet<>();

        while (rs.next()) {
            String tenMon = rs.getString("TEN_MON");
            int gia = rs.getInt("GIA");
            byte[] hinhAnh = rs.getBytes("HINH_ANH");

            ItemTopSeller item = new ItemTopSeller(tenMon, gia, hinhAnh);
            result.add(item);
        }

        return result;
    }

    public List<ItemTopSeller> getDemoSellerFollowingNum(int num) throws SQLException {
        Statement sm = conn.createStatement();
        ResultSet rs = sm.executeQuery("SELECT * FROM MON_AN ORDER BY GIA DESC FETCH FIRST " + num + " ROWS ONLY");
        List<ItemTopSeller> result = new ArrayList<>();

        while (rs.next()) {
            String tenMon = rs.getString("TEN_MON");
            int gia = rs.getInt("GIA");
            byte[] hinhAnh = rs.getBytes("HINH_ANH");

            ItemTopSeller item = new ItemTopSeller(tenMon, gia, hinhAnh);
            result.add(item);
        }

        return result;
    }

    public List<Ingredient> getAllIngredients() throws SQLException {
        List<Ingredient> ingredients = new ArrayList<>();
        String query = "SELECT * FROM NGUYEN_LIEU";
        try (Statement statement = conn.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                int id = resultSet.getInt("ID");
                String ten = resultSet.getString("TEN");
                String donVi = resultSet.getString("DON_VI");
                int soLuongTrongKho = resultSet.getInt("SO_LUONG_TRONG_KHO");
                int giaNL = resultSet.getInt("GIA_NL");

                Ingredient ingredient = new Ingredient(id, ten, donVi, soLuongTrongKho, giaNL);
                ingredients.add(ingredient);
            }
        }
        return ingredients;
    }


    public List<Material> getMaterialsForDish(int dishId) throws SQLException {
        List<Material> materials = new ArrayList<>();
        Statement sm = conn.createStatement();
        ResultSet resultSet = sm.executeQuery("SELECT NL.TEN, NL.DON_VI, NLM.SO_LUONG " +
                "FROM NGUYEN_LIEU NL " +
                "INNER JOIN NGUYEN_LIEU_MON_AN NLM ON NL.ID = NLM.ID_NL " +
                "WHERE NLM.ID_MON = " + dishId);

        while (resultSet.next()) {
            String name = resultSet.getString("TEN");
            String unit = resultSet.getString("DON_VI");
            int quantity = resultSet.getInt("SO_LUONG");

            Material material = new Material(name, unit, quantity);
            materials.add(material);
        }

        return materials;
    }


    public int find_manager_id(String MANAGER_NAME) throws SQLException {
        int manager_id = 0;
        PreparedStatement pstmt = conn.prepareStatement("SELECT ID FROM NHAN_VIEN WHERE TEN = ? AND CHUC_VU='Quản lý'");
        pstmt.setString(1, MANAGER_NAME);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            manager_id = rs.getInt(1);
        }
        return manager_id;
    }

    public void InsertEmp(String name, String sdt, String chuc_vu, int luong, int id_quan_ly) throws SQLException {
        String sql = "INSERT INTO NHAN_VIEN ( ID, TEN, SO_DIEN_THOAI, CHUC_VU, LUONG, ID_QUAN_LY ) VALUES (?,?,?,?,?,?)";
        Statement sm = conn.createStatement();
        ResultSet rs = sm.executeQuery("SELECT MAX(ID) FROM NHAN_VIEN");
        int id = 0;
        if (rs.next()) {
            id = rs.getInt(1) + 1;
        }
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.setString(2, name);
            pstmt.setString(3, sdt);
            pstmt.setString(4, chuc_vu);
            pstmt.setInt(5, luong);
            if (chuc_vu.equals("Quản lý")) {
                pstmt.setInt(6, Types.NULL);
            } else {
                pstmt.setInt(6, id_quan_ly);
            }
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void DeleteEmp(String emp_name) throws SQLException {
        int id = find_id(emp_name);
        String sql = "DELETE FROM NHAN_VIEN WHERE ID = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int find_id(String name) throws SQLException {
        int id_ = 0;
        PreparedStatement pstmt = conn.prepareStatement("SELECT ID FROM NHAN_VIEN WHERE TEN = ?");
        pstmt.setString(1, name);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            id_ = rs.getInt(1);
        }
        return id_;
    }

    public void UpdateEmp(String name, String sdt, int luong, String ten_quan_ly) throws SQLException {
        int id = find_id(name);
        int id_quan_ly = find_manager_id(ten_quan_ly);
        String sql = "UPDATE NHAN_VIEN SET SO_DIEN_THOAI=?, LUONG=?, ID_QUAN_LY=? WHERE ID=?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, sdt);
        pstmt.setInt(2, luong);
        pstmt.setInt(3, id_quan_ly);
        pstmt.setInt(4, id);
        pstmt.executeUpdate();
    }

    public List<Supplier> getAllNhaCungCap() throws SQLException {
        List<Supplier> nhaCungCapList = new ArrayList<>();
        Statement sm = conn.createStatement();
        ResultSet resultSet = sm.executeQuery("SELECT * FROM NHA_CUNG_CAP");

        while (resultSet.next()) {
            Supplier nhaCungCap = new Supplier();
            nhaCungCap.setId(resultSet.getInt("ID"));
            nhaCungCap.setTen(resultSet.getString("TEN"));
            nhaCungCap.setSoDienThoai(resultSet.getString("SO_DIEN_THOAI"));
            nhaCungCap.setEmail(resultSet.getString("EMAIL"));
            nhaCungCap.setDiaChi(resultSet.getString("DIA_CHI"));

            nhaCungCapList.add(nhaCungCap);
        }

        return nhaCungCapList;
    }

    public List<SuperSuppiler> getAllNhaCungCapWithNguyenLieu() throws SQLException {
        List<SuperSuppiler> superSupplierList = new ArrayList<>();
        Statement statement = conn.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT NHA_CUNG_CAP.*, x1.IDNGUYENLIEU, x1.TENNGUYENLIEU " +
                "FROM NHA_CUNG_CAP " +
                "LEFT JOIN (SELECT NGUYEN_LIEU.ID AS IDNGUYENLIEU, TEN AS TENNGUYENLIEU, ID_NHA_CUNG_CAP " +
                "           FROM NGUYEN_LIEU, NCC_NL " +
                "           WHERE NCC_NL.ID_NGUYEN_LIEU = NGUYEN_LIEU.ID) x1 ON x1.ID_NHA_CUNG_CAP = NHA_CUNG_CAP.ID");

        while (resultSet.next()) {
            int id = resultSet.getInt("ID");
            String ten = resultSet.getString("TEN");

            String soDienThoai = resultSet.getString("SO_DIEN_THOAI");
            if (resultSet.wasNull()) {
                soDienThoai = "unknown"; // Set your desired default value here
            }

            String email = resultSet.getString("EMAIL");
            String diaChi = resultSet.getString("DIA_CHI");
            int nguyenLieuId = resultSet.getInt("IDNGUYENLIEU");
            String nguyenLieuTen = resultSet.getString("TENNGUYENLIEU");

            SuperSuppiler superSupplier = new SuperSuppiler(id, ten, soDienThoai, email, diaChi, nguyenLieuId, nguyenLieuTen);
            superSupplierList.add(superSupplier);
        }

        return superSupplierList;
    }

    public List<SuperIngredient> getAllSuperIngredients() throws SQLException {
        List<SuperIngredient> superIngredientList = new ArrayList<>();
        Statement sm = conn.createStatement();
        ResultSet resultSet = sm.executeQuery("SELECT NL.ID, NL.TEN, NL.DON_VI, NL.SO_LUONG_TRONG_KHO, NL.GIA_NL, NCC_NL.NGAY_NL_NHAP_KHO " +
                "FROM NGUYEN_LIEU NL " +
                "LEFT JOIN NHACUNGCAP_NGUYENLIEU_QUANLY_BEP NCC_NL ON NL.ID = NCC_NL.ID_NL");

        while (resultSet.next()) {
            SuperIngredient superIngredient = new SuperIngredient();
            superIngredient.setId(resultSet.getInt("ID"));
            superIngredient.setTen(resultSet.getString("TEN"));
            superIngredient.setDonVi(resultSet.getString("DON_VI"));
            superIngredient.setSoLuongTrongKho(resultSet.getInt("SO_LUONG_TRONG_KHO"));
            superIngredient.setGia(resultSet.getInt("GIA_NL"));

            Date ngayNhapKhoValue = resultSet.getDate("NGAY_NL_NHAP_KHO");
            if (ngayNhapKhoValue != null) {
                superIngredient.setNgayNhapKho(ngayNhapKhoValue);
            } else {
                superIngredient.setNgayNhapKho(null); // Set null value if NGAY_NL_NHAP_KHO is null
            }

            superIngredientList.add(superIngredient);
        }

        return superIngredientList;
    }

    public List<Supplier> getNhaCungCapByNguyenLieuId(int nguyenLieuId) throws SQLException {
        List<Supplier> nhaCungCapList = new ArrayList<>();

        String sql = "SELECT NCC.ID, NCC.TEN, NCC.SO_DIEN_THOAI, NCC.EMAIL, NCC.DIA_CHI " +
                "FROM NHA_CUNG_CAP NCC " +
                "INNER JOIN NCC_NL NN ON NCC.ID = NN.ID_NHA_CUNG_CAP " +
                "WHERE NN.ID_NGUYEN_LIEU = ?";

        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setInt(1, nguyenLieuId);
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            Supplier nhaCungCap = new Supplier();
            nhaCungCap.setId(resultSet.getInt("ID"));
            nhaCungCap.setTen(resultSet.getString("TEN"));
            nhaCungCap.setSoDienThoai(resultSet.getString("SO_DIEN_THOAI"));
            nhaCungCap.setEmail(resultSet.getString("EMAIL"));
            nhaCungCap.setDiaChi(resultSet.getString("DIA_CHI"));

            nhaCungCapList.add(nhaCungCap);
        }

        return nhaCungCapList;
    }

    public List<Voucher> getAllVoucher() throws SQLException {
        Statement sm = conn.createStatement();
        ResultSet rs = sm.executeQuery("SELECT * FROM CHUONG_TRINH_KM");

        List<Voucher> vouchers = new ArrayList<>();

        while (rs.next()) {
            int id = rs.getInt("ID");
            String moTa = rs.getString("MO_TA");
            Date ngayBatDau = rs.getDate("NGAY_BAT_DAU");
            Date ngayKetThuc = rs.getDate("NGAY_KET_THUC");
            String maGiamGia = rs.getString("MA_GIAM_GIA");
            int phanTramGiamGia = rs.getInt("PHAN_TRAM_GIAM_GIA");

            Voucher voucher = new Voucher(id, moTa, ngayBatDau, ngayKetThuc, maGiamGia, phanTramGiamGia);
            vouchers.add(voucher);
        }

        return vouchers;
    }
}


