package com.binar.kelompokd.repos.oauth;

import com.binar.kelompokd.models.entity.oauth.Users;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface UserRepository extends PagingAndSortingRepository<Users, Long> {
  @Query("FROM Users u WHERE LOWER(u.username) = LOWER(?1)")
  Users findOneByUsername(String username);

  @Query("FROM Users u WHERE u.otp = ?1")
  Users findOneByOTP(String otp);

  @Query("FROM Users u WHERE LOWER(u.username) = LOWER(:username)")
  Users checkExistingEmail(String username);

  Users findUsersById(Long id);

  @Modifying
  @Query(value = "update oauth_user set fullname=?2, phone_number=?3 where id=?1", nativeQuery = true)
  Integer updateUser(Long id, String fullname, String phoneNumber);

  @Modifying
  @Query(value = "update oauth_user set img_url=?2 where id=?1", nativeQuery = true)
  Integer uploadAvatar(Long id, String imgUrl);

  @Modifying
  @Query(value = "update oauth_user set password=?2 where id=?1", nativeQuery = true)
  Integer updatePassword(Long id, String password);

}
