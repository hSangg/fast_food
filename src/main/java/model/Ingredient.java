package model;

public class Ingredient {
    private int id;
    private String ten;
    private String donVi;
    private int soLuongTrongKho;
    private double giaNL;

    public Ingredient(int id, String ten, String donVi, int soLuongTrongKho, double giaNL) {
        this.id = id;
        this.ten = ten;
        this.donVi = donVi;
        this.soLuongTrongKho = soLuongTrongKho;
        this.giaNL = giaNL;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getDonVi() {
        return donVi;
    }

    public void setDonVi(String donVi) {
        this.donVi = donVi;
    }

    public int getSoLuongTrongKho() {
        return soLuongTrongKho;
    }

    public void setSoLuongTrongKho(int soLuongTrongKho) {
        this.soLuongTrongKho = soLuongTrongKho;
    }

    public double getGiaNL() {
        return giaNL;
    }

    public void setGiaNL(double giaNL) {
        this.giaNL = giaNL;
    }
}
