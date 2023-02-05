package com.binar.kelompokd.repos.transaction;

import com.binar.kelompokd.models.entity.transaction.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, UUID> {
    @Query(
            nativeQuery = true,
            value = "select * from t_invoice"
    )
    List<Invoice> getAllInvoice();

    Invoice getInvoiceById(UUID transactionId);
}
