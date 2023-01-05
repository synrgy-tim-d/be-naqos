package com.binar.kelompokd.repos;

import com.binar.kelompokd.models.entity.KostSpecification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KostSpecificationRepository extends JpaRepository<KostSpecification, Integer> {
}
