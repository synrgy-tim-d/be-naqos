package com.binar.kelompokd.service.users;

import com.binar.kelompokd.models.entity.oauth.Users;
import com.binar.kelompokd.repos.oauth.UserRepository;
import com.binar.kelompokd.services.oauth.UserServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UsersServiceImplTest {
  @Mock
  private UserRepository usersRepository;

  @InjectMocks
  private UserServiceImpl usersService;

  @Test
  @DisplayName("Assert find User by Id return User")
  void findUsersByIdTest(){
    Users user = new Users();
    user.setId(1L);
    user.setUsername("test@email.com");
    user.setPhoneNumber("081212");
    user.setPassword("password1234");

    Mockito.when(usersRepository.findUsersById(1L)).thenReturn(user);
    assertEquals(usersService.findUsersById(1L), user);
  }

  @Test
  @DisplayName("Assert find User by username/email return User")
  void findByUsernameTest(){
    String email = "test@email.com";
    Users users1 = new Users();
    users1.setId(1L);
    users1.setUsername("test@email.com");
    users1.setFullname("Mochito");
    users1.setPhoneNumber("081212");
    users1.setPassword("password1234");

    Mockito.when(usersRepository.findByUsername(email)).thenReturn(users1);
    Users mocked = usersService.findByUsername(email);

    assertEquals("Mochito", mocked.getFullname());
    assertEquals("test@email.com", mocked.getUsername());
    assertEquals("password1234", mocked.getPassword());
  }

}
