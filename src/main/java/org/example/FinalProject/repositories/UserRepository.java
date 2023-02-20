package org.example.FinalProject.repositories;

import org.example.FinalProject.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;

public interface UserRepository extends JpaRepository<User, BigInteger> {
}
