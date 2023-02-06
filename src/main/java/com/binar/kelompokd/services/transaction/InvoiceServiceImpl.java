package com.binar.kelompokd.services.transaction;

import com.binar.kelompokd.interfaces.InvoiceService;
import com.binar.kelompokd.models.entity.transaction.Invoice;
import com.binar.kelompokd.models.response.transaction.*;
import com.binar.kelompokd.repos.transaction.BookingRepository;
import com.binar.kelompokd.repos.transaction.InvoiceRepository;
import com.binar.kelompokd.repos.transaction.PaymentRepository;
import com.cloudinary.api.exceptions.NotFound;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
@AllArgsConstructor
public class InvoiceServiceImpl implements InvoiceService {

    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    InvoiceRepository invoiceRepository;

    @Autowired
    PaymentRepository paymentRepository;


    @Override
    public List<Invoice> getAllInvoice() {
        return invoiceRepository.getAllInvoice();
    }


    @Override
    public List<TransactionOwnerBookingList> getTransactionOwnerById(Long ownerId) throws NotFound {
        List<Invoice> invoices = invoiceRepository.getAllTransactionByOwnerId(ownerId);

        List<TransactionOwnerBookingList> response = new ArrayList<>();
        for (Invoice invoice : invoices) {
            TransactionOwnerBookingList invoiceItem = new TransactionOwnerBookingList(
                    invoice.getId(),
                    invoice.getBookingId().getOccupantId().getId(),
                    invoice.getBookingId().getOccupantId().getImgUrl(),
                    invoice.getBookingId().getOccupantId().getFullname(),
                    invoice.getBookingId().getCreatedAt(),
                    invoice.getBookingId().getKostId().getId(),
                    invoice.getBookingId().getKostId().getName(),
                    invoice.getBookingId().getId(),
                    invoice.getBookingId().getBookingOption(),
                    invoice.getGrandTotal(),
                    invoice.getPaymentId().getId(),
                    invoice.getPaymentId().getPaymentProof(),
                    invoice.getPaymentId().getPaymentStatus()
            );
            response.add(invoiceItem);
        }
        if (response.isEmpty()) {
            throw new NotFound("transaction is not found");
        }
        return response;
    }

    @Override
    public TransactionOwnerDetail getOwnerKosById(UUID transactionId) throws NoSuchElementException {

        Invoice invoice = invoiceRepository.getInvoiceById(transactionId);
        if (invoice == null) {
            throw new NoSuchElementException("id transaction doesn't exist");
        }

        return new TransactionOwnerDetail(
                invoice.getBookingId().getOccupantId().getId(),
                invoice.getBookingId().getOccupantId().getImgUrl(),
                invoice.getBookingId().getOccupantId().getFullname(),
                invoice.getBookingId().getCreatedAt(),
                invoice.getBookingId().getOccupantId().getPhoneNumber(),
                invoice.getBookingId().getKostId().getImageKosts().get(1),
                invoice.getBookingId().getKostId().getId(),
                invoice.getBookingId().getKostId().getName(),
                invoice.getBookingId().getKostId().getCity(),
                invoice.getBookingId().getId(),
                invoice.getBookingId().getStartDate(),
                invoice.getBookingId().getEndDate(),
                invoice.getBookingId().getRoomId().getPricePerDaily(),
                invoice.getBookingId().getRoomId().getPricePerWeekly(),
                invoice.getBookingId().getRoomId().getPricePerMonthly(),
                invoice.getBookingId().getPrice(),
                invoice.getBookingId().getAmount(),
                invoice.getBookingId().getBookingOption(),
                invoice.getGrandTotal(),
                invoice.getPaymentId().getId(),
                invoice.getPaymentId().getPaymentProof(),
                invoice.getPaymentId().getPaymentStatus()
        );
    }

