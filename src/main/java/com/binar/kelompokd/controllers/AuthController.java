package com.binar.kelompokd.controllers;

import com.binar.kelompokd.config.Config;
import com.binar.kelompokd.interfaces.IUserAuthService;
import com.binar.kelompokd.models.dto.user.LoginDTO;
import com.binar.kelompokd.models.dto.user.RegisterDTO;
import com.binar.kelompokd.models.dto.user.RegisterGoogleDTO;
import com.binar.kelompokd.models.dto.user.SendOTPDTO;
import com.binar.kelompokd.models.entity.oauth.Users;
import com.binar.kelompokd.repos.oauth.UserRepository;
import com.binar.kelompokd.services.oauth.EmailSender;
import com.binar.kelompokd.utils.EmailTemplate;
import com.binar.kelompokd.utils.SimpleStringUtils;
import com.binar.kelompokd.utils.response.Response;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.oauth2.Oauth2;
import com.google.api.services.oauth2.model.Userinfoplus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;


import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
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
  private PasswordEncoder passwordEncoder;
  @Autowired
  private RestTemplateBuilder restTemplateBuilder;
  @Autowired
  public Response templateCRUD;
  @Value("${BASEURL:}")//FILE_SHOW_RUL
  private String BASEURL;

  public boolean checkEmpty(Object req){
    return req == null || req.toString().isEmpty();
  }

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
  @Operation(summary = "Register Google Testing", tags = {"User Management"})
  @PostMapping("/register-google")
  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<Map> saveRegisterManualGoogle(@Valid @RequestBody RegisterGoogleDTO objModel) throws RuntimeException {

    Users user = userRepository.checkExistingEmail(objModel.getUsername());
    if (null != user) {
      return new ResponseEntity<Map>(templateCRUD.templateSukses("Username is already exist"), HttpStatus.OK);
    }
    String result = serviceReq.registerGoogle(objModel);
    return new ResponseEntity<Map>(templateCRUD.templateSukses(result), HttpStatus.OK);
  }

  @Value("${expired.token.password.minute}")
  int expiredToken;

  @Operation(summary = "Send Email OTP to User", tags = {"User Management"})
  @PostMapping("/send-otp")
  public Map sendEmailRegister(@NonNull @RequestBody SendOTPDTO user) {
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
  @PostMapping("/google")
  @Operation(summary = "Login - Register Google Account roleUser must = 'PEMILIK' or 'PENYEWA' ", tags = {"User Management"})
  @ResponseBody
  public ResponseEntity<Map> repairGoogleSigninAction(@NotNull @RequestParam String roleUser ) throws Exception {
    Map<String, Object> map123 = new HashMap<>();
    String accessToken = serviceReq.googleAuthorize();
    GoogleCredential credential = new GoogleCredential().setAccessToken(accessToken);
    System.out.println("access_token user=" + accessToken);
    Oauth2 oauth2 = new Oauth2.Builder(new NetHttpTransport(), new JacksonFactory(), credential).setApplicationName("Oauth2").build();
    Userinfoplus profile= null;
    try {
      profile = oauth2.userinfo().get().execute();
    }catch (GoogleJsonResponseException e)
    {
      return new ResponseEntity<Map>(templateCRUD.badRequest(e.getDetails()), HttpStatus.BAD_GATEWAY);
    }
    profile.toPrettyString();
    Users user = userRepository.findOneByUsername(profile.getEmail());
    if (user != null) {
      if(!user.isEnabled()){
        user.setEnabled(true); // matikan user
      }

      LoginDTO login = new LoginDTO();
      login.setUsername(profile.getEmail());
      login.setPassword(profile.getId());
      ResponseEntity<Map> mapLogin = login(login);

      String url = BASEURL + "/api/oauth/token?username=" + login.getUsername() +
              "&password=" + login.getPassword() +
              "&grant_type=password" +
              "&client_id=my-client-web" +
              "&client_secret=password";
      ResponseEntity<Map> response123 = restTemplateBuilder.build().exchange(url, HttpMethod.POST, null, new ParameterizedTypeReference<Map>() {});

      if (response123.getStatusCode() == HttpStatus.OK) {
        map123.put("access_token", response123.getBody().get("access_token"));
        map123.put("token_type", response123.getBody().get("token_type"));
        map123.put("refresh_token", response123.getBody().get("refresh_token"));
        map123.put("expires_in", response123.getBody().get("expires_in"));
        map123.put("scope", response123.getBody().get("scope"));
        map123.put("jti", response123.getBody().get("jti"));
        map123.put("user_id", user.getId());
        if (user.getRoles().size() >2){
          map123.put("role", user.getRoles().get(2).getName()) ;
        }
        map123.put("status", response123.getStatusCode());
        map123.put("code", response123.getStatusCodeValue());
        userRepository.save(user);
        return new ResponseEntity<Map>(templateCRUD.templateSukses(mapLogin.getBody().get("data")), HttpStatus.OK);
      }
    } else {
      RegisterGoogleDTO registerModel = new RegisterGoogleDTO();
      registerModel.setUsername(profile.getEmail());
      registerModel.setFullname(profile.getName()+profile.getFamilyName());
      registerModel.setPassword(profile.getId());
      registerModel.setRole(roleUser);
      registerModel.setImageUrl(profile.getPicture());
      saveRegisterManualGoogle(registerModel);

      LoginDTO login = new LoginDTO();
      login.setUsername(profile.getEmail());
      login.setPassword(profile.getId());
      ResponseEntity<Map> mapLogin = login(login);

      return new ResponseEntity<Map>(templateCRUD.templateSukses(mapLogin.getBody().get("data")), HttpStatus.OK);
    }
    return new ResponseEntity<Map>(map123, HttpStatus.OK);
  }
}
