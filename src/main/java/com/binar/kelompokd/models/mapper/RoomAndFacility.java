package com.binar.kelompokd.models.mapper;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "t_kost_rooms_facility")
public class RoomAndFacility {
    @Id
    @Column(name = "room_id")
    private UUID roomId;
    @Column(name = "facility_id")
    private UUID facilityId;
}
