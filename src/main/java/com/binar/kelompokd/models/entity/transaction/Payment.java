package com.binar.kelompokd.models.entity.transaction;

import com.binar.kelompokd.enums.PaymentStatus;
import com.binar.kelompokd.enums.PaymentType;
import com.binar.kelompokd.models.entity.oauth.Users;
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
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="t_payments")
public class Payment implements Serializable {
    @Id
    @Cascade(CascadeType.ALL)
    private UUID id;

    @Schema(example = "TRANSFER")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentType paymentType;

    @Schema(example = "PENDING")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus paymentStatus;

    @Schema(example = "1")
    private BigDecimal amount;

    @Schema(example = "yyyy-MM-dd HH-mm-ss")
    private LocalDateTime paidAt;

    @Schema(example = "yyyy-MM-dd HH-mm-ss")
    private LocalDateTime cancelAt;

    @Schema(example = "images_transfer.com")
    @Column(name = "payment_proof", columnDefinition="TEXT", nullable = false)
    private String paymentProof;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "paid_by", referencedColumnName = "id")
    private Users paidBy;

}
