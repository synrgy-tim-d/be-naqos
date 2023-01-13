package com.binar.kelompokd.repos;

import com.binar.kelompokd.models.entity.RoomFacility;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomFacilityRepository extends JpaRepository<RoomFacility, Integer> {
}
