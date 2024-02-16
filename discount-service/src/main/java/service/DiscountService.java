package service;

import java.util.concurrent.ThreadLocalRandom;
import discount_strategy.DiscountStrategy;

public class DiscountService {
    private DiscountStrategy discountStrategy;

    public DiscountService(DiscountStrategy discountStrategy) {
        this.discountStrategy = discountStrategy;
    }

    public void setDiscountStrategy(DiscountStrategy discountStrategy) {
        this.discountStrategy = discountStrategy;
    }

    public double getPriceWithDiscount(String productName) {
        double basePrice = getBasePrice(productName);
        double discountPercentage = discountStrategy.calculateDiscount(productName);
        return basePrice * (1 - discountPercentage);
    }

    private double getBasePrice(String productName) {
        return ThreadLocalRandom.current().nextDouble(50.0, 100.0);
    }
}
