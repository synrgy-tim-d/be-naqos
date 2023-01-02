package com.binar.kelompokd.interfaces;

import com.binar.kelompokd.models.entity.UserModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IUserService {
    List<UserModel> getAllUsers();
}
