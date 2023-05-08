package model;

public class Account {
    private String name;
    private String password;
<<<<<<< HEAD
=======
    private String pos;
>>>>>>> 9b84ed2add4b36cf7cfc62360fe950b00027380c
    public String getName(){
        return name;
    }
    public String getPassword(){
<<<<<<< HEAD
    return password;
    }
    public Account(String ten,String mk){
        name=ten;
        password=mk;
=======
        return password;
    }
    public String getps(){
        return pos;
    }
    public Account(String ten,String mk,String pos){
        name=ten;
        password=mk;
        this.pos=pos;
>>>>>>> 9b84ed2add4b36cf7cfc62360fe950b00027380c
    }
}
