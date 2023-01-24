package com.binar.kelompokd.models.response;

import lombok.Data;

import java.util.List;

@Data
public class WishlistKostPageResponse {
  private int totalPage;
  private long totalElement;
  private int currentPage;
  private boolean isFirst;
  private boolean isLast;
  private int size;
  private List<NewKostResponse> kostResponses;

  public WishlistKostPageResponse(int totalPage, long totalElement, int currentPage, boolean isFirst,
                             boolean isLast, int size, List<NewKostResponse> kostResponses) {
    this.totalPage = totalPage;
    this.totalElement = totalElement;
    this.currentPage = currentPage;
    this.isFirst = isFirst;
    this.isLast = isLast;
    this.size = size;
    this.kostResponses = kostResponses;
  }

}
