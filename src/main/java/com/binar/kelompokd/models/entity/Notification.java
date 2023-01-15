//package com.binar.kelompokd.models.entity;
//
//import com.binar.kelompokd.models.DateModel;
//import lombok.Data;
//import lombok.EqualsAndHashCode;
//import lombok.NoArgsConstructor;
//
//import javax.persistence.*;
//import java.io.Serializable;
//
//@Data
//@EqualsAndHashCode(callSuper = false)
//@Entity(name = "t_user_notification")
//@NoArgsConstructor
//public class Notification extends DateModel implements Serializable {
//  @Id
//  @GeneratedValue(strategy = GenerationType.IDENTITY)
//  private Integer id;
//
//  @Column(name = "title")
//  private String title;
//
//  @Column(name = "content")
//  private String content;
//
//  @Column(name = "is_read")
//  private Boolean isRead = false;
//
//  @ManyToOne(fetch = FetchType.LAZY)
//  @JoinColumn(name = "sent_to", nullable = true)
//  private User sentTo;
//
//}
