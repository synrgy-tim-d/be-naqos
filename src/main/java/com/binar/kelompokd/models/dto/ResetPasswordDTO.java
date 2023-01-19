package com.binar.kelompokd.models.dto;

import lombok.Data;

@Data
public class ResetPasswordDTO {
    public String username;
    public String otp;
    public String newPassword;
}
