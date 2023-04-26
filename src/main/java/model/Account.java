package model;

public class Account {
    private String name;
    private String password;
    public String getName(){
        return name;
    }
    public String getPassword(){
    return password;
    }
    public Account(String ten,String mk){
        name=ten;
        password=mk;
    }
}
