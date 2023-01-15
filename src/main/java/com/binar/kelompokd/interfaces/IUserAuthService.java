package com.binar.kelompokd.interfaces;

import com.binar.kelompokd.models.dto.LoginDTO;
import com.binar.kelompokd.models.dto.RegisterDTO;

import java.util.Map;

public interface IUserAuthService {
    Map registerManual(RegisterDTO objModel) ;
    Map login(LoginDTO loginModel);

}
