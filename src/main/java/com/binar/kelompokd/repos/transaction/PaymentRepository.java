package com.binar.kelompokd.repos.transaction;

import com.binar.kelompokd.models.entity.transaction.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, UUID> {
}
