package com.binar.kelompokd.models.entity;

import com.binar.kelompokd.enums.RoomType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "t_kost_rooms")
public class KostRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private RoomType roomType;

    // rules text

    private Double pricePerDaily;
    private Double pricePerWeekly;

    @Column(nullable = false)
    private Double pricePerMonthly;

    @Column(nullable = false)
    private Boolean isAvailable;

    // facility_id array[]

    // image_id array[]
}
