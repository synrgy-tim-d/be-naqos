package com.binar.kelompokd.models.response.transaction;

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
public class TransactionTenantPayment {

    private UUID kosId;
    private String name;

    private UUID bookingId;
    private Date createdAt;         // booking ent

    private BigDecimal grandTotal;

    private UUID paymentId;
    private String paymentProof;
    private PaymentStatus paymentStatus;
}
