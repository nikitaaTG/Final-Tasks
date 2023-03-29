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

    /**
     * Method for save users address in DB. Here we set userId and UserEntity to AddressEntity and save it.
     *
     * @param addressDTO
     * @param userId
     * @return
     */
    public AddressEntity saveAddress(AddressDTO addressDTO, long userId) {
        AddressEntity addressEntity = AddressMapper.INSTANCE.addressDTOToEntity(addressDTO);
        addressEntity.setUserId(userId);
        addressEntity.setUser(userService.getUserById(userId));
        return addressRepository.save(addressEntity);
    }

    public AddressEntity getAddressById(long id) {
        return addressRepository.findById(id).orElse(null);
    }

    /**
     * Method for updating address in DB. Here we get parameters of sql-request and place them in right order.
     *
     * @param id
     * @param updatedAddress
     */
    public void updateAddress(long id, AddressDTO updatedAddress) {
        String country = updatedAddress.getCountry();
        String city = updatedAddress.getCity();
        int postIndex = updatedAddress.getPostIndex();
        String street = updatedAddress.getStreet();
        int home = updatedAddress.getHome();
        int apartment = updatedAddress.getApartment();
        addressRepository.updateAddress(country, city, postIndex, street, home, apartment, id);
    }
}
