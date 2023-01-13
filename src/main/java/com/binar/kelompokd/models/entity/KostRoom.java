package com.binar.kelompokd.models.entity;

import com.binar.kelompokd.enums.RoomType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;

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
    @Column(columnDefinition="TEXT", nullable = false)
    private String rules;

    private Double pricePerDaily;
    private Double pricePerWeekly;

    @Column(nullable = false)
    private Double pricePerMonthly;

    @Column(nullable = false)
    private Boolean isAvailable;

    // facility_id array[]
    private Integer[] facilityId;


    // image_id array[]
    private Integer[] imageId;
}
