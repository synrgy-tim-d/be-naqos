package com.binar.kelompokd.interfaces;

import com.binar.kelompokd.models.entity.transaction.Invoice;
import com.binar.kelompokd.models.response.transaction.*;
import com.cloudinary.api.exceptions.NotFound;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

public interface InvoiceService {
    List<Invoice> getAllInvoice();

    List<TransactionOwnerBookingList> getTransactionOwnerById(Long ownerID) throws NotFound;

    TransactionOwnerDetail getOwnerKosById(UUID transactionId) throws NoSuchElementException;


    List<TransactionTenantHistory> getAllTransactionTenantById(Long tenantId) throws NotFound;

    List<TransactionTenantPayment> getTransactionTenantPaymentById(Long tenantId) throws NotFound;

    List<TransactionTenantCancelled> getTransactionTenantCancelById(Long tenantId) throws NotFound;

    List<TransactionTenantHistory> getTransactionTenantPendingById(Long tenantId) throws NotFound;

}
