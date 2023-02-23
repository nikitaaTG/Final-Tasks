package org.example.FinalProject.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.FinalProject.models.Product;
import org.example.FinalProject.models.Test;
import org.example.FinalProject.repositories.TestRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TestService {
    private final TestRepository testRepository;

    public List<Test> listTest() {
    return testRepository.findAll();
    }


    public void saveProduct(Test test) {
        log.info("Saving new {}", test);
        testRepository.save(test);
    }

}
