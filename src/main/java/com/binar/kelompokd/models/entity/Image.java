package com.binar.kelompokd.models.entity;

import com.binar.kelompokd.models.DateModel;
import com.binar.kelompokd.models.entity.kost.Kost;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "t_setup_images")
public class Image extends DateModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // file_location text
    @Schema(example = "www.foto-kamar-1-kos-alamanda.com")
    @Column(name = "url", columnDefinition="TEXT", nullable = false)
    private String url;

    @ManyToOne
    @JoinColumn(name="kost_id")
    private Kost kost;
}
