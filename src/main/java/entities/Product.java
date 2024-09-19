package entities;

import java.time.LocalDate;

public record Product(String id, String name, Category category, int rating, LocalDate createdDate, LocalDate modifiedDate) {
     public Product {
         if (name == null || name.isBlank()) {
             throw new IllegalArgumentException("Name cannot be null or empty");
         }
         if (rating < 0 || rating > 10) {
             throw new IllegalArgumentException("Rating must be between 0 and 10");
         }
         if (createdDate == null) {
             throw new IllegalArgumentException("Date cannot be null");
         }
         if (modifiedDate == null) {
             modifiedDate = createdDate;
         }
     }
}