package com.binar.kelompokd.models.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor

public class WishlistStatusResponse {
  private String kostId;
  private Long userId;
  private Boolean wishlistStatus;

}
