package org.example.FinalProject.repositories;

import jakarta.transaction.Transactional;
import org.example.FinalProject.models.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.Date;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Page<UserEntity> findAll (Pageable pageable);

    @Modifying
    @Query(value = "UPDATE webMarket2.user SET name = ?, surname = ?, date_of_birth = ?, email_address = ?, password = ?, WHERE id = ?;",
            nativeQuery = true)
    int updateUser(String name, String surname, Date birthDay, String email, String password, Long id);
}

