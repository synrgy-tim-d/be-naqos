package com.binar.kelompokd.services;

import com.binar.kelompokd.interfaces.IUserService;
import com.binar.kelompokd.models.entity.UserModel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserServiceImpl implements IUserService {
    @Override
    public List<UserModel> getAllUsers() {
        return null;
    }
}
