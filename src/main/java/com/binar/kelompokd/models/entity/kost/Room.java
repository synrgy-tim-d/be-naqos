package com.binar.kelompokd.models.entity.kost;

import com.binar.kelompokd.enums.RoomType;
import com.binar.kelompokd.models.DateModel;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "t_kost_rooms")
public class Room extends DateModel implements Serializable {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(
                            name = "uuid_gen_strategy_class",
                            value = "org.hibernate.id.uuid.CustomVersionOneStrategy"
                    )
            }
    )
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private UUID id;

    @Schema(example = "FULL_FURNISHED")
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private RoomType roomType;

    @Schema(example = "true")
    @Column(nullable = false, columnDefinition = "boolean default true")
    private Boolean isAvailable;

    @ManyToOne
    @JoinColumn(name="kost_id", referencedColumnName = "id")
    @Cascade(org.hibernate.annotations.CascadeType.MERGE)
    @JsonBackReference
    private Kost kost;

    public Room(RoomType roomType,
                Boolean isAvailable, Kost kost) {
        this.roomType = roomType;
        this.isAvailable = isAvailable;
        this.kost = kost;
    }

    @Override
    public String toString() {
        return "Room{" +
                "id=" + id +
                ", roomType=" + roomType +
                ", isAvailable=" + isAvailable +
                ", kost=" + kost +
                '}';
    }
}
