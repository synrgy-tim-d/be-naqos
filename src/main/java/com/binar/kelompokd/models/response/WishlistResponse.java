package com.binar.kelompokd.models.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WishlistResponse {
  private String kostId;
  private Long userId;
  private String message;

}
