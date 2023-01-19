package com.binar.kelompokd.repos.kost;

import com.binar.kelompokd.models.entity.kost.RoomFacility;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RoomFacilityRepository extends JpaRepository<RoomFacility, UUID> {
}
