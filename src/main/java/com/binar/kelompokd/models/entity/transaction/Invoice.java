package com.binar.kelompokd.models.entity.transaction;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="t_invoice")
public class Invoice implements Serializable {

    @Id
    @Cascade(CascadeType.ALL)
    private UUID id;

    @Schema(example = "150000")
    private BigDecimal grandTotal;

    @OneToOne(targetEntity = Booking.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "booking_id", nullable = false)
    private Booking bookingId;

    @OneToOne(targetEntity = Payment.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "payment_id", nullable = false)
    private Payment paymentId;
}
