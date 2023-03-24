package org.example.FinalProject.repositories;

import jakarta.transaction.Transactional;
import org.example.FinalProject.models.CategoryEntity;
import org.example.FinalProject.models.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    List<ProductEntity> findByTitleContainingIgnoreCase(String title);

    Page<ProductEntity> findAll(Pageable pageable);

    Page<ProductEntity> findAllByCategory(CategoryEntity category, Pageable pageable);

    @Modifying
    @Query(value = "UPDATE webMarket2.product SET title = :title, price = :price, left_in_stock = :leftInStock, description = :description, category_id = :category_id WHERE id = :id ;",
            nativeQuery = true)
    int updateProduct(@Param("title") String title, @Param("price") double price, @Param("leftInStock") int leftInStock, @Param("description") String description, @Param("category_id") long category_id, @Param("id") Long id);
}
