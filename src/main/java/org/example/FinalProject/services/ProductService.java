package org.example.FinalProject.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.FinalProject.dto.ProductDTO;
import org.example.FinalProject.mappers.ProductMapper;
import org.example.FinalProject.models.CategoryEntity;
import org.example.FinalProject.models.ProductEntity;
import org.example.FinalProject.repositories.CategoryRepository;
import org.example.FinalProject.repositories.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {
    private final ProductRepository productRepository;

    private final CategoryRepository categoryRepository;

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
        return categoryRepository.findById(id).orElseThrow(RuntimeException::new);
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
        CategoryEntity category = categoryRepository.findById(categoryId).orElseThrow(RuntimeException::new);
        Page<ProductEntity> pagesInCategory = productRepository.findAllByCategory(category, pageable);
        return pagesInCategory;
    }

    public void saveProduct(ProductDTO product) {
        log.info("Saving new {}", product);
        ProductEntity newProduct = ProductMapper.INSTANCE.productDTOToEntity(product);
        productRepository.save(newProduct);
    }

    public void updateProduct(long id, ProductDTO updatedProduct) {
        String title = updatedProduct.getTitle();
        double price = updatedProduct.getPrice();
        int leftInStock = updatedProduct.getLeftInStock();
        long categoryId=updatedProduct.getCategory().getId();
        productRepository.updateProduct(title, price, leftInStock, categoryId, id);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    public ProductEntity getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }
}
