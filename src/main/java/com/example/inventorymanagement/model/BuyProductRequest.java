package com.example.inventorymanagement.model;

import java.util.List;

public class BuyProductRequest {
    private List<RequiredProduct> requiredProductList;

    public List<RequiredProduct> getRequiredProductList() {
        return requiredProductList;
    }

    public void setRequiredProductList(List<RequiredProduct> requiredProductList) {
        this.requiredProductList = requiredProductList;
    }
}
