package org.example.FinalProject.repositories;

import org.example.FinalProject.models.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;

public interface AddressRepository extends JpaRepository<Address, BigInteger> {
}
