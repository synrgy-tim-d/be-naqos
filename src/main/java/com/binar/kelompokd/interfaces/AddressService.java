package com.binar.kelompokd.interfaces;

import com.binar.kelompokd.models.entity.location.Address;

import java.util.List;

public interface AddressService {
    Address save(Address address);
    Address getAddressById(Integer id);
    List<Address> getAllAddresses();
    Address update(Integer id, Address address);
    void delete(Integer id);
}
