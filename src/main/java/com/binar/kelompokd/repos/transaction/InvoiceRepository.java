package com.binar.kelompokd.repos.transaction;

import com.binar.kelompokd.models.entity.transaction.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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

    @Query(
            nativeQuery = true,
            value = "select *" +
                    " from t_bookings" +
                    " join t_invoice on (t_invoice.booking_id=t_bookings.id)" +
                    " join t_payments on (t_invoice.payment_id=t_payments.id)" +
                    " where t_bookings.owner_id=:owner_id order by created_at asc;"
    )
    List<Invoice> getAllTransactionByOwnerId(@Param("owner_id") Long tenantId);

    @Query(
            nativeQuery = true,
            value = "select *" +
                    " from t_bookings" +
                    " join t_invoice on (t_invoice.booking_id=t_bookings.id)" +
                    " join t_payments on (t_invoice.payment_id=t_payments.id)" +
                    " where t_bookings.occupant_id=:occupant_id and booking_option!='CANCELLED' order by created_at asc;"
    )
    List<Invoice> getAllTransactionByTenantId(@Param("occupant_id") Long tenantId);

    @Query(
            nativeQuery = true,
            value = "select *" +
                    " from t_bookings" +
                    " join t_invoice on (t_invoice.booking_id=t_bookings.id)" +
                    " join t_payments on (t_invoice.payment_id=t_payments.id)" +
                    " where t_bookings.occupant_id=:occupant_id and booking_option='CANCELLED' order by created_at asc;"
    )
    List<Invoice> getBookingCancelledByTenantId(@Param("occupant_id") Long tenantId);

    @Query(
            nativeQuery = true,
            value = "select *" +
                    " from t_bookings" +
                    " join t_invoice on (t_invoice.booking_id=t_bookings.id)" +
                    " join t_payments on (t_invoice.payment_id=t_payments.id)" +
                    " where t_bookings.occupant_id=:occupant_id and booking_option='PENDING' order by created_at asc;"
    )
    List<Invoice> getBookingPendingByTenantId(@Param("occupant_id") Long tenantId);
}
