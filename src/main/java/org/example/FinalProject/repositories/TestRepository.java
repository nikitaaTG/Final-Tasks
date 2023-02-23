package org.example.FinalProject.repositories;

import org.example.FinalProject.models.Product;
import org.example.FinalProject.models.Test;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TestRepository extends JpaRepository<Test, Long> {
List<Test> findByName(String Name);
List<Test> findAll();
}
