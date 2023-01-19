package com.binar.kelompokd.repos.kost;

import com.binar.kelompokd.models.entity.kost.KostRoom;
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
public interface KostRoomRepository extends JpaRepository<KostRoom, UUID> {

    @Query(
            nativeQuery = true,
            value = "select * from t_kost_rooms where id = :id and is_available = true"
    )
    Optional<KostRoom> getRoomByIdWhereIsAvailableTrue(@Param("id") UUID id);

    @Query(
            nativeQuery = true,
            value = "select * from t_kost_rooms where is_available = true"
    )
    List<KostRoom> getAllRoomsWhereIsAvailableTrue();

    @Query(
            nativeQuery = true,
            value = "update t_kost_rooms set is_available = false where id = :id"
    )
    @Transactional
    @Modifying
    void softDeleteRoom(@Param("id") UUID id);

}