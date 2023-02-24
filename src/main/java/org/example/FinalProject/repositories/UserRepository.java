package org.example.FinalProject.repositories;

import org.example.FinalProject.models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
}
