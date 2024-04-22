package com.example.inventorymanagement.service.impl;

import com.example.inventorymanagement.exception.ImportArticlesException;
import com.example.inventorymanagement.exception.ImportProductsException;
import com.example.inventorymanagement.exception.ProcessOrderException;
import com.example.inventorymanagement.exception.RetrieveProductsException;
import com.example.inventorymanagement.model.*;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class WarehouseServiceImplTest {
    @InjectMocks
    private WarehouseServiceImpl warehouseService;
    @Mock
    private Gson gson;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * <B>Description :</B> Invoked to test import articles
     */
    @Test
    void testImportArticles() throws ImportArticlesException {
        List<Article> articles = Arrays.asList(new Article(), new Article());
        when(gson.fromJson((String) any(), eq(JsonObject.class))).thenReturn(new JsonObject());
        when(gson.fromJson((String) any(), any())).thenReturn(articles);
        List<Article> result = warehouseService.importArticles();
        assertEquals(4, result.size());
    }

    /**
     * <B>Description :</B> Invoked to test import products
     */
    @Test
    void testImportProducts() throws ImportProductsException {
        List<Product> products = Arrays.asList(new Product(), new Product());
        when(gson.fromJson((String) any(), eq(JsonObject.class))).thenReturn(new JsonObject());
        when(gson.fromJson((String) any(), any())).thenReturn(products);
        List<Product> result = warehouseService.importProducts();
        assertEquals(2, result.size());
    }

    /**
     * <B>Description :</B> Invoked to test retrieve all products
     */
    @Test
    void testRetrieveAllProducts() throws RetrieveProductsException {
        List<Product> products = new ArrayList<>();
        List<ContainArticle> containArticleList = new ArrayList<>();

        ContainArticle containArticle = new ContainArticle("1", 2);
        containArticleList.add(containArticle);

        Product product = new Product("table", containArticleList, 39.99, 2);
        products.add(product);
        Map<String, Integer> result = warehouseService.retrieveAllProducts();
        assertEquals(2, result.size());
    }

    /**
     * <B>Description :</B> Invoked to test adding a new product
     */
    @Test
    void testAddProductSuccess() throws IOException {
        AddProductRequest request = new AddProductRequest();
        request.setName("Product1");
        request.setStock(1);
        request.setPrice(100);
        request.setArticleQuantities(Collections.singletonList(new ContainArticle("1", 1)));
        boolean result = warehouseService.updateOrAddProduct(request);
        assertTrue(result);
    }

    /**
     * <B>Description :</B> Invoked to test failure while adding a new product
     */
    @Test
    void testAddProductFailure() throws IOException {
        AddProductRequest request = new AddProductRequest();
        request.setName("Product1");
        request.setStock(10);
        request.setPrice(100);
        request.setArticleQuantities(Collections.singletonList(new ContainArticle("1", 20)));
        boolean result = warehouseService.updateOrAddProduct(request);
        assertFalse(result);
    }

    /**
     * <B>Description :</B> Invoked to test adding an existing product
     */
    @Test
    void testUpdateProductSuccess() throws IOException {
        AddProductRequest request = new AddProductRequest();
        request.setName("Dining Chair");
        request.setStock(1);
        request.setPrice(100);
        request.setArticleQuantities(Collections.singletonList(new ContainArticle("1", 5)));
        boolean result = warehouseService.updateOrAddProduct(request);
        assertTrue(result);
    }

    /**
     * <B>Description :</B> Invoked to test adding a new article
     */
    @Test
    void testAddArticle() throws IOException {
        AddArticleRequest request = new AddArticleRequest();
        request.setName("plastic");
        request.setStock(10);
        List<Article> result = warehouseService.updateOrAddArticle(request);
        assertEquals(5, result.size());
        assertEquals("plastic", result.get(result.size() - 1).getName());
    }

    /**
     * <B>Description :</B> Invoked to test updating an existing article
     */
    @Test
    void testUpdateArticle() throws IOException {
        AddArticleRequest request = new AddArticleRequest();
        request.setName("leg");
        request.setStock(10);
        List<Article> result = warehouseService.updateOrAddArticle(request);
        assertEquals(4, result.size());
        assertEquals("leg", result.get(0).getName());
    }


    /**
     * <B>Description :</B> Invoked to test processing a successful order
     */
    @Test
    void testProcessOrderSuccess() throws ProcessOrderException {
        BuyProductRequest request = new BuyProductRequest();
        request.setRequiredProductList(Collections.singletonList(new RequiredProduct("Dining Chair", 1)));
        boolean result = warehouseService.processOrder(request);
        assertTrue(result);
    }

    /**
     * <B>Description :</B> Invoked to test a failed order
     */
    @Test
    void testProcessOrderFailure() throws ProcessOrderException {
        BuyProductRequest request = new BuyProductRequest();
        request.setRequiredProductList(Collections.singletonList(new RequiredProduct("Product1", 5)));
        boolean result = warehouseService.processOrder(request);
        assertFalse(result);
    }


}
