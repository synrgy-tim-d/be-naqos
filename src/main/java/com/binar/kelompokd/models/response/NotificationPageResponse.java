//package com.binar.kelompokd.models.response;
//
//import lombok.Data;
//import org.springframework.data.domain.Page;
//
//import java.util.List;
//
//@Data
//public class NotificationPageResponse {
//  private int totalPage;
//  private long totalElement;
//  private int currentPage;
//  private boolean isFirst;
//  private boolean isLast;
//  private int size;
//  private List<NotificationResponse> notificationResponses;
//
//  public NotificationPageResponse(Page<NotificationResponse> responses, int currentPage) {
//    this.totalPage = responses.getTotalPages();
//    this.totalElement = responses.getTotalElements();
//    this.currentPage = currentPage;
//    this.isFirst = responses.isFirst();
//    this.isLast = responses.isLast();
//    this.size = responses.getSize();
//    this.notificationResponses = responses.getContent();
//  }
//}
