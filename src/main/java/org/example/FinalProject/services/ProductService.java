package org.example.FinalProject.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.FinalProject.dto.ProductDTO;
import org.example.FinalProject.models.CategoryEntity;
import org.example.FinalProject.models.ProductEntity;
import org.example.FinalProject.repositories.CategoryRepository;
import org.example.FinalProject.repositories.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {
    private final ProductRepository productRepository;

    private final CategoryRepository categoryRepository;
    private  CategoryEntity category;

    public CategoryEntity addCategory(String title) {
        if (title != null && !title.isEmpty()) {
            String lowerCaseTitle = title.trim().toLowerCase(Locale.ROOT);
            return categoryRepository.save(new CategoryEntity(lowerCaseTitle));

        }
        throw new IllegalArgumentException("Title is empty or null");
    }

    public List<CategoryEntity> getAllCategories() {
        return categoryRepository.findAll();
    }

    public CategoryEntity getCategoryById(Long id) {
        return categoryRepository.findById(id).orElseThrow();
    }


    public List<ProductEntity> listProductsByTitle(String title) {
        if (title != null)
            return productRepository.findByTitle(title);
        else throw new IllegalArgumentException("Title is null");
    }

    public Page<ProductEntity> listProducts(Pageable pageable) {
        Page<ProductEntity> pages = productRepository.findAll(pageable);
        return pages;
    }

    public Page<ProductEntity> listProductsByCategory(Long categoryId, Pageable pageable){
        category = categoryRepository.findById(categoryId).orElseThrow();
        Page<ProductEntity> pagesInCategory = productRepository.findAllByCategory(category, pageable);
        return pagesInCategory;
    }

    public void saveProduct(ProductDTO product) {
        log.info("Saving new {}", product);
        CategoryEntity categoryEntity = categoryRepository.findById(product.getCategoryId()).orElseThrow(RuntimeException::new);
        ProductEntity newProduct = new ProductEntity();
        newProduct.setPrice(product.getPrice());
        newProduct.setTitle(product.getTitle());
        newProduct.setLeftInStock(product.getLeftInStock());
        newProduct.setCategory(categoryEntity);
        productRepository.save(newProduct);
    }

    public void updateProduct(long id, ProductDTO updatedProduct) {
        ProductEntity productToUpdate = productRepository.findById(id).orElseThrow(RuntimeException::new);
        log.info("Changed product {}", productToUpdate);
        CategoryEntity categoryEntity = categoryRepository.findById(updatedProduct.getCategoryId()).orElseThrow(RuntimeException::new);
        productToUpdate.setTitle(updatedProduct.getTitle());
        productToUpdate.setPrice(updatedProduct.getPrice());
        productToUpdate.setCategory(categoryEntity);
        productToUpdate.setLeftInStock(updatedProduct.getLeftInStock());
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    public ProductEntity getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }
}
