package org.example.FinalProject.services;

import org.example.FinalProject.models.Address;
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

    public Address findByUserID (BigInteger id) {
        return  addressRepository.getReferenceById(id);
    }

    public Address addAddress(Address address) {
        return  addressRepository.save(address);
    }

    public void deleteAddress(BigInteger id) {
        addressRepository.deleteById(id);
    }
}
