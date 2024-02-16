package service;

import model.Product;
import model.Category;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class ProductService {
    private static final AtomicInteger idCounter = new AtomicInteger();
    private final List<Product> products;
    private final DiscountService discountService = new DiscountService();

    public ProductService() {
        products = new ArrayList<>();
        addProduct(new Product("Iphone", 10.99, Category.SMARTPHONE));
        addProduct(new Product("MacBookPro", 15.99, Category.NOTEBOOK));
        addProduct(new Product("Asus", 20.99, Category.NOTEBOOK));
        addProduct(new Product("Hamburger", 8.99, Category.FOOD));
        addProduct(new Product("Pepsi-Cola", 2.99, Category.DRINK));
    }

    public List<Product> getAllProducts() {
        return products.stream()
                .peek(this::setProductPriceWithDiscount)
                .toList();
    }

    public Product getProductById(Long id) {
        return products.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .map(product -> {
                    setProductPriceWithDiscount(product);
                    return product;
                })
                .orElseGet(() -> {
                    System.out.println("Product not found");
                    return null;
                });
    }

    private void setProductPriceWithDiscount(Product product) {
        double priceWithDiscount = getPriceWithDiscount(product);
        product.setPrice(priceWithDiscount);
    }

    private double getPriceWithDiscount(Product product) {
        double priceWithDiscount = discountService.getPriceWithDiscount(product.getName());
        if (product.getCategory() == Category.NOTEBOOK) {
            double categoryDiscount = 0.1;
            priceWithDiscount -= priceWithDiscount * categoryDiscount;
        }
        return priceWithDiscount;
    }

    public void addProduct(Product newProduct) {
        newProduct.setId(generateProductId());
        setProductPriceWithDiscount(newProduct);
        products.add(newProduct);
    }

    public void updateProduct(Product updatedProduct) {
        products.stream()
                .filter(product -> product.getId().equals(updatedProduct.getId()))
                .findFirst()
                .ifPresent(product -> {
                    setProductPriceWithDiscount(updatedProduct);
                    product.setId(updatedProduct.getId());
                    product.setName(updatedProduct.getName());
                    product.setPrice(updatedProduct.getPrice());
                    product.setCategory(updatedProduct.getCategory());
                });
    }

    public void deleteProduct(Long id) {
        products.removeIf(product -> product.getId().equals(id));
    }

    private Long generateProductId() {
        return (long) idCounter.incrementAndGet();
    }
}
