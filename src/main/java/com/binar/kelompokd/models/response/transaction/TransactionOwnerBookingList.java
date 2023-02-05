package com.binar.kelompokd.models.response.transaction;

import com.binar.kelompokd.enums.BookingOption;
import com.binar.kelompokd.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionOwnerBookingList {

    private UUID invoiceID;

    private Long userId;
    private String imgUrl;
    private String fullName;
    private Date createdAt;         // booking ent

    private UUID kosId;
    private String name;

    private UUID bookingId;
    private BookingOption bookingOption;
    private BigDecimal grandTotal;

    private UUID paymentId;
    private String paymentProof;
    private PaymentStatus paymentStatus;
}