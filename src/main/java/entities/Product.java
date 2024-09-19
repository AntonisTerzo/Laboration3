package entities;

import java.time.LocalDate;

public record Product(String id, String name, Category category, int rating, LocalDate date, LocalDate modifiedDate) {
     public Product {
         if (name == null || name.isBlank()) {
             throw new IllegalArgumentException("Name cannot be null or empty");
         }
         if (date == null) {
             throw new IllegalArgumentException("Date cannot be null");
         }
         if (modifiedDate == null) {
             modifiedDate = date;
         }
     }
}
