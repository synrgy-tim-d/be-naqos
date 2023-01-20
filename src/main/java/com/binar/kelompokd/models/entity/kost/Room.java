package com.binar.kelompokd.models.entity.kost;

import com.binar.kelompokd.enums.RoomType;
import com.binar.kelompokd.models.DateModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
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
    @Schema(example = "Jangan berisik di atas jam 10 malam")
    @Column(columnDefinition="TEXT", nullable = false)
    private String rules;

    @Schema(example = "150000")
    private BigDecimal pricePerDaily;   // saya tidak tau gmn caranya buat tipe data decimal di postgres. tapi berdasarkan riset kita bisa menggunakan bigdecimal untuk tipe data decimal

    @Schema(example = "650000")
    private BigDecimal pricePerWeekly;

    @Schema(example = "2000000")
    @Column(nullable = false)
    private BigDecimal pricePerMonthly;

    @Schema(example = "true")
    @Column(nullable = false)
    private Boolean isAvailable;

    @ManyToOne
    @JoinColumn(name="kos_id", referencedColumnName = "id")
    private Kost kost;
}
