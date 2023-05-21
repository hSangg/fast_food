package model;

import javafx.scene.image.Image;

public class ItemTopSeller {
    private String tenMon;
    private int gia;
    private int soLuong;
    private byte[] image;
    public ItemTopSeller(String tenMon, int gia,int soLuong){
        this.tenMon=tenMon;
        this.gia=gia;
        this.soLuong=soLuong;
    }
    public void setImage(byte[] image){
        this.image=image;
    }
    public String getTenMon(){
        return tenMon;
    }
}
