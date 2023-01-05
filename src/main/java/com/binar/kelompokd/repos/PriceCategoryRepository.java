package com.binar.kelompokd.repos;

import com.binar.kelompokd.models.entity.PricesCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PriceCategoryRepository extends JpaRepository<PricesCategory, Integer> {
}
