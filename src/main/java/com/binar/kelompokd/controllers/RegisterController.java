package com.binar.kelompokd.controllers;

import com.binar.kelompokd.config.Config;
import com.binar.kelompokd.interfaces.IUserAuthService;
import com.binar.kelompokd.models.dto.RegisterDTO;
import com.binar.kelompokd.models.entity.oauth.Users;
import com.binar.kelompokd.repos.oauth.UserRepository;
import com.binar.kelompokd.services.EmailSender;
import com.binar.kelompokd.utils.EmailTemplate;
import com.binar.kelompokd.utils.Response;
import com.binar.kelompokd.utils.SimpleStringUtils;
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
  @PostMapping("/register")
  public ResponseEntity<Map> saveRegisterManual(@Valid
                                                @RequestBody RegisterDTO objModel) throws RuntimeException {
    Map map = new HashMap();

    Users user = userRepository.checkExistingEmail(objModel.getUsername());
    if (null != user) {
      return new ResponseEntity<Map>(templateCRUD.notFound("Username sudah ada"), HttpStatus.OK);

    }
    map = serviceReq.registerManual(objModel);
    Map sendOTP = sendEmailegister(objModel);
    return new ResponseEntity<Map>(map, HttpStatus.OK);
  }

  @Value("${expired.token.password.minute}")
  int expiredToken;

  @PostMapping("/send-otp")//send OTP
  public Map sendEmailegister(
      @RequestBody RegisterDTO user) {
    String message = "Thanks, please check your email for activation.";

    if (user.getUsername() == null) return templateCRUD.templateEror("No email provided");
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

  @GetMapping("/register-confirm-otp/{token}")
  public ResponseEntity<Map> saveRegisterManual(@PathVariable(value = "token") String tokenOtp) throws RuntimeException {
    Users user = userRepository.findOneByOTP(tokenOtp);
    if (null == user) {
      return new ResponseEntity<Map>(templateCRUD.templateEror("OTP tidak ditemukan"), HttpStatus.OK);
    }

    if(user.isEnabled()){
      return new ResponseEntity<Map>(templateCRUD.templateSukses("Akun Anda sudah aktif, Silahkan melakukan login"), HttpStatus.OK);
    }
    String today = config.convertDateToString(new Date());

    String dateToken = config.convertDateToString(user.getOtpExpiredDate());
    if(Long.parseLong(today) > Long.parseLong(dateToken)){
      return new ResponseEntity<Map>(templateCRUD.templateEror("Your token is expired. Please Get token again."), HttpStatus.OK);
    }
    //update user
    user.setEnabled(true);
    userRepository.save(user);
    return new ResponseEntity<Map>(templateCRUD.templateSukses("Sukses, Silahkan Melakukan Login"), HttpStatus.OK);
  }
}
