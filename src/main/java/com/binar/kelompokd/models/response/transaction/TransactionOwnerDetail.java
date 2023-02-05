package com.binar.kelompokd.models.response.transaction;

import com.binar.kelompokd.enums.BookingOption;
import com.binar.kelompokd.enums.PaymentStatus;
import com.binar.kelompokd.models.entity.Image;
import com.binar.kelompokd.models.entity.location.City;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionOwnerDetail {

    private Long userId;
    private String imgUrl;
    private String fullName;
    private Date createdAt;         // booking ent
    private String phoneNumber;


    private Image imageKos;
    private UUID kosId;
    private String name;
    private City city;

    private UUID bookingId;
    private LocalDate startDate;
    private LocalDate endDate;

    private BigDecimal pricePerDaily;
    private BigDecimal pricePerWeekly;
    private BigDecimal pricePerMonthly;
    private BigDecimal price;
    private BigDecimal amount;
    private BookingOption bookingOption;
    private BigDecimal grandTotal;

    private UUID paymentId;
    private String paymentProof;
    private PaymentStatus paymentStatus;
}
