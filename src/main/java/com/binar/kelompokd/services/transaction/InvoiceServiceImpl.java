package com.binar.kelompokd.services.transaction;

import com.binar.kelompokd.interfaces.InvoiceService;
import com.binar.kelompokd.models.entity.transaction.Invoice;
import com.binar.kelompokd.models.response.transaction.TransactionOwnerBookingList;
import com.binar.kelompokd.models.response.transaction.TransactionOwnerDetail;
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
        List<Invoice> invoices = invoiceRepository.getAllInvoice();

        List<TransactionOwnerBookingList> response = new ArrayList<>();
        for (Invoice invoice : invoices) {
            if (Objects.equals(invoice.getBookingId().getOwnerId().getId(), ownerId)) {

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
        }
        if(response.isEmpty()){
            throw new NotFound("transaction is not found");
        }
        return response;
    }

    @Override
    public TransactionOwnerDetail getOwnerKosById(UUID transactionId) throws NoSuchElementException {

        Invoice invoice = invoiceRepository.getInvoiceById(transactionId);
        if(invoice == null){
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
}
