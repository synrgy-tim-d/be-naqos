package com.binar.kelompokd.models.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UpdateUserRequest {
  private String fullname;
  private String phoneNumber;
}
