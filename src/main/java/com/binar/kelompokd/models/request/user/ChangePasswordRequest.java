package com.binar.kelompokd.models.request.user;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class ChangePasswordRequest {
  @NotBlank
  String oldPassword;
  @NotBlank
  String newPassword;
  @NotBlank
  String confirmPassword;
}
