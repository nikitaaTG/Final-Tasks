package org.example.FinalProject.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.FinalProject.models.Product;
import org.example.FinalProject.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {
    private final ProductRepository productRepository;


    public List<Product> listProducts(String title) {
        if (title!=null)
            productRepository.findByTitle(title);
    return productRepository.findAll();
    }


    public void saveProduct(Product product) {
        log.info("Saving new {}", product);
        productRepository.save(product);
    }

    public void deleteProduct (Long id){
        productRepository.deleteById(id);
        ;
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }
}
