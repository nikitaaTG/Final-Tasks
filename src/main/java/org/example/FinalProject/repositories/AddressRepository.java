package org.example.FinalProject.repositories;

import org.example.FinalProject.models.AddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface AddressRepository extends JpaRepository<AddressEntity, Long> {

    List<AddressEntity> findAllByUserId(Long id);

    @Modifying
    @Query(value = "UPDATE webMarket2.address SET country = :country, city = :city, " +
            "post_index = :postIndex, street = :street, home = :home, apartment = :apartment " +
            "WHERE id = :id ;",
            nativeQuery = true)
    int updateAddress(@Param("country") String country, @Param("city") String city, @Param("postIndex") int postIndex, @Param("street") String street, @Param("home") int home, @Param("apartment") int apartment, @Param("id") long id);
}
