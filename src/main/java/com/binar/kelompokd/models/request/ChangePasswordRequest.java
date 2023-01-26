package com.binar.kelompokd.models.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ChangePasswordRequest {
  @NotBlank
  String oldPassword;
  @NotBlank
  String newPassword;
  @NotBlank
  String confirmPassword;
}
