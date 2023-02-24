package org.example.FinalProject.services;

import org.example.FinalProject.models.AddressEntity;
import org.example.FinalProject.repositories.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;

@Service
public class AddressService {

    private final AddressRepository addressRepository;

    @Autowired
    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    public AddressEntity findByUserID (Long id) {
        return  addressRepository.getReferenceById(id);
    }

    public AddressEntity addAddress(AddressEntity addressEntity) {
        return  addressRepository.save(addressEntity);
    }

    public void deleteAddress(Long id) {
        addressRepository.deleteById(id);
    }
}
