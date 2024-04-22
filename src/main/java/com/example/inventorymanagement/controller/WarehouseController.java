package com.example.inventorymanagement.controller;

import com.example.inventorymanagement.exception.ImportArticlesException;
import com.example.inventorymanagement.exception.ImportProductsException;
import com.example.inventorymanagement.exception.ProcessOrderException;
import com.example.inventorymanagement.exception.RetrieveProductsException;
import com.example.inventorymanagement.model.*;
import com.example.inventorymanagement.service.WarehouseManagementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/ikea")
public class WarehouseController {
    @Autowired
    private WarehouseManagementService warehouseService;

    private static final Logger LOGGER = LoggerFactory.getLogger(WarehouseController.class);

    /**
     * <B>Description :</B>  GET API to import articles from JSON file
     *
     * @return List<Article>
     */
    @GetMapping("/import-articles")
    public List<Article> importArticles() throws ImportArticlesException {
        LOGGER.info("Request received to import articles");
        return warehouseService.importArticles();
    }

    /**
     * <B>Description :</B>  GET API to import products from JSON file
     *
     * @return List<Product>
     */
    @GetMapping("/import-products")
    public List<Product> importProducts() throws ImportProductsException {
        LOGGER.info("Request received to import products");
        return warehouseService.importProducts();
    }

    /**
     * <B>Description :</B>  POST API to add a new article
     *
     * @param request AddArticleRequest
     * @return ResponseEntity<String> Return Success or Failure message
     */
    @PostMapping("/add-article")
    public List<Article> updateOrAddArticle(@RequestBody AddArticleRequest request) throws IOException {
        LOGGER.info("Request received to add an article");
        return warehouseService.updateOrAddArticle(request);
    }

    /**
     * <B>Description :</B>  POST API to add a new product
     *
     * @param request AddProductRequest
     * @return ResponseEntity<String> Return Success or Failure message
     */
    @PostMapping("/add-product")
    public ResponseEntity<String> updateOrAddProduct(@RequestBody AddProductRequest request) throws IOException {
        LOGGER.info("Request received to add product");
        boolean added = warehouseService.updateOrAddProduct(request);
        if (added) {
            return new ResponseEntity<>("Product added successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Not enough stock to add product", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * <B>Description :</B>  GET API to fetch all available products
     *
     * @return Map<String, Integer> Map of product and quantity
     */
    @GetMapping("/get-all-products")
    public Map<String, Integer> retrieveAllProducts() throws RetrieveProductsException {
        LOGGER.info("Request received to retrieve all products");
        return warehouseService.retrieveAllProducts();
    }

    /**
     * <B>Description :</B>  POST API to process an order
     *
     * @param request BuyProductRequest
     * @return ResponseEntity<String> Return Success or Failure message
     */
    @PostMapping("/buy-product")
    public ResponseEntity<String> processOrder(@RequestBody BuyProductRequest request) throws ProcessOrderException {
        LOGGER.info("Request received to place an order");
        boolean added = warehouseService.processOrder(request);
        if (added) {
            return new ResponseEntity<>("Order successful :)", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Item Out of Stock :(", HttpStatus.BAD_REQUEST);
        }
    }
}
