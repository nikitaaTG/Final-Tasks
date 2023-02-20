package org.example.FinalProject.DAO;

import org.example.FinalProject.models.Product;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProductDAO {
     List<Product> products;
     long id=0;
    {
        products = new ArrayList<>();
        products.add(new Product(++id, "Test", "TestTestTest", 123.2, "Test", "Test"));
        products.add(new Product(++id, "Test2", "TestTestTest", 123.2, "Test", "Test"));
        products.add(new Product(++id, "Test3", "TestTestTest", 123.2, "Test", "Test"));
        products.add(new Product(++id, "Test4", "TestTestTest", 123.2, "Test", "Test"));
    }

    public List<Product> index() {
        return products;
    }

    public void save(Product product) {
        product.setId(++id);
        products.add(product);
    }


    public Product show(long id) {

        return products.stream().filter(product -> product.getId() == id).findAny().orElse(null);
    }

    public void update(long id, Product updatedProduct) {
        Product productToUpdate = show(id);

        productToUpdate.setTitle(updatedProduct.getTitle());
        productToUpdate.setPrice(updatedProduct.getPrice());
        productToUpdate.setDescription(updatedProduct.getDescription());
        productToUpdate.setCity(updatedProduct.getCity());
        productToUpdate.setAuthor(updatedProduct.getAuthor());
    }

    public void delete(long id) {
        products.removeIf(product -> product.getId() == id);
    }
}
