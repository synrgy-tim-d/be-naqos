package com.binar.kelompokd.models.entity.transaction;

import com.binar.kelompokd.enums.BookingOption;
import com.binar.kelompokd.models.DateModel;
import com.binar.kelompokd.models.entity.kost.Kost;
import com.binar.kelompokd.models.entity.kost.Room;
import com.binar.kelompokd.models.entity.oauth.Users;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="t_booking_detail")
public class BookingDetail extends DateModel implements Serializable {
    @Id
    @Cascade(CascadeType.ALL)
    private UUID id;

    @OneToOne
    @JoinColumn(name = "booking_id", referencedColumnName = "id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Booking bookingId;

    @Schema(example = "false")
    @Column(nullable = false)
    private Boolean isAvailable;

    @Schema(example = "false")
    @Column(nullable = false)
    private Boolean isPaid;

    @Schema(example = "false")
    @Column(nullable = false)
    private Boolean willPay;

    @Schema(example = "false")
    @Column(nullable = false)
    private Boolean isConfirm;

    @Schema(example = "false")
    @Column(nullable = false)
    private Boolean isCancelled;

    @Schema(example = "bayar ntar nunggu gajian")
    @Column(nullable = false)
    private String paymentMethod;

    @Schema(example = "PENDING")
    private String rentTime;

    @Schema(example = "")
    private BigInteger rentPrice;
}
