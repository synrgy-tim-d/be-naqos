package com.binar.kelompokd.repos.oauth;

import com.binar.kelompokd.models.entity.oauth.Users;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends PagingAndSortingRepository<Users, Long> {
  @Query("FROM Users u WHERE LOWER(u.username) = LOWER(?1)")
  Users findOneByUsername(String username);

  @Query("FROM Users u WHERE u.otp = ?1")
  Users findOneByOTP(String otp);

  @Query("FROM Users u WHERE LOWER(u.username) = LOWER(:username)")
  Users checkExistingEmail(String username);

  Users findUsersById(Long id);

  Users findByEmail(String email);
}
