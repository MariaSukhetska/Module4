package model;

import lombok.Data;
import java.util.concurrent.atomic.AtomicInteger;

@Data
public class Product {
    private static final AtomicInteger idCounter = new AtomicInteger();
    private Long id;
    private String name;
    private double price;
    private Category category;

    public Product(String name, double price, Category category) {
        this.name = name;
        this.price = price;
        this.category = category;
    }
}
