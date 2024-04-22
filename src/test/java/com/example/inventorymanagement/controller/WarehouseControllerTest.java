package com.example.inventorymanagement.controller;

import com.example.inventorymanagement.exception.ImportArticlesException;
import com.example.inventorymanagement.exception.ImportProductsException;
import com.example.inventorymanagement.exception.ProcessOrderException;
import com.example.inventorymanagement.exception.RetrieveProductsException;
import com.example.inventorymanagement.model.*;
import com.example.inventorymanagement.service.WarehouseManagementService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class WarehouseControllerTest {
    @InjectMocks
    private WarehouseController warehouseController;
    @Mock
    private WarehouseManagementService warehouseService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * <B>Description :</B> Invoked to test importing articles from JSON
     */
    @Test
    void testImportArticles() throws ImportArticlesException {
        List<Article> articles = Arrays.asList(new Article(), new Article());
        when(warehouseService.importArticles()).thenReturn(articles);
        List<Article> result = warehouseController.importArticles();
        assertEquals(2, result.size());
        verify(warehouseService, times(1)).importArticles();
    }

    /**
     * <B>Description :</B> Invoked to test import articles throws Exception
     */
    @Test
    void testImportArticlesException() throws ImportArticlesException {
        when(warehouseService.importArticles()).thenThrow(new ImportArticlesException("Error importing articles"));
        assertThrows(ImportArticlesException.class, () -> warehouseController.importArticles());
        verify(warehouseService, times(1)).importArticles();
    }

    /**
     * <B>Description :</B> Invoked to test import products from JSON
     */
    @Test
    void testImportProducts() throws ImportProductsException {
        List<Product> products = Arrays.asList(new Product(), new Product());
        when(warehouseService.importProducts()).thenReturn(products);
        List<Product> result = warehouseController.importProducts();
        assertEquals(2, result.size());
        verify(warehouseService, times(1)).importProducts();
    }

    /**
     * <B>Description :</B> Invoked to test import products throws Exception
     */
    @Test
    void testImportProductsException() throws ImportProductsException {
        when(warehouseService.importProducts()).thenThrow(new ImportProductsException("Error importing products"));
        assertThrows(ImportProductsException.class, () -> warehouseController.importProducts());
        verify(warehouseService, times(1)).importProducts();
    }

    /**
     * <B>Description :</B> Invoked to test adding an article
     */
    @Test
    void testAddArticle() throws IOException {
        AddArticleRequest request = new AddArticleRequest();
        List<Article> articles = Arrays.asList(new Article());
        when(warehouseService.updateOrAddArticle(request)).thenReturn(articles);
        List<Article> result = warehouseController.updateOrAddArticle(request);
        assertEquals(1, result.size());
        verify(warehouseService, times(1)).updateOrAddArticle(request);
    }

    /**
     * <B>Description :</B> Invoked to test adding an article throws Exception
     */
    @Test
    void testAddArticleIOException() throws IOException {
        AddArticleRequest request = new AddArticleRequest();
        when(warehouseService.updateOrAddArticle(request)).thenThrow(new IOException("Error adding article"));
        assertThrows(IOException.class, () -> warehouseController.updateOrAddArticle(request));
        verify(warehouseService, times(1)).updateOrAddArticle(request);
    }

    /**
     * <B>Description :</B> Invoked to adding a product
     */
    @Test
    void testAddProductSuccess() throws IOException {
        AddProductRequest request = new AddProductRequest();
        when(warehouseService.updateOrAddProduct(request)).thenReturn(true);
        ResponseEntity<String> result = warehouseController.updateOrAddProduct(request);
        assertEquals("Product added successfully", result.getBody());
        assertEquals(HttpStatus.OK, result.getStatusCode());
        verify(warehouseService, times(1)).updateOrAddProduct(request);
    }

    /**
     * <B>Description :</B> Invoked to test add product failure when articles not in stock
     */
    @Test
    void testAddProductFailure() throws IOException {
        AddProductRequest request = new AddProductRequest();
        when(warehouseService.updateOrAddProduct(request)).thenReturn(false);
        ResponseEntity<String> result = warehouseController.updateOrAddProduct(request);
        assertEquals("Not enough stock to add product", result.getBody());
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        verify(warehouseService, times(1)).updateOrAddProduct(request);
    }

    /**
     * <B>Description :</B> Invoked to test retrieve all products functionality
     */
    @Test
    void testGetAllProducts() throws RetrieveProductsException {
        Map<String, Integer> products = new HashMap<>();
        products.put("Product1", 10);
        products.put("Product2", 20);
        when(warehouseService.retrieveAllProducts()).thenReturn(products);
        Map<String, Integer> result = warehouseController.retrieveAllProducts();
        assertEquals(2, result.size());
        assertEquals(10, result.get("Product1"));
        assertEquals(20, result.get("Product2"));
        verify(warehouseService, times(1)).retrieveAllProducts();
    }

    /**
     * <B>Description :</B> Invoked to test retrieve all products functionality throws Exception
     */
    @Test
    void testRetrieveProductsException() throws RetrieveProductsException {
        when(warehouseService.retrieveAllProducts()).thenThrow(new RetrieveProductsException("Error retrieving products"));
        assertThrows(RetrieveProductsException.class, () -> warehouseController.retrieveAllProducts());
        verify(warehouseService, times(1)).retrieveAllProducts();
    }

    /**
     * <B>Description :</B> Invoked to test processing order successfully
     */
    @Test
    void testProcessOrderSuccess() throws ProcessOrderException {
        BuyProductRequest request = new BuyProductRequest();
        when(warehouseService.processOrder(request)).thenReturn(true);
        ResponseEntity<String> result = warehouseController.processOrder(request);
        assertEquals("Order successful :)", result.getBody());
        assertEquals(HttpStatus.OK, result.getStatusCode());
        verify(warehouseService, times(1)).processOrder(request);
    }

    /**
     * <B>Description :</B> Invoked to test processing order failure
     */
    @Test
    void testProcessOrderFailure() throws ProcessOrderException {
        BuyProductRequest request = new BuyProductRequest();
        when(warehouseService.processOrder(request)).thenReturn(false);
        ResponseEntity<String> result = warehouseController.processOrder(request);
        assertEquals("Item Out of Stock :(", result.getBody());
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        verify(warehouseService, times(1)).processOrder(request);
    }

    /**
     * <B>Description :</B> Invoked to test process order throws Exception
     */
    @Test
    void testProcessOrderException() throws ProcessOrderException {
        BuyProductRequest request = new BuyProductRequest();
        when(warehouseService.processOrder(request)).thenThrow(new ProcessOrderException("Error processing order"));
        assertThrows(ProcessOrderException.class, () -> warehouseController.processOrder(request));
        verify(warehouseService, times(1)).processOrder(request);
    }
}