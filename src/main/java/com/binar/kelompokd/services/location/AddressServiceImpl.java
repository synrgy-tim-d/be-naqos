package com.binar.kelompokd.services.location;

import com.binar.kelompokd.interfaces.AddressService;
import com.binar.kelompokd.models.entity.location.Address;
import com.binar.kelompokd.repos.location.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    AddressRepository addressRepository;

    @Override
    @Transactional
    public Address save(Address address) {
        return addressRepository.save(address);
    }

    @Override
    public Address getAddressById(Integer id) {
        return addressRepository.findById(id).get();
    }

    @Override
    public List<Address> getAllAddresses() {
        return addressRepository.findAll();
    }

    @Override
    @Transactional
    public Address update(Integer id, Address address) {
        Address address1 = addressRepository.findById(id).get();

        address1.setAddress(address.getAddress());
        address1.setProvinceId(address.getProvinceId());
        address1.setCityId(address.getCityId());
        address1.setLatitude(address.getLatitude());
        address1.setLongitude(address.getLongitude());
        address1.setDistrict(address.getDistrict());
        address1.setSubdistrict(address.getSubdistrict());
        address1.setPostalCode(address.getPostalCode());

        return addressRepository.save(address1);
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        addressRepository.deleteById(id);
    }
}
