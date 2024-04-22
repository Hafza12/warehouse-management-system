package com.example.inventorymanagement.model;

import java.util.List;

public class Product {
    private String name;
    private List<ContainArticle> contain_articles;
    private double price;
    private int stock;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ContainArticle> getContain_articles() {
        return contain_articles;
    }

    public void setContain_articles(List<ContainArticle> contain_articles) {
        this.contain_articles = contain_articles;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public Product(String name, List<ContainArticle> contain_articles, double price, int stock) {
        this.name = name;
        this.contain_articles = contain_articles;
        this.price = price;
        this.stock = stock;
    }

    public Product() {
    }

    public Product(String name, int stock) {
        this.name = name;
        this.stock = stock;
    }
}
