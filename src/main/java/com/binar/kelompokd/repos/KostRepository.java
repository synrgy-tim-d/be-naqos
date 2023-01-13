package com.binar.kelompokd.repos;

import com.binar.kelompokd.models.entity.Kost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface KostRepository extends JpaRepository<Kost, UUID> {
}
