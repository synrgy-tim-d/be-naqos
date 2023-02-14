package com.binar.kelompokd.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Getter
@Setter
@Embeddable
public class Sort implements Serializable {

    private String field;

    private String dir;

}
