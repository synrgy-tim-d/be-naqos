package com.binar.kelompokd.controllers;

import com.binar.kelompokd.interfaces.INotificationService;
import com.binar.kelompokd.interfaces.IUserAuthService;
import com.binar.kelompokd.interfaces.ImageService;
import com.binar.kelompokd.models.entity.oauth.Users;
import com.binar.kelompokd.models.request.user.ChangePasswordRequest;
import com.binar.kelompokd.models.request.user.UpdateUserRequest;
import com.binar.kelompokd.models.response.MessageResponse;
import com.binar.kelompokd.models.response.user.UserResponse;
import com.binar.kelompokd.utils.response.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.Map;

@RestController
@AllArgsConstructor
@Tag(name = "User Management", description = "APIs for Managing User")
@RequestMapping("/users")
public class UserController {
  private final static Logger logger = LoggerFactory.getLogger(UserController.class);
  private IUserAuthService userAuthService;
  private PasswordEncoder passwordEncoder;
  private ImageService imageService;
  private INotificationService notificationService;

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
  @PutMapping(value = "/update_data")
  @PreAuthorize("hasRole('ROLE_USER')")
  public ResponseEntity<?> updateUsersAuth(@Valid UpdateUserRequest request) {
    try {
      Users user = userAuthService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
      String phoneNumberRegex = "^8\\d{8,11}$";
      if (!request.getPhoneNumber().matches(phoneNumberRegex)){
        return new ResponseEntity<Map>(res.badRequest("Please input your phone number correctly (start with '8' and 9 to 12 digits range"), HttpStatus.BAD_REQUEST);
      }
      String fullNameRegex = "^[a-zA-Z]+([ ]+[a-zA-Z]+)*$";
      if (!request.getFullname().matches(fullNameRegex)){
        return new ResponseEntity<Map>(res.badRequest("Please input your full name correctly without any number or special character"), HttpStatus.BAD_REQUEST);

      }
      userAuthService.updateUser(user.getId(), request.getFullname(), request.getPhoneNumber());
      notificationService.saveNotification("Update User", "Update User Success", user.getId());
      MessageResponse response = new MessageResponse("User " + user.getUsername() + " Updated!");

      return new ResponseEntity<>(res.templateSukses(response), HttpStatus.OK);
    }catch (Exception e){
      return new ResponseEntity<>(res.badRequest("Update User failed"), HttpStatus.BAD_REQUEST);
    }
  }
  public boolean checkEmpty(Object req){
    return req == null || req.toString().isEmpty();
  }


  @Operation(summary = "Change Password User", tags = {"User Management"})
  @PutMapping("/password")
  @PreAuthorize("hasRole('ROLE_USER')")
  public ResponseEntity<?> changePassword(@Valid @RequestBody ChangePasswordRequest request){

    Users user = userAuthService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
    if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
      return new ResponseEntity<>(res.notAccepted("Wrong Password."), HttpStatus.NOT_ACCEPTABLE);
    }
    if (!request.getNewPassword().equals(request.getConfirmPassword())) {
      return new ResponseEntity<>(res.notAccepted("Password Confirmation Mismatched."), HttpStatus.NOT_ACCEPTABLE);
    }
    if (request.getNewPassword().length() <= 6 ){
      return new ResponseEntity<Map>(res.notAccepted("password must have 6 characters or more"), HttpStatus.NOT_ACCEPTABLE);
    }

    try {
      userAuthService.updatePassword(user.getId(), request.getNewPassword());
      notificationService.saveNotification("Change Password", "Change Password Successfully", user.getId());
      return new ResponseEntity<>(res.templateSukses("Password Changed Successfully."), HttpStatus.OK);
    } catch (Exception e){
     return new ResponseEntity<>(res.templateEror(e), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @Operation(summary = "Upload Avatar User", tags = {"User Management"})
  @PutMapping(value = "/avatar")
  public ResponseEntity<?> updateUsersAuth(Authentication authentication,
                                           @RequestParam("imageFile") MultipartFile imageFile) {
    try {
      if (imageFile.getSize() > 1048576) {
        return new ResponseEntity<>(res.badRequest("File size should be less than 1MB"), HttpStatus.BAD_REQUEST);
      }

      Users user = userAuthService.findByUsername(authentication.getName());
      String url = imageService.uploadFileAvatar(imageFile);
      userAuthService.uploadAvatarUser(user.getId(), url);
      notificationService.saveNotification("Upload Avatar User", "Upload Avatar User Success", user.getId());
      MessageResponse response = new MessageResponse("Avatar " + user.getUsername() + " Updated!");

      return new ResponseEntity<>(res.templateSukses(response), HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(res.badRequest("File upload failed"), HttpStatus.BAD_REQUEST);
    }
  }
}
