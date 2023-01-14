package com.binar.kelompokd.models.entity;

import com.binar.kelompokd.enums.RoomType;
import com.binar.kelompokd.models.DateModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "t_kost_rooms")
public class KostRoom extends DateModel implements Serializable {

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

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private RoomType roomType;

    // rules text
    @Column(columnDefinition="TEXT", nullable = false)
    private String rules;

    private BigDecimal pricePerDaily;
    private BigDecimal pricePerWeekly;

    @Column(nullable = false)
    private BigDecimal pricePerMonthly;

    @Column(nullable = false)
    private Boolean isAvailable;

    // facility_id array[]
    private UUID[] facilityId;


    // image_id array[]
    private UUID[] imageId;
}
