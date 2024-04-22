package com.example.inventorymanagement.model;

import java.util.List;

public class AddProductRequest {
    private String name;
    private List<ContainArticle> articleQuantities;
    private double price;
    private int stock;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ContainArticle> getArticleQuantities() {
        return articleQuantities;
    }

    public void setArticleQuantities(List<ContainArticle> articleQuantities) {
        this.articleQuantities = articleQuantities;
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
}