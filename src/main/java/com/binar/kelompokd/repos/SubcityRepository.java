package com.binar.kelompokd.repos;

import com.binar.kelompokd.models.entity.Subcity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubcityRepository extends JpaRepository<Subcity, Integer> {
}
