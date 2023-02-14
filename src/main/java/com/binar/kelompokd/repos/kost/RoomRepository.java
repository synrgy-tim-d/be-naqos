package com.binar.kelompokd.repos.kost;

import com.binar.kelompokd.models.entity.kost.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RoomRepository extends JpaRepository<Room, UUID> {

    @Query(
            nativeQuery = true,
            value = "select * from t_kost_rooms where id = :id and is_available = true"
    )
    Optional<Room> getRoomByIdWhereIsAvailableTrue(@Param("id") UUID id);

    @Query(
            nativeQuery = true,
            value = "select * from t_kost_rooms where kost_id = :kostId and is_available = true"
    )
    List<Room> getAllAvailableRoomsByKostId(@Param("kostId") UUID kostId);

    @Query(
            nativeQuery = true,
            value = "select * from t_kost_rooms where is_available = true"
    )
    List<Room> getAllRoomsWhereIsAvailableTrue();

    @Query(
            nativeQuery = true,
            value = "update t_kost_rooms set is_available = false where id = :id"
    )
    @Transactional
    @Modifying
    void softDeleteRoom(@Param("id") UUID id);

}
