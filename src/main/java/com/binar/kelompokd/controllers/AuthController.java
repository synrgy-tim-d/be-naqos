package com.binar.kelompokd.controllers;

import com.binar.kelompokd.config.Config;
import com.binar.kelompokd.interfaces.IUserAuthService;
import com.binar.kelompokd.models.dto.user.LoginDTO;
import com.binar.kelompokd.models.dto.user.RegisterDTO;
import com.binar.kelompokd.models.dto.user.SendOTPDTO;
import com.binar.kelompokd.models.entity.oauth.Users;
import com.binar.kelompokd.repos.oauth.UserRepository;
import com.binar.kelompokd.services.oauth.EmailSender;
import com.binar.kelompokd.utils.EmailTemplate;
import com.binar.kelompokd.utils.response.Response;
import com.binar.kelompokd.utils.SimpleStringUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;


import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.util.*;

@RestController
@Tag(name = "User Management", description = "APIs for Managing User")
@RequestMapping("/auth")
public class AuthController {
  private final static Logger logger = LoggerFactory.getLogger(AuthController.class);
  @Autowired
  private UserRepository userRepository;

  Config config = new Config();

  @Autowired
  public IUserAuthService serviceReq;
  @Autowired
  public EmailTemplate emailTemplate;

  @Autowired
  public EmailSender emailSender;

  @Autowired
  public Response templateCRUD;

  @Value("${BASEURL:}")//FILE_SHOW_RUL
  private String BASEURL;


  @Operation(summary = "User Login with username/email and password", tags = {"User Management"})
  @PostMapping("/login")
  public ResponseEntity<Map> login(@Valid @RequestBody LoginDTO objModel) {
    Map map = serviceReq.login(objModel);
    return new ResponseEntity<Map>(map, (HttpStatus) map.get("message"));
  }

  @Operation(summary = "Register User with username, fullname, phoneNumber, password, and role ('PEMILIK' or 'PENYEWA'). Role is not required yet", tags = {"User Management"})
  @PostMapping("/register")
  public ResponseEntity<Map> saveRegisterManual(@Valid @RequestBody RegisterDTO objModel) throws RuntimeException {
    Users user = userRepository.checkExistingEmail(objModel.getUsername());
    if (null != user) {
      return new ResponseEntity<Map>(templateCRUD.templateSukses("Username sudah ada"), HttpStatus.OK);

    }

    if(checkEmpty(objModel.getUsername())){
      return new ResponseEntity<Map>(templateCRUD.badRequest("username is required."), HttpStatus.BAD_REQUEST);
    } else if (checkEmpty(objModel.getPassword())) {
      return new ResponseEntity<Map>(templateCRUD.badRequest("password is required."), HttpStatus.BAD_REQUEST);
    } else if (checkEmpty(objModel.getFullname())) {
      return new ResponseEntity<Map>(templateCRUD.badRequest("fullname is required."), HttpStatus.BAD_REQUEST);
    }else if (checkEmpty(objModel.getPhoneNumber())) {
      return new ResponseEntity<Map>(templateCRUD.badRequest("phone is required."), HttpStatus.BAD_REQUEST);
    }else if (checkEmpty(objModel.getRole())) {
      return new ResponseEntity<Map>(templateCRUD.badRequest("role is required."), HttpStatus.BAD_REQUEST);
    }

    if (objModel.getPassword().length() <= 6 ){
      return new ResponseEntity<Map>(templateCRUD.badRequest("password must have 6 characters or more"), HttpStatus.BAD_REQUEST);
    }
    String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

    if(objModel.getUsername().matches(emailRegex)) {
      // Email is valid
      String result = serviceReq.registerManual(objModel);
      SendOTPDTO username = new SendOTPDTO();
      username.setUsername(objModel.getUsername());
      Map sendOTP = sendEmailRegister(username);
      return new ResponseEntity<Map>(templateCRUD.templateSukses(result), HttpStatus.OK);
    } else {
      // Email is invalid
      return new ResponseEntity<Map>(templateCRUD.badRequest("Please input your email address correctly"), HttpStatus.BAD_REQUEST);
    }
  }
public boolean checkEmpty(Object req){
  return req == null || req.toString().isEmpty();
}
  @Operation(summary = "Register Google Testing", tags = {"User Management"})
  @PostMapping("/register-google")
  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<Map> saveRegisterManualGoogle(@Valid
                                                @RequestBody RegisterDTO objModel) throws RuntimeException {


    Users user = userRepository.checkExistingEmail(objModel.getUsername());
    if (null != user) {
      return new ResponseEntity<Map>(templateCRUD.templateSukses("Username is already exist"), HttpStatus.OK);

    }
    String result = serviceReq.registerManual(objModel);
//    SendOTPDTO username = new SendOTPDTO();
//    username.setUsername(objModel.getUsername());
//    Map sendOTP = sendEmailRegister(username);
    return new ResponseEntity<Map>(templateCRUD.templateSukses(result), HttpStatus.OK);
  }

  @Value("${expired.token.password.minute}")
  int expiredToken;

  @Operation(summary = "Send Email OTP to User", tags = {"User Management"})
  @PostMapping("/send-otp")
  public Map sendEmailRegister(
      @NonNull @RequestBody SendOTPDTO user) {
    String message = "Thanks, please check your email for activation.";

    if (user.getUsername() == null) return templateCRUD.badRequest("No email provided");
    Users found = userRepository.findOneByUsername(user.getUsername());
    if (checkEmpty(user.username)){
      return templateCRUD.badRequest("Username is required."); //throw new BadRequest("Email not found");
    }
    if (found == null) return templateCRUD.notFound("Email not found"); //throw new BadRequest("Email not found");


    String template = emailTemplate.getRegisterTemplate();
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
      template = template.replaceAll("\\{\\{USERNAME}}", (found.getFullname() == null ? found.getUsername() : found.getFullname()));
      template = template.replaceAll("\\{\\{VERIFY_TOKEN}}",  otp);
      userRepository.save(found);
    } else {
      template = template.replaceAll("\\{\\{USERNAME}}", (found.getFullname() == null ? found.getUsername() : found.getFullname()));
      template = template.replaceAll("\\{\\{VERIFY_TOKEN}}",  found.getOtp());
    }
    emailSender.sendAsync(found.getUsername(), "Naqos - Register", template);
    return templateCRUD.templateSukses(message);
  }

  @Operation(summary = "Input OTP from Email", tags = {"User Management"})
  @GetMapping("/register-confirm-otp/{token}")
  public ResponseEntity<Map> saveRegisterManual(@PathVariable(value = "token") String tokenOtp) throws RuntimeException {
    Users user = userRepository.findOneByOTP(tokenOtp);
    if (null == user) {
      return new ResponseEntity<Map>(templateCRUD.notFound("OTP not found"), HttpStatus.NOT_FOUND);
    }

    if(user.isEnabled()){
      return new ResponseEntity<Map>(templateCRUD.templateSukses("Your account is active now. Please login!"), HttpStatus.OK);
    }
    String today = config.convertDateToString(new Date());

    String dateToken = config.convertDateToString(user.getOtpExpiredDate());
    if(Long.parseLong(today) > Long.parseLong(dateToken)){
      return new ResponseEntity<Map>(templateCRUD.unauthorized("Your token is expired. Please Get token again!"), HttpStatus.GATEWAY_TIMEOUT);
    }
    //update user
    user.setEnabled(true);
    userRepository.save(user);
    return new ResponseEntity<Map>(templateCRUD.templateSukses("Verification success. Please login!"), HttpStatus.OK);
  }
}
