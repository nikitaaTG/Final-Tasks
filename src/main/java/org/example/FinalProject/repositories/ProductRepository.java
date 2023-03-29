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


    /**
     * Method for updating product information in DB
     *
     * @param title
     * @param price
     * @param leftInStock
     * @param description
     * @param id
     */
    @Modifying
    @Query(value = "UPDATE webMarket2.product SET title = :title, price = :price, left_in_stock = :leftInStock, description = :description WHERE id = :id ;",
            nativeQuery = true)
    int updateProduct(@Param("title") String title, @Param("price") double price, @Param("leftInStock") int leftInStock, @Param("description") String description, @Param("id") Long id);


    /**
     * Method for updating product's category  in DB
     *
     * @param category_id
     * @param id
     */
    @Modifying
    @Query(value = "UPDATE webMarket2.product SET category_id = :category_id WHERE id = :id ;",
            nativeQuery = true)
    int updateProductCategory(@Param("category_id") long category_id, @Param("id") Long id);

    /**
     * Method for reducing of amount in stock in DB
     *
     * @param id
     */
    @Modifying
    @Query(value = "UPDATE webMarket2.product SET left_in_stock = left_in_stock-1 WHERE id = :id ;",
            nativeQuery = true)
    int reduceAmount(@Param("id") Long id);
}
