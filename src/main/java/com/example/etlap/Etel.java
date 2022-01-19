package com.example.etlap;

public class Etel {
    private String name;
    private String category;
    private String desc;
    private int price;

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public String getDesc() {
        return desc;
    }

    public int getPrice() {
        return price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Etel(String name, String category, String desc, int price) {
        this.name = name;
        this.category = category;
        this.desc = desc;
        this.price = price;
    }
}
