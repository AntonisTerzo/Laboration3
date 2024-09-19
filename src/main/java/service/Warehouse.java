package service;
import entities.*;

import java.util.ArrayList;
import java.util.List;

public class Warehouse {
    private final List<Product> products = new ArrayList<>();

    public void addProducts(Product product) {
        products.add(product);
    }

    public List<Product> getAllProducts() {
        return List.copyOf(products);
    }
}
