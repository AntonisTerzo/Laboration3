package service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import entities.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class WarehouseTest {

    private Warehouse warehouse;

    @BeforeEach
    void setUp() {
        warehouse = new Warehouse();
        LocalDate now = LocalDate.now();
        warehouse.addProducts(new Product("1", "Laptop", Category.ELECTRONICS, 8, now.minusDays(1), now));
        warehouse.addProducts(new Product("2", "Phone", Category.ELECTRONICS, 9, now.minusDays(1), now));
        warehouse.addProducts(new Product("3", "Tablet", Category.ELECTRONICS, 7, now.minusDays(1), now));
    }

    @Test
    void testAddProducts() {

    }

    @Test
    void testModifyProduct() {
        Product originalProduct = warehouse.getProductsById("2");
        
        warehouse.modifyProduct("2", "Smartphone", Category.ELECTRONICS, 10);
        Product modifiedProduct = warehouse.getProductsById("2");
        assertEquals("Smartphone", modifiedProduct.name());
        assertEquals(Category.ELECTRONICS, modifiedProduct.category());
        assertEquals(10, modifiedProduct.rating());

        assertTrue(modifiedProduct.modifiedDate().isAfter(originalProduct.createdDate()));
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
    void getsProductsByExistingId_returnsMatchingProduct() {
        Product result = warehouse.getProductsById("2");

        assertEquals("2", result.id());
        assertEquals("Phone", result.name());
    }

    @Test
    void getProductsById_nonExistingId_throwsAnException() {
        

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            warehouse.getProductsById("99");
        });
        assertEquals("Product with id 99 not found", exception.getMessage());   
     }
}
