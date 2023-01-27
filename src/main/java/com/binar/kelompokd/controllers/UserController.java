package com.binar.kelompokd.controllers;

import com.binar.kelompokd.interfaces.IUserAuthService;
import com.binar.kelompokd.models.entity.oauth.Users;
import com.binar.kelompokd.models.request.ChangePasswordRequest;
import com.binar.kelompokd.models.request.UpdateUserRequest;
import com.binar.kelompokd.models.response.MessageResponse;
import com.binar.kelompokd.models.response.UserResponse;
import com.binar.kelompokd.utils.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@Tag(name = "User Management", description = "APIs for Managing User")
@RequestMapping("/users")
public class UserController {
  private final static Logger logger = LoggerFactory.getLogger(UserController.class);
  private IUserAuthService userAuthService;
  private PasswordEncoder passwordEncoder;

  @Autowired
  Response res;

  @Operation(summary = "Get User With Token", tags = {"User Management"})
  @GetMapping("/get")
  public ResponseEntity<?> GetUserByToken(Authentication authentication){
    Users user = userAuthService.findByUsername(authentication.getName());
    if (user == null) return new ResponseEntity<>(res.notFound("User not Found"), HttpStatus.NOT_FOUND);
    UserResponse response = new UserResponse(user);
    logger.info("Get User",response);
    return new ResponseEntity<>(res.templateSukses(response),HttpStatus.OK);
  }

  @Operation(summary = "Update Data User with fullname and phoneNumber", tags = {"User Management"})
  @PutMapping("/update_data")
  public ResponseEntity<?> updateUsersAuth(Authentication authentication,
                                           @Valid @RequestBody UpdateUserRequest request) {
    Users user = userAuthService.findByUsername(authentication.getName());
    userAuthService.updateUser(user.getId(), request.getFullname(), request.getPhoneNumber());
    MessageResponse response = new MessageResponse("User " + user.getUsername() + " Updated!");
    return new ResponseEntity<>(res.templateSukses(response), HttpStatus.OK);
  }

  @Operation(summary = "Change Password User", tags = {"User Management"})
  @PutMapping("/password")
  public ResponseEntity<?> changePassword(Authentication authentication,
                                          @RequestBody ChangePasswordRequest request){

    Users user = userAuthService.findByUsername(authentication.getName());

    try {
      if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
        return new ResponseEntity<>(res.notAccepted("Wrong Password."), HttpStatus.NOT_ACCEPTABLE);
      }

      if (!request.getNewPassword().equals(request.getConfirmPassword())) {
        return new ResponseEntity<>(res.notAccepted("Password Confirmation Mismatched."), HttpStatus.NOT_ACCEPTABLE);
      }

      userAuthService.updatePassword(user.getId(), request.getNewPassword());
      return new ResponseEntity<>(res.templateSukses("Password Changed Successfully."), HttpStatus.OK);

    } catch (NullPointerException e){
     return new ResponseEntity<>(res.templateEror(e), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
