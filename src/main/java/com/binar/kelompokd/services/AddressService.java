package com.binar.kelompokd.services;

import com.binar.kelompokd.models.entity.Address;
import com.binar.kelompokd.models.entity.City;

import java.util.List;

public interface AddressService {
    Address save(Address address);
    Address getAddressById(Integer id);
    List<Address> getAllAddresses();
    Address update(Integer id, Address address);
    void delete(Integer id);
}
