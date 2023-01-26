package com.binar.kelompokd.models.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateUserRequest {
  private String fullname;
  private String phoneNumber;
}
