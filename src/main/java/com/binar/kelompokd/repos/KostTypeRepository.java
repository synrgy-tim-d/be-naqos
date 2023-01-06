package com.binar.kelompokd.repos;

import com.binar.kelompokd.models.entity.City;
import com.binar.kelompokd.models.entity.KostType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface KostTypeRepository extends JpaRepository<KostType, Integer> {

}
