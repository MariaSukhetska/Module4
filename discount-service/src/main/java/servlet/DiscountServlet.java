package servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import discount_strategy.DayOfWeekDiscountStrategy;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Discount;
import service.DiscountService;
import java.io.IOException;

@WebServlet("/discount")
public class DiscountServlet extends HttpServlet {
    private final DiscountService discountService = new DiscountService(new DayOfWeekDiscountStrategy());
    private static final ObjectMapper mapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String productName = req.getParameter("productName");
        double discountPrice = discountService.getPriceWithDiscount(productName);
        Discount discount = new Discount(discountPrice);
        String jsonString = mapper.writeValueAsString(discount);
        resp.setContentType("application/json");
        resp.getWriter().println(jsonString);
    }
}
