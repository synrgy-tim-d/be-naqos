package com.binar.kelompokd.repos.location;

import com.binar.kelompokd.models.entity.location.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {
}
