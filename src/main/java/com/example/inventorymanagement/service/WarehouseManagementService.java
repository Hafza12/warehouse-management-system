package com.example.inventorymanagement.service;

import com.example.inventorymanagement.exception.RetrieveProductsException;
import com.example.inventorymanagement.exception.ImportArticlesException;
import com.example.inventorymanagement.exception.ImportProductsException;
import com.example.inventorymanagement.exception.ProcessOrderException;
import com.example.inventorymanagement.model.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface WarehouseManagementService {
    List<Article> importArticles() throws ImportArticlesException;

    List<Product> importProducts() throws ImportProductsException;

    List<Article> updateOrAddArticle(AddArticleRequest request) throws IOException;

    Boolean updateOrAddProduct(AddProductRequest request) throws IOException;

    Map<String, Integer> retrieveAllProducts() throws RetrieveProductsException;

    Boolean processOrder(BuyProductRequest request) throws ProcessOrderException;
}
