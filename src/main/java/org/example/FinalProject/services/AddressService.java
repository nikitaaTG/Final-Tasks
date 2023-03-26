package org.example.FinalProject.services;

import java.util.List;

import org.example.FinalProject.dto.AddressDTO;
import org.example.FinalProject.mappers.AddressMapper;
import org.example.FinalProject.models.AddressEntity;
import org.example.FinalProject.repositories.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressService {
    @Autowired
    private UserService userService; // FIXME: выбери одну стратегию для вайринга и спользуй ее везде, а то у тебя репозиторий через конструктор вайрится, а сервис через аннотацию. Если через конструктор, то можно все сделать файнл и повести @RequiredArgsConstructor

    private final AddressRepository addressRepository;

    @Autowired
    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    public List<AddressEntity> findByUserID(Long id) {
        return addressRepository.findAllByUserId(id);
    }

    // FIXME: неиспользованные методы лучше удалять
    public AddressEntity addAddress(AddressEntity addressEntity) {
        return addressRepository.save(addressEntity);
    }

    public void deleteAddress(Long id) {
        addressRepository.deleteById(id);
    }

    // FIXME: а еще если не используется возвращаемое значение, то лучше использовать воид
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
        addressRepository.updateAddress(country, city, postIndex, street, home, apartment,
                id); // FIXME: вот тут ты мог бы просто вызвать туЕнтити и вызвать сейв = две строчки кода вместо 10)
    }
}
