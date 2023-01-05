package com.binar.kelompokd.repos;

import com.binar.kelompokd.models.entity.KostProperty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KostPropertyRepository extends JpaRepository<KostProperty, Integer> {
}
