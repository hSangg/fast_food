package model;

public class ItemTopSeller {
    private String tenMon;
    private int gia;
    private byte[] hinhAnh;

    public ItemTopSeller(String tenMon, int gia, byte[] hinhAnh) {
        this.tenMon = tenMon;
        this.gia = gia;
        this.hinhAnh = hinhAnh;
    }

    public String getTenMon() {
        return tenMon;
    }

    public int getGia() {
        return gia;
    }

    public byte[] getHinhAnh() {
        return hinhAnh;
    }

    // You can add additional methods or functionality as needed
}
