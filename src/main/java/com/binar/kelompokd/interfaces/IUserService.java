package com.binar.kelompokd.interfaces;

import com.binar.kelompokd.models.dto.LoginDTO;
import com.binar.kelompokd.models.dto.RegisterDTO;
import com.binar.kelompokd.models.entity.User;
import com.binar.kelompokd.models.oauath.Users;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface IUserService {
    public Map login(LoginDTO loginModel);

    public Map registerManual(RegisterDTO registerModel) ;




}
