package com.binar.kelompokd.models.dto.user;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class RegisterGoogleDTO {
    @NotEmpty(message = "username is required.")
    private String username;
    @NotEmpty(message = "password is required.")
    private String password;
    @NotEmpty(message = "fullname is required.")
    private String fullname;
    @NotEmpty(message = "phone is required.")
    private String phoneNumber;
    @NotEmpty(message = "role is required.")
    private String role;
    @NotEmpty(message = "image url is required.")
    private String imageUrl;
}
