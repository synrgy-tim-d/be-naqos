package com.binar.kelompokd.repos.transaction;

import com.binar.kelompokd.models.entity.transaction.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BookingRepository extends JpaRepository<Booking, UUID> {
}
