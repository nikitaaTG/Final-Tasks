package org.example.FinalProject.repositories;

import org.example.FinalProject.models.AddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressRepository extends JpaRepository<AddressEntity, Long> {

    List<AddressEntity> findAllByUserId(Long id);
}
