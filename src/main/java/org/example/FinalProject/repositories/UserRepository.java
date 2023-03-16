package org.example.FinalProject.repositories;

import jakarta.transaction.Transactional;
import org.example.FinalProject.models.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Page<UserEntity> findAll(Pageable pageable);

    @Query(value = "SELECT * FROM webMarket2.user WHERE email_address = ?;",
            nativeQuery = true)
    UserEntity findByEmail(String email);

    @Modifying
    @Query(value = "UPDATE webMarket2.user SET name = :name, surname = :surname, date_of_birth = :birthDay, email_address = :email WHERE id = :id ;",
            nativeQuery = true)
    int updateUser(String name, String surname, Date birthDay, String email, Long id);

    @Modifying
    @Query(value = "UPDATE webMarket2.user SET password = :password WHERE id = :id ;",
            nativeQuery = true)
    int updatePassword(String password, long id);
}

