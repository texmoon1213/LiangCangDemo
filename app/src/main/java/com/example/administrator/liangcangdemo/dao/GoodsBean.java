package com.example.administrator.liangcangdemo.dao;

/**
 * Created by Administrator on 2017/7/18.
 */

public class GoodsBean {
    private int id;
    private int number;
    private String name;
    private String price;

    public GoodsBean() {
    }

    public GoodsBean(int id, int number) {
        this.id = id;
        this.number = number;
    }

    public GoodsBean(int id, int number, String name, String price) {
        this.id = id;
        this.number = number;
        this.name = name;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
