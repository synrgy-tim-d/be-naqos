package com.binar.kelompokd.models.entity.transaction;

import com.binar.kelompokd.models.DateModel;
import com.binar.kelompokd.models.entity.kost.Kost;
import com.binar.kelompokd.models.entity.kost.Room;
import com.binar.kelompokd.models.entity.oauth.Users;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.*;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="t_booking")
public class Booking extends DateModel implements Serializable {
    @Id
    @Cascade(CascadeType.ALL)
    private UUID id;

    @Schema(example = "yyyy-MM-dd")
    @Column(nullable = false)
    private LocalDate booking_date_start;

    @Schema(example = "yyyy-MM-dd")
    @Column(nullable = false)
    private LocalDate booking_date_end;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Users userId;

    @OneToOne(targetEntity = Room.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "room_id", nullable = false)
    private Room roomId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "kost_id", referencedColumnName = "id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Kost kostId;
}
