//package com.binar.kelompokd.repos;
//
//import com.binar.kelompokd.models.entity.Notification;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Modifying;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.stereotype.Repository;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//import java.util.Optional;
//
//@Repository
//@Transactional
//public interface NotificationRepo extends JpaRepository<Notification, Integer> {
//  Optional<Notification> findById(Integer id);
//
//  @Modifying
//  @Query(value = "select * from notification where sent_to=?1 order by created_on DESC", nativeQuery = true)
//  List<Notification> findNotifications(Integer userId);
//
//  @Modifying
//  @Query(value = "select count(1) from notification where sent_to=?1 and is_read=false", nativeQuery = true)
//  Integer unreadNotifications(Integer userId);
//
//  @Modifying
//  @Query(value = "select * from notification where sent_to=?1 and is_read=false", nativeQuery = true)
//  List<Notification> getAllNotificationsWhereIsReadFalse(Integer userId);
//}
