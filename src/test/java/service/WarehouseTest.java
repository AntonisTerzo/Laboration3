package service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;
import entities.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class WarehouseTest {

    private Warehouse warehouse;

    @BeforeEach
    void setUp() {
        warehouse = new Warehouse();
        LocalDateTime dateTime = LocalDateTime.of(2024, 9, 21, 11, 59);
        LocalDateTime dateTime2 = LocalDateTime.of(2024, 9, 17, 11, 59);
        warehouse.addProducts(new Product("1", "Phone", Category.ELECTRONICS, 10, dateTime, null));
        warehouse.addProducts(new Product("2", "Laptop", Category.ELECTRONICS, 9, dateTime2, null));
        warehouse.addProducts(new Product("3", "Tablet", Category.ELECTRONICS, 7, dateTime, null));
        warehouse.addProducts(new Product("4", "Thai", Category.FOOD, 7, dateTime2, null));
        warehouse.addProducts(new Product("5", "Greek", Category.FOOD, 10, dateTime, null));
        warehouse.addProducts(new Product("6", "Italian", Category.FOOD, 9, dateTime2, null));
    }

    @Test
    void testAddProducts() {
        LocalDateTime now = LocalDateTime.now();
        warehouse.addProducts(new Product("7", "Desktop", Category.ELECTRONICS, 9, now.minusDays(1), null));
        Product product = warehouse.getProductById("7");

        assertEquals("7", product.id());
    }

    @Test
    void testAddProductsWithMissingAttributes() {
        LocalDateTime now = LocalDateTime.now();
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            warehouse.addProducts(new Product("8", "", Category.ELECTRONICS, 5, now.minusDays(1), null));
        });
        assertEquals("Name cannot be null or empty", exception.getMessage());
    }

    @Test
    void testModifyProduct() {
        Product originalProduct = warehouse.getProductById("2");
        LocalDateTime originalModifiedDate = originalProduct.modifiedDate();

        warehouse.modifyProduct("2", "Smartphone", Category.ELECTRONICS, 10);
        Product modifiedProduct = warehouse.getProductById("2");

        assertEquals("Smartphone", modifiedProduct.name());
        assertEquals(Category.ELECTRONICS, modifiedProduct.category());
        assertEquals(10, modifiedProduct.rating());

        assertTrue(modifiedProduct.modifiedDate().isAfter(originalModifiedDate));
        assertEquals(originalProduct.createdDate(), modifiedProduct.createdDate());
    }

    @Test
    void testModifyingANonExistentProduct() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            warehouse.modifyProduct("999", "NonExistent", Category.ELECTRONICS, 5);
        });
        assertEquals("Product with id 999 not found", exception.getMessage());
    }

    @Test
    void testGetAllProducts() {
        List<Product> allProducts = warehouse.getAllProducts();

        assertEquals(6, allProducts.size());
        assertTrue(allProducts.stream().anyMatch(p -> p.id().equals("1")));
        assertTrue(allProducts.stream().anyMatch(p -> p.id().equals("2")));
        assertTrue(allProducts.stream().anyMatch(p -> p.id().equals("3")));
    }

    @Test
    void testToModifyTheList_throwsAnException() {
        List<Product> allProducts = warehouse.getAllProducts();

        assertThrows(UnsupportedOperationException.class, () -> {
            allProducts.add(new Product("4", "New Product", Category.ELECTRONICS, 8, LocalDateTime.now(), null));
        });
    }

    @Test
    void getProductByExistingId_returnsMatchingProduct() {
        Product result = warehouse.getProductById("2");

        assertEquals("2", result.id());
        assertEquals("Laptop", result.name());
    }

    @Test
    void getProductById_nonExistingId_throwsAnException() {

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            warehouse.getProductById("99");
        });
        assertEquals("Product with id 99 not found", exception.getMessage());
    }

    @Test
    void getAllProductsByCategoryAndSortedByName() {
        List<Product> electronicsProducts = warehouse.getProductsByCategory(Category.ELECTRONICS);
        
        assertEquals(3, electronicsProducts.size());
        assertEquals("Laptop", electronicsProducts.get(0).name());
        assertEquals("Phone", electronicsProducts.get(1).name());
        assertEquals("Tablet", electronicsProducts.get(2).name());
        
        List<Product> foodProducts = warehouse.getProductsByCategory(Category.FOOD);
        
        assertEquals(3, foodProducts.size());
        assertEquals("Greek", foodProducts.get(0).name());
        assertEquals("Italian", foodProducts.get(1).name());
        assertEquals("Thai", foodProducts.get(2).name());
    }

    @Test
    void testGetProductsCreatedAfter() {
        LocalDateTime cutoffDate = LocalDateTime.of(2024, 9, 20, 0, 0);
        List<Product> recentProducts = warehouse.getProductsCreatedAfter(cutoffDate);

        assertEquals(3, recentProducts.size());
        assertTrue(recentProducts.stream().allMatch(p -> p.createdDate().isAfter(cutoffDate)));
        assertTrue(recentProducts.stream().anyMatch(p -> p.id().equals("1")));
        assertTrue(recentProducts.stream().anyMatch(p -> p.id().equals("3")));
        assertTrue(recentProducts.stream().anyMatch(p -> p.id().equals("5")));
    }
    
    @Test
    void testGetProductsModifiedSinceCreation() {
        // Modify an existing product
        warehouse.modifyProduct("2", "Modified Laptop", Category.ELECTRONICS, 10);

        List<Product> modifiedProducts = warehouse.getProductsModifiedSinceCreation();

        assertEquals(1, modifiedProducts.size());
        Product modifiedProduct = modifiedProducts.get(0);
        assertEquals("2", modifiedProduct.id());
        assertEquals("Modified Laptop", modifiedProduct.name());
        assertTrue(modifiedProduct.modifiedDate().isAfter(modifiedProduct.createdDate()));
    }
    
}
