package com.binar.kelompokd.controllers;

import com.binar.kelompokd.config.Config;
import com.binar.kelompokd.interfaces.IUserAuthService;
import com.binar.kelompokd.models.dto.user.ResetPasswordDTO;
import com.binar.kelompokd.models.dto.user.SendOTPDTO;
import com.binar.kelompokd.models.entity.oauth.Users;
import com.binar.kelompokd.repos.oauth.UserRepository;
import com.binar.kelompokd.services.oauth.EmailSender;
import com.binar.kelompokd.utils.EmailTemplate;
import com.binar.kelompokd.utils.response.Response;
import com.binar.kelompokd.utils.SimpleStringUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/forget-password/")
@Tag(name = "User Management", description = "APIs for Managing User")
public class ForgetPasswordController {
  private final static Logger logger = LoggerFactory.getLogger(ForgetPasswordController.class);
  @Autowired
  private UserRepository userRepository;

  Config config = new Config();

  @Value("${expired.token.password.minute:}")
  private int expiredToken;

  @Autowired
  public Response templateCRUD;

  @Autowired
  public EmailTemplate emailTemplate;

  @Autowired
  public EmailSender emailSender;

  @Autowired
  private PasswordEncoder passwordEncoder;

  // Step 1 : Send OTP
  @Operation(summary = "Send Email OTP Forget Password", tags = {"User Management"})
  @PostMapping("/forgot-password")//send OTP
  public ResponseEntity<?> sendEmailPassword(@RequestBody SendOTPDTO user) {
    String message = "Thanks, please check your email";

    if (StringUtils.isEmpty(user.getUsername()))
      return new ResponseEntity<>(templateCRUD.badRequest("No email provided"), HttpStatus.BAD_REQUEST);
    Users found = userRepository.findOneByUsername(user.getUsername());
    if (found == null)
      return new ResponseEntity<>(templateCRUD.notFound("Email not found"), HttpStatus.NOT_FOUND); //throw new BadRequest("Email not found");

    String template = emailTemplate.getResetPassword();
    if (StringUtils.isEmpty(found.getOtp())) {
      Users search;
      String otp;
      do {
        otp = SimpleStringUtils.randomString(6, true);
        search = userRepository.findOneByOTP(otp);
      } while (search != null);
      Date dateNow = new Date();
      Calendar calendar = Calendar.getInstance();
      calendar.setTime(dateNow);
      calendar.add(Calendar.MINUTE, expiredToken);
      Date expirationDate = calendar.getTime();

      found.setOtp(otp);
      found.setOtpExpiredDate(expirationDate);
      template = template.replaceAll("\\{\\{PASS_TOKEN}}", otp);
      template = template.replaceAll("\\{\\{USERNAME}}", (found.getUsername() == null ? found.getFullname() : found.getUsername()));

      userRepository.save(found);
    } else {
      template = template.replaceAll("\\{\\{USERNAME}}", (found.getUsername() == null ? found.getFullname() : found.getUsername()));
      template = template.replaceAll("\\{\\{PASS_TOKEN}}", found.getOtp());
    }
    emailSender.sendAsync(found.getUsername(), "Naqos - Forget Password", template);
    logger.info("forget password success");
    return new ResponseEntity<>(templateCRUD.templateSukses("success"), HttpStatus.OK);
  }

  //Step 2 : CHek TOKEN OTP EMAIL
  @Operation(summary = "Check Token OTP Email", tags = {"User Management"})
  @GetMapping("/forgot-password-check-token/{token}")
  public ResponseEntity<?> validateToken(@PathVariable(value = "token") String tokenOtp) {
    if (tokenOtp.isEmpty())
      return new ResponseEntity<>(templateCRUD.badRequest("Token " + config.isRequired), HttpStatus.BAD_REQUEST);

    Users user = userRepository.findOneByOTP(tokenOtp);
    if (user == null) {
      return new ResponseEntity<>(templateCRUD.notFound("Token not valid"), HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<>(templateCRUD.templateSukses("Success"), HttpStatus.OK);
  }

  // Step 3 : lakukan reset password baru
  @Operation(summary = "Reset Password Login Naqos", tags = {"User Management"})
  public boolean checkEmpty(Object req){
    return req == null || req.toString().isEmpty();
  }
  @PostMapping("/forgot-password-reset")
  public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordDTO model) {
    if (model.getOtp() == null) return new ResponseEntity<>(templateCRUD.badRequest("Token " + config.isRequired), HttpStatus.BAD_REQUEST);
    if (model.getNewPassword() == null) return new ResponseEntity<>(templateCRUD.badRequest("New Password " + config.isRequired), HttpStatus.BAD_REQUEST);
    if(checkEmpty(model.getUsername())){
      return new ResponseEntity<Map>(templateCRUD.badRequest("username is required."), HttpStatus.BAD_REQUEST);
    } else if (checkEmpty(model.getNewPassword())) {
      return new ResponseEntity<Map>(templateCRUD.badRequest("New password is required."), HttpStatus.BAD_REQUEST);
    } else if (checkEmpty(model.getOtp())) {
      return new ResponseEntity<Map>(templateCRUD.badRequest("OTP is required."), HttpStatus.BAD_REQUEST);
    }
    if (model.getNewPassword().length() <= 6 ){
      return new ResponseEntity<Map>(templateCRUD.badRequest("password must have 6 characters or more"), HttpStatus.BAD_REQUEST);
    }
    String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
    if (model.getUsername().matches(emailRegex)){
      Users user = userRepository.findOneByOTP(model.getOtp());
      String success;
      if (user == null) return new ResponseEntity<>(templateCRUD.notFound("Token not valid"), HttpStatus.NOT_FOUND);

      user.setPassword(passwordEncoder.encode(model.getNewPassword().replaceAll("\\s+", "")));

      try {
        userRepository.save(user);
        success = "success";
      } catch (Exception e) {
        logger.error("gagal reset password", e);
        return new ResponseEntity<>( templateCRUD.templateEror("Gagal simpan user"), HttpStatus.INTERNAL_SERVER_ERROR);
      }
      return new ResponseEntity<>(templateCRUD.templateSukses(success), HttpStatus.OK);
    }else {
      return new ResponseEntity<Map>(templateCRUD.badRequest("Please input your email address correctly"), HttpStatus.BAD_REQUEST);
    }
  }
}
