package com.binar.kelompokd.models.entity.kost;


import com.binar.kelompokd.enums.Condition;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "t_room_facility")
public class Facility {

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

    @Schema(example = "AC")
    @Column(nullable = false, length = 50)
    private String name;

    @Schema(example = "BARU")
    @Enumerated(EnumType.STRING)
    private Condition condition;

    @Schema(example = "true")
    @Column(nullable = false)
    private Boolean isActive = false;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            },
            mappedBy = "facilities")
    @JsonIgnore
    private Set<Room> rooms = new HashSet<>();

    @Override
    public String toString() {
        return "Facility{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", condition=" + condition +
                ", isActive=" + isActive +
                '}';
    }
}
