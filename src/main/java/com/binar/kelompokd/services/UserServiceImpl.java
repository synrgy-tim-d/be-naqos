package com.binar.kelompokd.services;

import com.binar.kelompokd.interfaces.IUserService;
import com.binar.kelompokd.models.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserServiceImpl implements IUserService {
  @Override
  public List<User> getAllUsers() {
    return null;
  }

  @Override
  public User findUsersById(Integer id) {
    return null;
  }

  @Override
  public User findByEmail(String email) {
    return null;
  }
}
