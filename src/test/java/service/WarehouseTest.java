package service;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import entities.*;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class WarehouseTest {

    private Warehouse warehouse;


    @BeforeEach
    void setUp() {
        warehouse = new Warehouse();
        LocalDate now = LocalDate.now();
        warehouse.addProducts(new Product("1", "Laptop", Category.ELECTRONICS, 8, now, now));
        warehouse.addProducts(new Product("2", "Phone", Category.ELECTRONICS, 9, now, now));
        warehouse.addProducts(new Product("3", "Tablet", Category.ELECTRONICS, 7, now, now));
    }

    @Test
    void testAddProducts() {
        
    }
    @Test
    void testModifyProduct() {

    }

    @Test
    void testGetAllProducts() {

    }

    @Test
    void getsProductsByExistingId_returnsMatchingProducts() {
        List<Product> result = warehouse.getProductsById("2");

        assertEquals(1, result.size());
        assertEquals("2", result.getFirst().id());
        assertEquals("Phone", result.getFirst().name());
    }
 @Test
    void getProductsById_nonExistingId_returnsEmptyList() {
        List<Product> result = warehouse.getProductsById("999");
        
        assertTrue(result.isEmpty());
    }
}
