package com.binar.kelompokd.controllers;

import com.binar.kelompokd.config.Config;
import com.binar.kelompokd.interfaces.IUserAuthService;
import com.binar.kelompokd.models.dto.user.RegisterDTO;
import com.binar.kelompokd.models.dto.user.SendOTPDTO;
import com.binar.kelompokd.models.entity.oauth.Users;
import com.binar.kelompokd.repos.oauth.UserRepository;
import com.binar.kelompokd.services.oauth.EmailSender;
import com.binar.kelompokd.utils.EmailTemplate;
import com.binar.kelompokd.utils.Response;
import com.binar.kelompokd.utils.SimpleStringUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/user-register/")
public class RegisterController {
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

  @Operation(summary = "Register User with username, fullname, phoneNumber, password, and role ('PEMILIK' or 'PENYEWA'). Role is not required yet")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Register User Success",
          content = {@Content(schema = @Schema(example = "User Added!"))})
  })
  @PostMapping("/register")
  public ResponseEntity<Map> saveRegisterManual(@Valid
                                                @RequestBody RegisterDTO objModel) throws RuntimeException {


    Users user = userRepository.checkExistingEmail(objModel.getUsername());
    if (null != user) {
      return new ResponseEntity<Map>(templateCRUD.templateSukses("Username sudah ada"), HttpStatus.OK);

    }
    String result = serviceReq.registerManual(objModel);
    SendOTPDTO username = new SendOTPDTO();
    username.setUsername(objModel.getUsername());
    Map sendOTP = sendEmailRegister(username);
    return new ResponseEntity<Map>(templateCRUD.templateSukses(result), HttpStatus.OK);
  }

  @Value("${expired.token.password.minute}")
  int expiredToken;

  @Operation(summary = "Send Email OTP to User")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "OTP Send!",
          content = {@Content(schema = @Schema(example = "OTP Send!"))})
  })
  @PostMapping("/send-otp")
  public Map sendEmailRegister(
      @NonNull @RequestBody SendOTPDTO user) {
    String message = "Thanks, please check your email for activation.";

    if (user.getUsername() == null) return templateCRUD.badRequest("No email provided");
    Users found = userRepository.findOneByUsername(user.getUsername());
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
    emailSender.sendAsync(found.getUsername(), "Register", template);
    return templateCRUD.templateSukses(message);
  }

  @Operation(summary = "Input OTP from Email")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Input OTP Success!",
          content = {@Content(schema = @Schema(example = "Input OTP Success!"))})
  })
  @GetMapping("/register-confirm-otp/{token}")
  public ResponseEntity<Map> saveRegisterManual(@PathVariable(value = "token") String tokenOtp) throws RuntimeException {
    Users user = userRepository.findOneByOTP(tokenOtp);
    if (null == user) {
      return new ResponseEntity<Map>(templateCRUD.notFound("OTP tidak ditemukan"), HttpStatus.NOT_FOUND);
    }

    if(user.isEnabled()){
      return new ResponseEntity<Map>(templateCRUD.templateSukses("Akun Anda sudah aktif, Silahkan melakukan login"), HttpStatus.OK);
    }
    String today = config.convertDateToString(new Date());

    String dateToken = config.convertDateToString(user.getOtpExpiredDate());
    if(Long.parseLong(today) > Long.parseLong(dateToken)){
      return new ResponseEntity<Map>(templateCRUD.unauthorized("Your token is expired. Please Get token again."), HttpStatus.OK);
    }
    //update user
    user.setEnabled(true);
    userRepository.save(user);
    return new ResponseEntity<Map>(templateCRUD.templateSukses("Sukses, Silahkan Melakukan Login"), HttpStatus.OK);
  }
}
