package com.binar.kelompokd.models.response.transaction;

import com.binar.kelompokd.enums.BookingOption;
import com.binar.kelompokd.enums.PaymentStatus;
import com.binar.kelompokd.models.entity.Image;
import com.binar.kelompokd.models.entity.location.City;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionTenantHistory {

    private UUID invoiceID;

    private UUID kosId;
    private String name;
    private Image imageKos;
    private City city;

    private UUID bookingId;
    private LocalDate startDate;
    private LocalDate endDate;
    private BookingOption bookingOption;

    private UUID paymentId;
    private PaymentStatus paymentStatus;
}
