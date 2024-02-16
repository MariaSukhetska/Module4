package servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import model.Category;
import model.Product;
import service.ProductService;
import java.io.IOException;
import java.util.List;

@WebServlet("/products")
public class ProductServlet extends HttpServlet {
    private ProductService productService;
    private static final ObjectMapper mapper = new ObjectMapper();

    @Override
    public void init() throws ServletException {
        productService = new ProductService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Product> products = productService.getAllProducts();
        String jsonResponse = convertToJson(products);
        response.setContentType("application/json");
        response.getWriter().write(jsonResponse);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String name = request.getParameter("name");
        double price = Double.parseDouble(request.getParameter("price"));
        Category category = Category.valueOf(request.getParameter("category"));
        Product newProduct = new Product(name, price, category);
        productService.addProduct(newProduct);
        response.setStatus(HttpServletResponse.SC_CREATED);
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String name = request.getParameter("name");
        double price = Double.parseDouble(request.getParameter("price"));
        Category category = Category.valueOf(request.getParameter("category"));
        Product updatedProduct = new Product(name, price, category);
        productService.updateProduct(updatedProduct);
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        long id = Long.parseLong(request.getParameter("id"));
        productService.deleteProduct(id);
    }

    @SneakyThrows
    private String convertToJson(List<Product> products) {
        return mapper.writeValueAsString(products);
    }
}
