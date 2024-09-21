package service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;
import entities.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class WarehouseTest {

    private Warehouse warehouse;

    @BeforeEach
    void setUp() {
        warehouse = new Warehouse();
        LocalDateTime dateTime = LocalDateTime.of(2024, 9, 21, 5, 0);
        warehouse.addProducts(new Product("1", "Laptop", Category.ELECTRONICS, 10, dateTime, null));
        warehouse.addProducts(new Product("2", "Phone", Category.ELECTRONICS, 9, dateTime, null));
        warehouse.addProducts(new Product("3", "Tablet", Category.ELECTRONICS, 7, dateTime, null));
    }

    @Test
    void testAddProducts() {
        LocalDateTime now = LocalDateTime.now();
        warehouse.addProducts(new Product("4", "Desktop", Category.ELECTRONICS, 9, now.minusDays(1), null));
        Product product = warehouse.getProductById("4");

        assertEquals("4", product.id());
    }

    @Test
    void testAddProductsWithMissingAttributes() {
        LocalDateTime now = LocalDateTime.now();
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            warehouse.addProducts(new Product("5", "", Category.ELECTRONICS, 5, now.minusDays(1), null));
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

    }

    @Test
    void getProductByExistingId_returnsMatchingProduct() {
        Product result = warehouse.getProductById("2");

        assertEquals("2", result.id());
        assertEquals("Phone", result.name());
    }

    @Test
    void getProductById_nonExistingId_throwsAnException() {

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            warehouse.getProductById("99");
        });
        assertEquals("Product with id 99 not found", exception.getMessage());
    }
}
