package com.example.numad_ujjvalsharma;

public class Product {

    public int id;
    public String title;
    public int price;
    public String description;
    public String imgUrl;
    public  Product(int id, String title,int price,String description,String imgUrl){
        this.id=id;
        this.title=title;
        this.imgUrl=imgUrl;
        this.price=price;
        this.description=description;
    }


    @Override
    public String toString(){
        return "id: "+id+" title:" + title + " price:" + price + " description:" + description + " imgUrl:" + imgUrl;
    }


}
