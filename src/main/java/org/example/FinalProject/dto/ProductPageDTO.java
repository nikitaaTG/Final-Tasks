package org.example.FinalProject.dto;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.example.FinalProject.models.ProductEntity;
import org.springframework.data.domain.Page;

public class ProductPageDTO { // FIXME: не используется - удали
    Integer totalPages;
    List<Integer> pageNumbers;
    List<ProductDTO> products;

    public ProductPageDTO(Page<ProductEntity> page) {
        pageNumbers = IntStream.rangeClosed(1, page.getTotalPages()).boxed().collect(Collectors.toList());
        totalPages = page.getTotalPages();
        products = page.get()
                .map(pe -> new ProductDTO(pe.getId(), pe.getTitle(), pe.getPrice(), pe.getLeftInStock(),
                        pe.getCategory()))
                .collect(Collectors.toList());
    }
}
