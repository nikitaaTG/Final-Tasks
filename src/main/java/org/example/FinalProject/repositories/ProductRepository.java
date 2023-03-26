package org.example.FinalProject.repositories;

import java.util.List;

import org.example.FinalProject.models.CategoryEntity;
import org.example.FinalProject.models.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;

@Repository
@Transactional
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    List<ProductEntity> findByTitleContainingIgnoreCase(String title);

    Page<ProductEntity> findAll(Pageable pageable);

    Page<ProductEntity> findAllByCategory(CategoryEntity category, Pageable pageable);

    // FIXME: не уверен, но если у тебя тут перечислены все поля, то просто используй save() от JpaRepository
    @Modifying
    @Query(value = "UPDATE webMarket2.product SET title = :title, price = :price, left_in_stock = :leftInStock, category_id = :category_id WHERE id = :id ;", nativeQuery = true)
    int updateProduct(@Param("title") String title, @Param("price") double price, @Param("leftInStock") int leftInStock, @Param("category_id") long category_id, @Param("id") Long id);
}
