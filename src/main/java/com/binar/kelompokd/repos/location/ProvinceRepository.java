package com.binar.kelompokd.repos.location;

import com.binar.kelompokd.models.entity.location.Province;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProvinceRepository extends JpaRepository<Province, Integer> {
  Province findByProvince(String province);
}
