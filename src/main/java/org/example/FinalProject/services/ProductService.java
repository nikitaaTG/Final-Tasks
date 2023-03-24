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
import org.springframework.data.domain.PageImpl;
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


    public Page<ProductDTO> listProductsByTitle(String title, Pageable pageable) {
        if (title != null) {
            Page<ProductDTO> pageDto = (transformListToPage(productRepository.findByTitleContainingIgnoreCase(title), pageable).map(ProductMapper.INSTANCE::productEntityToDTO));
            return pageDto;
        }
        else throw new IllegalArgumentException("Title is null");
    }

    public Page<ProductDTO> listProducts(Pageable pageable) {
        Page<ProductDTO> pages = productRepository.findAll(pageable).map(ProductMapper.INSTANCE::productEntityToDTO);
        return pages;
    }

    public Page<ProductDTO> listProductsByCategory(Long categoryId, Pageable pageable){
        CategoryEntity category = categoryRepository.findById(categoryId).orElseThrow(RuntimeException::new);
        Page<ProductDTO> pagesInCategory = productRepository.findAllByCategory(category, pageable).map(ProductMapper.INSTANCE::productEntityToDTO);
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
        long categoryId = updatedProduct.getCategory().getId();
        String description = updatedProduct.getDescription();
        productRepository.updateProduct(title, price, leftInStock, description, categoryId, id);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    public ProductEntity getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    public Page<ProductEntity> transformListToPage(List<ProductEntity> list, Pageable pageable) {
        final int start = (int) pageable.getOffset();
        final int end = Math.min((start + pageable.getPageSize()), list.size());
        final Page<ProductEntity> page = new PageImpl<>(list.subList(start, end), pageable, list.size());
        return page;
    }
}
