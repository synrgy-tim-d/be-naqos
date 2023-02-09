package com.binar.kelompokd.models.request.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UpdateUserRequest {
  String fullname;
  String phoneNumber;
}
