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

    // rules text

    @Schema(example = "true")
    @Column(nullable = false, columnDefinition = "boolean default true")
    private Boolean isAvailable;

    @ManyToOne
    @JoinColumn(name="kost_id", referencedColumnName = "id")
    @Cascade(org.hibernate.annotations.CascadeType.MERGE)
    @JsonBackReference
    private Kost kost;

//    @ManyToMany
//    @Cascade({org.hibernate.annotations.CascadeType.ALL, org.hibernate.annotations.CascadeType.MERGE})
//    Set<Facility> facility;

//    @ManyToMany(fetch = FetchType.LAZY,
//            cascade = {
//                    CascadeType.PERSIST,
//                    CascadeType.MERGE
//            })
//    @JoinTable(name = "t_kost_rooms_facility",
//            joinColumns = { @JoinColumn(name = "room_id") },
//            inverseJoinColumns = { @JoinColumn(name = "facility_id") })
//    private Set<Facility> facilities = new HashSet<>();

    public Room(RoomType roomType,
//                String rules, BigDecimal pricePerDaily, BigDecimal pricePerWeekly, BigDecimal pricePerMonthly,
                Boolean isAvailable, Kost kost) {
        this.roomType = roomType;
//        this.rules = rules;
//        this.pricePerDaily = pricePerDaily;
//        this.pricePerWeekly = pricePerWeekly;
//        this.pricePerMonthly = pricePerMonthly;
        this.isAvailable = isAvailable;
        this.kost = kost;
    }

//    public void addFacility(Facility facility) {
//        this.facilities.add(facility);
//        facility.getRooms().add(this);
//    }

//    public void removeFacility(UUID facilityId) {
//        Facility facility = this.facilities.stream().filter(f -> f.getId().toString().equals(facilityId.toString())).findFirst().orElse(null);
//        if (facility != null) {
//            this.facilities.remove(facility);
//            facility.getRooms().remove(this);
//        }
//    }

    @Override
    public String toString() {
        return "Room{" +
                "id=" + id +
                ", roomType=" + roomType +
//                ", rules='" + rules + '\'' +
//                ", pricePerDaily=" + pricePerDaily +
//                ", pricePerWeekly=" + pricePerWeekly +
//                ", pricePerMonthly=" + pricePerMonthly +
                ", isAvailable=" + isAvailable +
                ", kost=" + kost +
                '}';
    }
}
