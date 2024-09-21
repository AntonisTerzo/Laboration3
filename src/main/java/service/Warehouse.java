package service;

import entities.*;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

public class Warehouse {
    private final List<Product> products = new ArrayList<>();

    public void addProducts(Product product) {
        if (products.stream().anyMatch(p -> p.id().equals(product.id()))) {
            throw new IllegalArgumentException("Product already exists");
        }
        products.add(product);
    }

    public void modifyProduct(String id, String newName, Category newCategory, int newRating) {
        Product product = products.stream()
                .filter(p -> p.id().equals(id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Product with id " + id + " not found"));
        
        Product modifiedProduct = new Product(
            id,
            newName,
            newCategory,
            newRating,
            product.createdDate(),
            LocalDate.now()
        );

        products.set(products.indexOf(product), modifiedProduct);
    }

    public List<Product> getAllProducts() {
        return List.copyOf(products);
    }

    public Product getProductsById(String id) {
        return products.stream()
                .filter(p -> p.id().equals(id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Product with id " + id + " not found"));
    }
}