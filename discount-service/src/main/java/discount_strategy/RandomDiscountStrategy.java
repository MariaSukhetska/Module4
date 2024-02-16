package discount_strategy;

import java.util.concurrent.ThreadLocalRandom;

public class RandomDiscountStrategy implements DiscountStrategy {
    @Override
    public double calculateDiscount(String productName) {
        return ThreadLocalRandom.current().nextDouble(0.05, 0.2);
    }
}
