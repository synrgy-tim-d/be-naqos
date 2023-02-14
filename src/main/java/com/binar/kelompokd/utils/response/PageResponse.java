package com.binar.kelompokd.utils.response;

import lombok.Data;

import java.util.List;

@Data
public class PageResponse {
  private int totalPage;
  private long totalElement;
  private int currentPage;
  private boolean isFirst;
  private boolean isLast;
  private int size;
  private List<?> data;

  public PageResponse(int totalPage, long totalElement, int currentPage, boolean isFirst,
                      boolean isLast, int size, List<?> kostResponses) {
    this.totalPage = totalPage;
    this.totalElement = totalElement;
    this.currentPage = currentPage;
    this.isFirst = isFirst;
    this.isLast = isLast;
    this.size = size;
    this.data = kostResponses;
  }

}
