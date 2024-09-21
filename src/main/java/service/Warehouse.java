package service;

import entities.*;

import java.util.ArrayList;
import java.util.List;

public class Warehouse {
    private final List<Product> products = new ArrayList<>();

    public void addProducts(Product product) {
        if (products.stream().anyMatch(p -> p.id().equals(product.id()))) {
            throw new IllegalArgumentException("Product already exists");
        }
        products.add(product);
    }

    public List<Product> getAllProducts() {
        return List.copyOf(products);
    }

    public List<Product> getProductsById(String id) {
        return products.stream()
                .filter(p -> p.id().equals(id))
                .toList();
    }
}