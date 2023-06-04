package utils;

import model.*;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.sql.Date;

public class DBHandler {
    public Connection conn;
    public int trang_thai_dang_nhap;

    public DBHandler() {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            String url = "jdbc:oracle:thin:@localhost:1521:ORCL";
            String user = "Sinhvien03";
            String pass = "1";
            this.conn = DriverManager.getConnection(url, user, pass);
            SetDateFormat();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public void SetDateFormat() throws SQLException {
        String sql = "alter session set nls_date_format ='YYYY-MM-DD'";
        try{
        Statement statement= conn.createStatement();
        statement.execute(sql);
        statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
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

            int numTables = rs.getInt("SO_BAN_TAO_DON");
            int total = rs.getInt("TONG_TIEN");
            String paymentMethod = rs.getString("HINH_THUC_THANH_TOAN");
            String status = rs.getString("TRANG_THAI");
            int isOnline = rs.getInt("DAT_ONLINE");
            Date orderDate = rs.getDate("NGAY_DAT");
            String notes = rs.getString("GHI_CHU");

            // create a new Order object using the retrieved data
            Order order = new Order(id, customerId, customerName, cashierId, cashierName
                    , numTables, total, paymentMethod,
                    status, isOnline, orderDate, notes);

            result.add(order);

        }

        return result;
    }
    public HashSet<Order1> getAllOrders1() throws SQLException {
        Statement sm = conn.createStatement();
        ResultSet rs = sm.executeQuery("SELECT * FROM DON_HANG");
        HashSet<Order1> result = new HashSet<>();

        while (rs.next()) {
            int id = rs.getInt("ID");
            int customerId = rs.getInt("ID_KH");
            int voucherId = rs.getInt("ID_KM");
            int cashierId = rs.getInt("ID_THU_NGAN");
            int numTables = rs.getInt("SO_BAN_TAO_DON");
            int total = rs.getInt("TONG_TIEN");
            String paymentMethod = rs.getString("HINH_THUC_THANH_TOAN");
            String status = rs.getString("TRANG_THAI");
            int isOnline = rs.getInt("DAT_ONLINE");
            Date orderDate = rs.getDate("NGAY_DAT");
            String notes = rs.getString("GHI_CHU");

            // create a new Order object using the retrieved data
            Order1 order = new Order1(id, customerId, voucherId, cashierId, numTables
                   , total, paymentMethod,
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
        String query = "SELECT N.ID,TEN,DON_VI,SO_LUONG_TRONG_KHO,GIA_NL,NGAY_NL_NHAP_KHO FROM NGUYEN_LIEU N FULL JOIN NHACUNGCAP_NGUYENLIEU_QUANLY_BEP M on N.ID=M.ID_NL";
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
    public int find_manager_id2(int id_nv) throws SQLException {
        int manager_id = 0;
        PreparedStatement pstmt = conn.prepareStatement("SELECT ID_QUAN_LY FROM NHAN_VIEN WHERE ID = ?");
        pstmt.setInt(1, id_nv);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            manager_id = rs.getInt(1);
        }
        return manager_id;
    }
    public boolean isBep(int id_nv) throws SQLException{
        PreparedStatement pstmt = conn.prepareStatement("SELECT ID FROM NHAN_VIEN WHERE ID = ? AND CHUC_VU='Đầu bếp'");
        pstmt.setInt(1, id_nv);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            if(String.valueOf(rs.getInt(1))!=null){
               return true;
            }
        }
        return false;
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
    public int find_idNl(String name) throws SQLException {
        int id = 0;
        PreparedStatement pstmt = conn.prepareStatement("SELECT ID FROM NGUYEN_LIEU WHERE TEN = ?");
        pstmt.setString(1, name);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            id = rs.getInt(1);
        }
        return id;
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

    public void AddFood(String ten, String moTa, String loai,int gia,String imagePath,String image) throws SQLException {
        String sql = "INSERT INTO MON_AN ( ID, TEN_MON, MO_TA, LOAI, GIA ) VALUES (?,?,?,?,?)";
        Statement sm = conn.createStatement();
        ResultSet rs = sm.executeQuery("SELECT MAX(ID) FROM MON_AN");
        int id=0;
        id = rs.getInt(1)+1;
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.setString(2, ten);
            pstmt.setString(3, moTa);
            pstmt.setString(4, loai);
            pstmt.setInt(5, gia);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        sql = "{call UPDATE_MON(?, ?, ?)}";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, image);
        pstmt.setInt(2, id);
        pstmt.setString(3, imagePath);
        pstmt.executeUpdate();
    }
    public void UpdateFood(int idMon, String ten, String moTa, String loai, int gia,String imagePath,String image) throws SQLException {
        String sql = "UPDATE MON_AN SET TEN_MON=?, MO_TA=?, LOAI=?,GIA=? WHERE ID=?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1,ten);
        pstmt.setString(2,moTa);
        pstmt.setString(3,loai);
        pstmt.setInt(4,gia);
        pstmt.setInt(5,idMon);
        pstmt.executeUpdate();
        if(!imagePath.isEmpty()&&!image.isEmpty()) {
            sql = "{call UPDATE_MON(?, ?, ?)}";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, image);
            pstmt.setInt(2, idMon);
            pstmt.setString(3, imagePath);
            pstmt.executeUpdate();

        }
    }

    public int findIdMon(String tenMon) throws SQLException{
        int idMon = 0;
        PreparedStatement pstmt = conn.prepareStatement("SELECT ID FROM MON_AN WHERE TEN_MON = ?");
        pstmt.setString(1, tenMon);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            idMon = rs.getInt(1);
        }
        return idMon;
    }
    public int findIdncc(String tenNcc) throws SQLException{
        int idncc = 0;
        PreparedStatement pstmt = conn.prepareStatement("SELECT ID FROM NHA_CUNG_CAP WHERE TEN = ?");
        pstmt.setString(1, tenNcc);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            idncc = rs.getInt(1);
        }
        return idncc;
    }
    public int findIdKh(String tenKh) throws SQLException{
        int idKh = 0;
        PreparedStatement pstmt = conn.prepareStatement("SELECT ID FROM KHACH_HANG WHERE TEN = ?");
        pstmt.setString(1, tenKh);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            idKh = rs.getInt(1);
        }
        return idKh;
    }
    public int findIdKm() throws SQLException{
        LocalDate currentDate = LocalDate.now();
        java.sql.Date ngayHienTai=java.sql.Date.valueOf(currentDate);
        int idKm = 0;
        Statement pstmt = conn.createStatement();
        ResultSet rs = pstmt.executeQuery("SELECT * FROM CHUONG_TRINH_KM");
        while (rs.next()) {
            java.sql.Date ngayBatDau=rs.getDate(3);
            java.sql.Date ngayKetThuc=rs.getDate(4);
            if(ngayHienTai.compareTo(ngayBatDau)>=0 && ngayHienTai.compareTo(ngayKetThuc)<=0){
                idKm=rs.getInt(1);
            }
        }
        pstmt.close();
        return idKm;
    }
    public double findPhanTramGiamGia() throws SQLException{
        LocalDate currentDate = LocalDate.now();
        java.sql.Date ngayHienTai=java.sql.Date.valueOf(currentDate);
        double PhanTramGiamGia = 0;
        Statement pstmt = conn.createStatement();
        ResultSet rs = pstmt.executeQuery("SELECT * FROM CHUONG_TRINH_KM");
        while (rs.next()) {
            java.sql.Date ngayBatDau=rs.getDate(3);
            java.sql.Date ngayKetThuc=rs.getDate(4);
            if(ngayHienTai.compareTo(ngayBatDau)>=0 && ngayHienTai.compareTo(ngayKetThuc)<=0){
                PhanTramGiamGia=rs.getDouble(6);
            }
        }
        pstmt.close();
        return PhanTramGiamGia;
    }

    public int findIdNl(String tenNl) throws SQLException{
        int idNl = 0;
        PreparedStatement pstmt = conn.prepareStatement("SELECT ID FROM NGUYEN_LIEU WHERE TEN = ?");
        pstmt.setString(1, tenNl);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            idNl = rs.getInt(1);
        }
        return idNl;
    }
    public void EditNlOfMon(int idMon,  String tenMon, String tenNl,int sL) throws SQLException {
        int idNl = find_id(tenNl);
        String sql ="Update NGUYEN_LIEU_MON_AN SET SO_LUONG=?  WHERE ID_MON=? AND ID_NL=?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1,sL);
        pstmt.setInt(2,idMon);
        pstmt.setInt(3,idNl);
        pstmt.executeUpdate();
    }

    public void DelNlOfMon(String tenMon, String tenNl) throws SQLException {
        int idMon = findIdMon(tenMon);
        int idNl = findIdNl(tenNl);
        PreparedStatement pstmt = conn.prepareStatement("DELETE FROM NGUYEN_LIEU_MON_AN WHERE ID_MON=? AND ID_NL=?");
        pstmt.setInt(1,idMon);
        pstmt.setInt(2,idNl);
        pstmt.executeUpdate();
    }
    public void InsNl(String tenMon,String tenNl,int sL) throws SQLException{
        int idMon = findIdMon(tenMon);
        int idNl = findIdNl(tenNl);
        PreparedStatement pstmt = conn.prepareStatement("INSERT INTO NGUYEN_LIEU_MON_AN VALUES (?,?,?)");
        pstmt.setInt(1,idNl);
        pstmt.setInt(2,idMon);
        pstmt.setInt(3,sL);
        pstmt.executeUpdate();
    }

    public void InsVC(int id,String mota,String phanTramGiamGia,String maGiamGia,java.util.Date ngayBatDau,java.util.Date ngayKetThuc) throws SQLException{
        java.sql.Date ngayBatDauSql=new java.sql.Date(ngayBatDau.getTime());
        java.sql.Date ngayKetThucSql=new java.sql.Date(ngayKetThuc.getTime());
        PreparedStatement pstmt = conn.prepareStatement("INSERT INTO CHUONG_TRINH_KM (ID, MO_TA, NGAY_BAT_DAU, NGAY_KET_THUC, MA_GIAM_GIA, PHAN_TRAM_GIAM_GIA) VALUES (?,?,?,?,?,?)");
        pstmt.setInt(1,id);
        pstmt.setString(2,mota);
        pstmt.setString(3, ngayBatDauSql.toString());
        pstmt.setString(4, ngayKetThucSql.toString());
        pstmt.setString(5,maGiamGia);
        pstmt.setInt(6, Integer.parseInt(phanTramGiamGia));
        pstmt.executeUpdate();
    }
    public void EditOfVC(int id,String mota,String phanTramGiamGia,String maGiamGia,java.util.Date ngayBatDau,java.util.Date ngayKetThuc) throws SQLException {
        java.sql.Date ngayBatDauSql=new java.sql.Date(ngayBatDau.getTime());
        java.sql.Date ngayKetThucSql=new java.sql.Date(ngayKetThuc.getTime());
        String sql ="UPDATE CHUONG_TRINH_KM SET MO_TA=?,NGAY_BAT_DAU=?,NGAY_KET_THUC=?,MA_GIAM_GIA=?,PHAN_TRAM_GIAM_GIA=? WHERE ID=?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1,mota);
        pstmt.setString(2,ngayBatDauSql.toString());
        pstmt.setString(3,ngayKetThucSql.toString());
        pstmt.setString(4,maGiamGia);
        pstmt.setInt(5, Integer.parseInt(phanTramGiamGia));
        pstmt.setInt(6,id);
        pstmt.executeUpdate();
    }
    public void xoaKM(int id) throws SQLException{
        PreparedStatement pstmt=conn.prepareStatement("DELETE FROM CHUONG_TRINH_KM WHERE ID=?");
        pstmt.setInt(1,id);
        pstmt.executeUpdate();
    }

    public void InsNCC(int id,String ten,String sdt,String email,String dia_chi) throws SQLException{
        PreparedStatement pstmt = conn.prepareStatement("INSERT INTO NHA_CUNG_CAP (ID, TEN,SO_DIEN_THOAI,EMAIL,DIA_CHI) VALUES (?,?,?,?,?)");
        pstmt.setInt(1,id);
        pstmt.setString(2,ten);
        pstmt.setString(3, sdt);
        pstmt.setString(4, email);
        pstmt.setString(5,dia_chi);
        pstmt.executeUpdate();
    }
    public void EditOfNCC(int id,String ten,String sdt,String email,String dia_chi) throws SQLException {
        String sql ="UPDATE NHA_CUNG_CAP SET TEN=?,SO_DIEN_THOAI=?,EMAIL=?,DIA_CHI=? WHERE ID=?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1,ten);
        pstmt.setString(2,sdt.toString());
        pstmt.setString(3,email.toString());
        pstmt.setString(4,dia_chi);
        pstmt.setInt(5,id);
        pstmt.executeUpdate();
    }
    public void xoaNCC(int id) throws SQLException{
        PreparedStatement pstmt=conn.prepareStatement("DELETE FROM NHA_CUNG_CAP WHERE ID=?");
        pstmt.setInt(1,id);
        pstmt.executeUpdate();
    }
    // chưa thao tác được dữ liệu ngày nhập kho
    public void InsIngre(int id,String ten,String don_vi,int sl,int gia) throws SQLException{
        PreparedStatement pstmt = conn.prepareStatement("INSERT INTO NGUYEN_LIEU (ID, TEN,DON_VI ,SO_LUONG_TRONG_KHO,GIA_NL) VALUES (?,?,?,?,?)");
        pstmt.setInt(1,id);
        pstmt.setString(2,ten);
        pstmt.setString(3, don_vi);
        pstmt.setInt(4, sl);
        pstmt.setInt(5,gia);
        pstmt.executeUpdate();

    }
    public void EditOfIngre(int id,String ten,String don_vi,int sl,int gia) throws SQLException {
        String sql ="UPDATE NHA_CUNG_CAP SET TEN=?,DON_VI=?,SO_LUONG_TRONG_KHO=?,GIA_NL=? WHERE ID=?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1,ten);
        pstmt.setString(2,don_vi);
        pstmt.setInt(3,sl);
        pstmt.setInt(4,gia);
        pstmt.setInt(5,id);
        pstmt.executeUpdate();
    }
    public void xoaIngre(int id) throws SQLException{
        PreparedStatement pstmt=conn.prepareStatement("DELETE FROM NGUYEN_LIEU WHERE ID=?");
        pstmt.setInt(1,id);
        pstmt.executeUpdate();
    }
    public void InsOrder(int id, int id_kh, int id_km, Integer id_thu_ngan, int tongtien, int soban, String hinhthuc, String trangthai, int datonl, LocalDate ngay_dat, String ghiChu) throws SQLException{
        java.sql.Date ngaydat=java.sql.Date.valueOf(ngay_dat.toString());
        PreparedStatement pstmt = conn.prepareStatement("INSERT INTO DON_HANG (ID, ID_KH, ID_KM,ID_THU_NGAN, TONG_TIEN, SO_BAN_TAO_DON,HINH_THUC_THANH_TOAN,TRANG_THAI,DAT_ONLINE,NGAY_DAT,GHI_CHU) VALUES (?,?,?,?,?,?,?,?,?,?,?)");
        pstmt.setInt(1,id);
       pstmt.setString(2,null);
       pstmt.setInt(3,id_km);
       pstmt.setString(4,null);
       pstmt.setInt(5,tongtien);
       pstmt.setInt(6,soban);
       pstmt.setString(7,hinhthuc);
       pstmt.setString(8,trangthai);
       pstmt.setInt(9,datonl);
       pstmt.setString(10,ngaydat.toString());
       pstmt.setString(11,ghiChu);
        pstmt.executeUpdate();
    }
    public void InsOrderDetail(int id_don,int id_mon,int soluong) throws SQLException{
        PreparedStatement pstmt = conn.prepareStatement("INSERT INTO CHITIET_DON (ID_DON, ID_MON,SOLUONG) VALUES (?,?,?)");
        pstmt.setInt(1,id_don);
        pstmt.setInt(2,id_mon);
        pstmt.setInt(3,soluong);
        pstmt.executeUpdate();
    }
    public void InsRequest(int id_nl,int id_ncc,int id_quan_ly,int id_bep,int tongtien,LocalDate ngayNhap,int soluong) throws SQLException{
        java.sql.Date ngaynhap=java.sql.Date.valueOf(ngayNhap);
        PreparedStatement pstmt = conn.prepareStatement("INSERT INTO NHACUNGCAP_NGUYENLIEU_QUANLY_BEP (ID_NL,ID_NCC,ID_QUAN_LY,ID_BEP,TONG_TIEN,SOLUONG,NGAY_NL_NHAP_KHO) VALUES (?,?,?,?,?,?,?)");
        pstmt.setInt(1,id_nl);
        pstmt.setInt(2,id_ncc);
        pstmt.setInt(3,id_quan_ly);
        pstmt.setInt(4,id_bep);
        pstmt.setInt(5,tongtien);
        pstmt.setInt(6,soluong);
        pstmt.setDate(7,ngaynhap);
        pstmt.executeUpdate();
    }

}



