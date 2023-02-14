package com.binar.kelompokd.models.entity.oauth;

import com.binar.kelompokd.models.entity.location.Province;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "t_setup_bank")
public class Bank {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Schema(example = "Bank BRI")
    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @Schema(example = "http:")
    @Column(name = "logo_url", nullable = false)
    private String logoUrl;
}
