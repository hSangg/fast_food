package model;

public class Account {
    private String name;
    private String password;
    private String pos;
    public String getName(){
        return name;
    }
    public String getPassword(){
        return password;
    }
    public String getps(){
        return pos;
    }
    public Account(String ten,String mk,String pos){
        name=ten;
        password=mk;
        this.pos=pos;
    }
}
