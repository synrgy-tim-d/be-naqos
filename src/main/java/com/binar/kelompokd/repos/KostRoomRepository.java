package com.binar.kelompokd.repos;

import com.binar.kelompokd.models.entity.KostRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface KostRoomRepository extends JpaRepository<KostRoom, UUID> {
}
