package org.example.FinalProject.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.FinalProject.models.CategoryEntity;
import org.example.FinalProject.repositories.CategoryRepository;
import org.example.FinalProject.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryService {
    private final ProductRepository productRepository;

    private final CategoryRepository categoryRepository;

    /**
     * Method for adding a new category.
     *
     * @param title
     */
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

}
