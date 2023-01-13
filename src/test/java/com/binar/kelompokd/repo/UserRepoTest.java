package com.binar.kelompokd.repo;

import com.binar.kelompokd.enums.EGender;
import com.binar.kelompokd.models.entity.User;
import com.binar.kelompokd.repos.UserRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@SpringBootTest
public class UserRepoTest {
    @Autowired
    UserRepo userRepo;

    @Test
    void addUser_success(){
        User user = User.builder().name("Eja").email("eja@yahoo.com").password("123").gender(EGender.MALE).build();
        user.setCreatedAt(new Date());
        user.setUpdatedAt(new Date());
        userRepo.save(user);
    }
}
