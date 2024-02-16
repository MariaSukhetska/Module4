package discount_strategy;

import java.time.DayOfWeek;
import java.time.LocalDate;

public class DayOfWeekDiscountStrategy implements DiscountStrategy {
    @Override
    public double calculateDiscount(String productName) {
        DayOfWeek dayOfWeek = LocalDate.now().getDayOfWeek();
        return switch (dayOfWeek) {
            case MONDAY, WEDNESDAY, FRIDAY -> 0.15;
            case TUESDAY, THURSDAY -> 0.1;
            case SATURDAY, SUNDAY -> 0.2;
            default -> 0.05;
        };
    }
}
