package org.example.FinalProject.repositories;

import jakarta.transaction.Transactional;
import org.example.FinalProject.models.OrderEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {

    Page<OrderEntity> findAll(Pageable pageable);

    Page<OrderEntity> findByUserId(double userId, Pageable pageable);

    /**
     * Method for updating order information in DB
     *
     * @param paymentStatus
     * @param orderStatus
     * @param id
     */
    @Modifying
    @Query(value = "UPDATE webMarket2.orders SET payment_status = :paymentStatus, order_status = :orderStatus WHERE id = :id ;",
            nativeQuery = true)
    int updateOrder(@Param("paymentStatus") String paymentStatus, @Param("orderStatus") String orderStatus, @Param("id") long id);
}
