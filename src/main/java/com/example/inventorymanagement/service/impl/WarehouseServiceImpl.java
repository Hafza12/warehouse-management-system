package com.example.inventorymanagement.service.impl;

import com.example.inventorymanagement.exception.ImportArticlesException;
import com.example.inventorymanagement.exception.ImportProductsException;
import com.example.inventorymanagement.exception.ProcessOrderException;
import com.example.inventorymanagement.exception.RetrieveProductsException;
import com.example.inventorymanagement.model.*;
import com.example.inventorymanagement.service.WarehouseManagementService;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class WarehouseServiceImpl implements WarehouseManagementService {
    private final Gson gson = new Gson();
    private static final Logger LOGGER = LoggerFactory.getLogger(WarehouseServiceImpl.class);

    /**
     * <B>Description :</B> Invoked to import articles from JSON file
     *
     * @return List<Article>
     * @throws ImportArticlesException if there's an issue importing articles
     */
    public List<Article> importArticles() throws ImportArticlesException {
        try (FileReader reader = new FileReader("src/main/resources/articles.json")) {
            JsonObject jsonObject = gson.fromJson(reader, JsonObject.class);
            JsonArray articlesArray = jsonObject.getAsJsonArray("articles");
            return gson.fromJson(articlesArray, new TypeToken<List<Article>>() {
            }.getType());
        } catch (Exception e) {
            LOGGER.error("Error while importing article: " + e.getMessage(), e);
            throw new ImportArticlesException("Failed to read JSON file", e);
        }
    }

    /**
     * <B>Description :</B> Invoked to import products from JSON file
     *
     * @return List<Product>
     * @throws ImportProductsException if there's an issue importing products
     */

    public List<Product> importProducts() throws ImportProductsException {
        try (FileReader reader = new FileReader("src/main/resources/products.json")) {
            JsonObject jsonObject = gson.fromJson(reader, JsonObject.class);
            JsonArray productsArray = jsonObject.getAsJsonArray("products");
            return gson.fromJson(productsArray, new TypeToken<List<Product>>() {
            }.getType());
        } catch (Exception e) {
            LOGGER.error("Error while importing products: " + e.getMessage(), e);
            throw new ImportProductsException("Failed to read JSON file", e);
        }
    }


    /**
     * <B>Description :</B> Invoked to fetch all available products and quantities
     *
     * @return Map<String, Integer>
     * @throws RetrieveProductsException if there's an issue in retrieving products
     */
    @Override
    public Map<String, Integer> retrieveAllProducts() throws RetrieveProductsException {
        try {
            List<Product> products = importProducts();
            return products.stream()
                    .collect(Collectors.toMap(
                            Product::getName,
                            Product::getStock
                    ));
        } catch (Exception e) {
            LOGGER.error("Error while retrieving products: " + e.getMessage(), e);
            throw new RetrieveProductsException("Failed to retrieve products: " + e.getMessage());
        }
    }

    /**
     * <B>Description :</B> Invoked to add a new product
     *
     * @param request AddProductRequest
     * @return Boolean to indicate operation success or not
     * @throws IOException if there's an issue in importing products
     */
    public Boolean updateOrAddProduct(AddProductRequest request) throws IOException {
        try {
            List<Article> articles = importArticles();
            List<Product> products = importProducts();
            Map<String, Article> articleStockMap = new HashMap<>();
            Set<String> productName = new HashSet<>();
            for (Article article : articles) {
                articleStockMap.put(article.getArt_id(), article);
            }
            for (Product product : products) {
                productName.add(product.getName());
            }
            for (ContainArticle articleQuantity : request.getArticleQuantities()) {
                Article article = articleStockMap.get(articleQuantity.getArt_id());
                if (article == null || article.getStock() < (request.getStock() * articleQuantity.getAmount_of())) {
                    return false;
                }
            }
            boolean containsProduct = productName.contains(request.getName());
            if (containsProduct) {
                for (Product product : products) {
                    if (product.getName().equals(request.getName())) {
                        product.setStock(product.getStock() + request.getStock());
                        updateArticleStock(articleStockMap, request);
                        break;
                    }
                }
            } else {
                Product product = new Product();
                product.setName(request.getName());
                product.setContain_articles(request.getArticleQuantities());
                product.setPrice(request.getPrice());
                products.add(product);
                updateArticleStock(articleStockMap, request);
            }
            return true;
        } catch (Exception e) {
            LOGGER.error("Error while adding/updating products: " + e.getMessage(), e);
            throw new IOException("Failed to get products: " + e.getMessage());
        }
    }

    /**
     * <B>Description :</B> Invoked to update article count
     */
    private void updateArticleStock(Map<String, Article> articleStockMap, AddProductRequest request) {
        for (ContainArticle articleQuantity : request.getArticleQuantities()) {
            Article article = articleStockMap.get(articleQuantity.getArt_id());
            article.setStock(article.getStock() - (articleQuantity.getAmount_of() * request.getStock()));
        }
    }

    /**
     * <B>Description :</B> Invoked to add a new article
     *
     * @param request AddArticleRequest
     * @return List of articles after the operation
     * @throws IOException if there's an issue importing articles
     */
    public List<Article> updateOrAddArticle(AddArticleRequest request) throws IOException {
        try {
            List<Article> articles = importArticles();
            Article existingArticle = articles.stream()
                    .filter(article -> article.getName().equals(request.getName()))
                    .findFirst()
                    .orElse(null);
            if (existingArticle != null) {
                existingArticle.setStock(existingArticle.getStock() + request.getStock());
            } else {
                Article newArticle = new Article();
                newArticle.setArt_id(String.valueOf(articles.size() + 1));
                newArticle.setName(request.getName());
                newArticle.setStock(request.getStock());
                articles.add(newArticle);
            }
            return articles;
        } catch (Exception e) {
            LOGGER.error("Error while adding/updating article: " + e.getMessage(), e);
            throw new IOException("Failed to update or add article: " + e.getMessage());
        }
    }

    /**
     * <B>Description :</B> Invoked to process an order based on quantity
     *
     * @param request BuyProductRequest
     * @return Boolean to indicate operation success or not
     * @throws ProcessOrderException if there's an issue in processing any order
     */
    public Boolean processOrder(BuyProductRequest request) throws ProcessOrderException {
        try {
            List<RequiredProduct> requiredProductList = request.getRequiredProductList();
            List<Product> productList = importProducts();
            Map<String, Product> productMap = new HashMap<>();

            for (Product product : productList) {
                productMap.put(product.getName(), product);
            }
            for (RequiredProduct requiredProduct : requiredProductList) {
                Product product = productMap.get(requiredProduct.getName());
                if (product != null && product.getStock() >= requiredProduct.getQuantity()) {
                    product.setStock(product.getStock() - requiredProduct.getQuantity());
                } else {
                    return false;
                }
            }
            return true;
        } catch (Exception e) {
            LOGGER.error("Error while processing your order: " + e.getMessage(), e);
            throw new ProcessOrderException("Failed to process your order: " + e.getMessage());
        }
    }
}
