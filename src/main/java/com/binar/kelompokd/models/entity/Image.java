package com.binar.kelompokd.models.entity;

import com.binar.kelompokd.models.DateModel;
import com.binar.kelompokd.models.entity.kost.Kost;
import com.fasterxml.jackson.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@Table(name = "t_setup_images")
public class Image extends DateModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Schema(example = "www.foto-kamar-1-kos-alamanda.com")
    @Column(name = "url", columnDefinition="TEXT", nullable = false)
    private String url;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "kost_id", referencedColumnName = "id" ,nullable = false)
    @JsonBackReference
    private Kost kosts;

}
