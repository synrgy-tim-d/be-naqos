package com.binar.kelompokd.interfaces;

import com.binar.kelompokd.models.dto.user.LoginDTO;
import com.binar.kelompokd.models.dto.user.RegisterDTO;
import com.binar.kelompokd.models.entity.oauth.Users;

import java.util.Map;

public interface IUserAuthService {
  String registerManual(RegisterDTO objModel) ;
  Map login(LoginDTO loginModel);
  Users findUsersById(Long id);
  Users findByUsername(String email);
  void updateUser(Long id, String fullname, String phoneNumber);
  void updatePassword(Long id, String password);
  void uploadAvatarUser(Long id, String url);
}
