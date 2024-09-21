package service;

import entities.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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
            LocalDateTime.now()
        );

        products.set(products.indexOf(product), modifiedProduct);
    }

    public List<Product> getAllProducts() {
        return Collections.unmodifiableList(products);
    }

    public Product getProductById(String id) {
        return Collections.unmodifiableList(products).stream()
                .filter(p -> p.id().equals(id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Product with id " + id + " not found"));
    }

    public List<Product> getProductsByCategory(Category category) {
        return Collections.unmodifiableList(products).stream()
        .filter(c -> c.category().equals(category))
        .sorted(Comparator.comparing(Product::name))
        .collect(Collectors.toList());
    }
}