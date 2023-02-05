package com.binar.kelompokd.interfaces;

import com.binar.kelompokd.models.entity.transaction.Booking;
import com.binar.kelompokd.models.entity.transaction.Invoice;
import com.binar.kelompokd.models.entity.transaction.Payment;
import com.binar.kelompokd.models.response.transaction.TransactionOwnerBookingList;
import com.binar.kelompokd.models.response.transaction.TransactionOwnerDetail;
import com.cloudinary.api.exceptions.NotFound;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

public interface InvoiceService {
    List<Invoice> getAllInvoice();

    List<TransactionOwnerBookingList> getTransactionOwnerById(Long ownerID) throws NotFound;

    TransactionOwnerDetail getOwnerKosById(UUID transactionId) throws NoSuchElementException;
}
