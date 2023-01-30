package com.binar.kelompokd.repos.kost;

import com.binar.kelompokd.models.entity.kost.RoomAndFacility;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.UUID;

@Repository
public interface RoomAndFacilityRepository extends JpaRepository<RoomAndFacility, UUID> {
    @Query(
            nativeQuery = true,
            value = "delete from t_kost_rooms_facility where room_id=:roomId"
    )
    @Transactional
    @Modifying
    void deleteByRoomId(@Param("roomId") UUID roomId);
}
