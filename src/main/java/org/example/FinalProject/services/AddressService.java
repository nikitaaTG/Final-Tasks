package org.example.FinalProject.services;

import org.example.FinalProject.dto.AddressDTO;
import org.example.FinalProject.mappers.AddressMapper;
import org.example.FinalProject.models.AddressEntity;
import org.example.FinalProject.repositories.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressService {
    @Autowired
    private UserService userService;

    private final AddressRepository addressRepository;

    @Autowired
    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    public List<AddressEntity> findByUserID(Long id) {
        return addressRepository.findAllByUserId(id);
    }

    public AddressEntity addAddress(AddressEntity addressEntity) {
        return addressRepository.save(addressEntity);
    }

    public void deleteAddress(Long id) {
        addressRepository.deleteById(id);
    }

    public AddressEntity saveAddress(AddressDTO addressDTO, long userId) {
        AddressEntity addressEntity = AddressMapper.INSTANCE.addressDTOToEntity(addressDTO);
        addressEntity.setUserId(userId);
        addressEntity.setUser(userService.getUserById(userId));
        return addressRepository.save(addressEntity);
    }

    public AddressEntity getAddressById(long id) {
        return addressRepository.findById(id).orElse(null);
    }

    public void updateAddress(long id, AddressDTO updatedAddress) {
        String country = updatedAddress.getCountry();
        String city = updatedAddress.getCity();
        int postIndex = updatedAddress.getPostIndex();
        String street = updatedAddress.getStreet();
        int home = updatedAddress.getHome();
        int apartment = updatedAddress.getApartment();
//        long userId = updatedAddress.getUserId();
        addressRepository.updateAddress(country, city, postIndex, street, home, apartment, id);
    }
}
