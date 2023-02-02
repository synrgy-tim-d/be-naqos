package com.binar.kelompokd.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Embeddable
public class Filter implements Serializable {

    private String field;

    private String value;

    private List<String> valueIn;

    private String op;

    private String join;

}
