package com.example.inventorymanagement.model;

public class Article {
    private String art_id;
    private String name;
    private int stock;

    public String getArt_id() {
        return art_id;
    }

    public void setArt_id(String art_id) {
        this.art_id = art_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public Article(String art_id, String name, int stock) {
        this.art_id = art_id;
        this.name = name;
        this.stock = stock;
    }

    public Article() {
    }
}