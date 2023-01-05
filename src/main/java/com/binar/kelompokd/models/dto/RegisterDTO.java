package com.binar.kelompokd.models.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class RegisterDTO {
    @NotEmpty(message = "email is required.")
    private String email;
    @NotEmpty(message = "password is required.")
    private String password;
    @NotEmpty(message = "fullname is required.")
    private String fullname;
}
