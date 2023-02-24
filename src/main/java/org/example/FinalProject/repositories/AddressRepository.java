package org.example.FinalProject.repositories;

import org.example.FinalProject.models.AddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;

public interface AddressRepository extends JpaRepository<AddressEntity, Long> {
}
