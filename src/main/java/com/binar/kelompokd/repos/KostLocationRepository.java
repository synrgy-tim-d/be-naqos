package com.binar.kelompokd.repos;

import com.binar.kelompokd.models.entity.KostLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KostLocationRepository extends JpaRepository<KostLocation, Integer> {
}
