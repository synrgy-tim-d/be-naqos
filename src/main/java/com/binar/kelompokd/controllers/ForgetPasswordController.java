package com.binar.kelompokd.controllers;

import com.binar.kelompokd.config.Config;
import com.binar.kelompokd.interfaces.IUserAuthService;
import com.binar.kelompokd.models.dto.user.ResetPasswordDTO;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/forget-password/")
public class ForgetPasswordController {

  @Autowired
  private UserRepository userRepository;

  Config config = new Config();

  @Autowired
  public IUserAuthService serviceReq;

  @Value("${expired.token.password.minute:}")//FILE_SHOW_RUL
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
  @Operation(summary = "Send Email OTP Forget Password")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "OTP Send!",
                  content = {@Content(schema = @Schema(example = "OTP Send!"))})
  })
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
      template = template.replaceAll("\\{\\{USERNAME}}", (found.getUsername() == null ? "" +
          "@UserName"
          :
          "@" + found.getUsername()));

      userRepository.save(found);
    } else {
      template = template.replaceAll("\\{\\{USERNAME}}", (found.getUsername() == null ? "" +
          "@UserName"
          :
          "@" + found.getUsername()));
      template = template.replaceAll("\\{\\{PASS_TOKEN}}", found.getOtp());
    }
    emailSender.sendAsync(found.getUsername(), "Chute - Forget Password", template);
    return new ResponseEntity<>(templateCRUD.templateSukses("success"), HttpStatus.OK);
  }

  //Step 2 : CHek TOKEN OTP EMAIL
  @Operation(summary = "Check Token OTP Email")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Check OTP!",
                  content = {@Content(schema = @Schema(example = "Check OTP!"))})
  })
  @PostMapping("/forgot-password-check-token")
  public ResponseEntity<?> validateToken(@RequestBody ResetPasswordDTO model) {
    if (model.getOtp() == null)
      return new ResponseEntity<>(templateCRUD.badRequest("Token " + config.isRequired), HttpStatus.BAD_REQUEST);

    Users user = userRepository.findOneByOTP(model.getOtp());
    if (user == null) {
      return new ResponseEntity<>(templateCRUD.notFound("Token not valid"), HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<>(templateCRUD.templateSukses("Success"), HttpStatus.OK);
  }

  // Step 3 : lakukan reset password baru
  @Operation(summary = "Reset Password Login Naqos")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Reset Password Login Naqos!",
          content = {@Content(schema = @Schema(example = "Reset Password Login Naqos!"))})
  })
  @PostMapping("/forgot-password-reset")
  public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordDTO model) {
    if (model.getOtp() == null) return new ResponseEntity<>(templateCRUD.badRequest("Token " + config.isRequired), HttpStatus.BAD_REQUEST);
    if (model.getNewPassword() == null) return new ResponseEntity<>(templateCRUD.badRequest("New Password " + config.isRequired), HttpStatus.BAD_REQUEST);
    Users user = userRepository.findOneByOTP(model.getOtp());
    String success;
    if (user == null) return new ResponseEntity<>(templateCRUD.notFound("Token not valid"), HttpStatus.NOT_FOUND);

    user.setPassword(passwordEncoder.encode(model.getNewPassword().replaceAll("\\s+", "")));
    user.setOtpExpiredDate(null);
    user.setOtp(null);

    try {
      userRepository.save(user);
      success = "success";
    } catch (Exception e) {
      return new ResponseEntity<>( templateCRUD.templateEror("Gagal simpan user"), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return new ResponseEntity<>(templateCRUD.templateSukses(success), HttpStatus.OK);
  }
}
