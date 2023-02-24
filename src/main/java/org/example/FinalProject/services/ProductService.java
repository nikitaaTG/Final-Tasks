package org.example.FinalProject.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.FinalProject.dto.ProductDTO;
import org.example.FinalProject.models.CategoryEntity;
import org.example.FinalProject.models.ProductEntity;
import org.example.FinalProject.repositories.CategoryRepository;
import org.example.FinalProject.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {
    private final ProductRepository productRepository;

    private final CategoryRepository categoryRepository;

    public CategoryEntity addCategory(String title){
        if (title!=null && !title.isEmpty()){
            String lowerCaseTitle = title.trim().toLowerCase(Locale.ROOT);
            return categoryRepository.save(new CategoryEntity(lowerCaseTitle));

        }
        throw new IllegalArgumentException("Title is empty or null");
    }

    public List<CategoryEntity> getAllCategories(){
        return categoryRepository.findAll();
    }


    public List<ProductEntity> listProductsByTitle(String title) {
        if (title!=null)
          return productRepository.findByTitle(title);
        else throw new IllegalArgumentException("Title is null");
    }

    public List<ProductEntity> listProducts() {

    return productRepository.findAll();
    }


    public void saveProduct(ProductDTO product) {
        log.info("Saving new {}", product);
        CategoryEntity categoryEntity = categoryRepository.findById(product.getCategoryId()).orElseThrow(RuntimeException::new);
        ProductEntity productEntity= new ProductEntity();
        productEntity.setPrice(product.getPrice());
        productEntity.setTitle(product.getTitle());
        productEntity.setLeftInStock(product.getLeftInStock());
        productEntity.setCategory(categoryEntity);
        productRepository.save(productEntity);
    }

    public void deleteProduct (Long id){
        productRepository.deleteById(id);
        ;
    }

    public ProductEntity getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }
}