    @Override
    public List<TransactionTenantPayment> getTransactionTenantPaymentById(Long tenantId) throws NotFound {
        List<Invoice> invoices = invoiceRepository.getAllInvoice();

        List<TransactionTenantPayment> response = new ArrayList<>();
        for (Invoice invoice : invoices) {
            if (Objects.equals(invoice.getBookingId().getOccupantId().getId(), tenantId)) {

                TransactionTenantPayment invoiceItem = new TransactionTenantPayment(
                        invoice.getBookingId().getKostId().getId(),
                        invoice.getBookingId().getKostId().getName(),
                        invoice.getBookingId().getId(),
                        invoice.getBookingId().getCreatedAt(),
                        invoice.getGrandTotal(),
                        invoice.getPaymentId().getId(),
                        invoice.getPaymentId().getPaymentProof(),
                        invoice.getPaymentId().getPaymentStatus()
                );
                response.add(invoiceItem);
            }
        }
        if (response.isEmpty()) {
            throw new NotFound("payment is not found");
        }
        return response;
    }

    @Override
    public List<TransactionTenantCancelled> getTransactionTenantCancelById(Long tenantId) throws NotFound {
        List<Invoice> invoices = invoiceRepository.getBookingCancelledByTenantId(tenantId);

        List<TransactionTenantCancelled> response = new ArrayList<>();
        for (Invoice invoice : invoices) {
            TransactionTenantCancelled invoiceItem = new TransactionTenantCancelled(
                    invoice.getBookingId().getKostId().getId(),
                    invoice.getBookingId().getKostId().getName(),
                    invoice.getBookingId().getKostId().getImageKosts().get(0),
                    invoice.getBookingId().getKostId().getCity(),
                    invoice.getBookingId().getId(),
                    invoice.getBookingId().getStartDate(),
                    invoice.getBookingId().getEndDate(),
                    invoice.getBookingId().getBookingOption(),
                    invoice.getPaymentId().getId(),
                    invoice.getPaymentId().getCancelAt(),
                    invoice.getPaymentId().getPaymentStatus());
            response.add(invoiceItem);
        }
        if (response.isEmpty()) {
            throw new NotFound("cancelled Transaction is not found");
        }
        return response;
    }

    @Override
    public List<TransactionTenantHistory> getTransactionTenantPendingById(Long tenantId) throws NotFound {
        List<Invoice> invoices = invoiceRepository.getBookingPendingByTenantId(tenantId);

        List<TransactionTenantHistory> response = new ArrayList<>();
        for (Invoice invoice : invoices) {
            TransactionTenantHistory invoiceItem = new TransactionTenantHistory(
                    invoice.getId(),
                    invoice.getBookingId().getKostId().getId(),
                    invoice.getBookingId().getKostId().getName(),
                    invoice.getBookingId().getKostId().getImageKosts().get(0),
                    invoice.getBookingId().getKostId().getCity(),
                    invoice.getBookingId().getId(),
                    invoice.getBookingId().getStartDate(),
                    invoice.getBookingId().getEndDate(),
                    invoice.getBookingId().getBookingOption(),
                    invoice.getPaymentId().getId(),
                    invoice.getPaymentId().getPaymentStatus());
            response.add(invoiceItem);
        }
        if (response.isEmpty()) {
            throw new NotFound("Pending Transaction is not found");
        }
        return response;
    }

    @Override
    public List<TransactionTenantHistory> getAllTransactionTenantById(Long tenantId) throws NotFound {
        List<Invoice> invoices = invoiceRepository.getAllTransactionByTenantId(tenantId);

        List<TransactionTenantHistory> response = new ArrayList<>();
        for (Invoice invoice : invoices) {
            TransactionTenantHistory invoiceItem = new TransactionTenantHistory(
                    invoice.getId(),
                    invoice.getBookingId().getKostId().getId(),
                    invoice.getBookingId().getKostId().getName(),
                    invoice.getBookingId().getKostId().getImageKosts().get(0),
                    invoice.getBookingId().getKostId().getCity(),
                    invoice.getBookingId().getId(),
                    invoice.getBookingId().getStartDate(),
                    invoice.getBookingId().getEndDate(),
                    invoice.getBookingId().getBookingOption(),
                    invoice.getPaymentId().getId(),
                    invoice.getPaymentId().getPaymentStatus());
            response.add(invoiceItem);
        }
        if (response.isEmpty()) {
            throw new NotFound("History Transaction is not found");
        }
        return response;
    }

}
