package com.binar.kelompokd.controllers;

import com.binar.kelompokd.config.Config;
import com.binar.kelompokd.interfaces.IUserAuthService;
import com.binar.kelompokd.models.dto.ResetPasswordDTO;
import com.binar.kelompokd.models.dto.SendForgetPasswordDTO;
import com.binar.kelompokd.models.entity.oauth.Users;
import com.binar.kelompokd.repos.oauth.UserRepository;
import com.binar.kelompokd.services.EmailSender;
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
  public Map sendEmailPassword(@RequestBody SendForgetPasswordDTO user) {
    String message = "Thanks, please check your email";

    if (StringUtils.isEmpty(user.getEmail())) return templateCRUD.templateEror("No email provided");
    Users found = userRepository.findOneByUsername(user.getEmail());
    if (found == null) return templateCRUD.notFound("Email not found"); //throw new BadRequest("Email not found");

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
    return templateCRUD.templateSukses("success");
  }

  //Step 2 : CHek TOKEN OTP EMAIL
  @Operation(summary = "Check Token OTP Email")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Check OTP!",
          content = {@Content(schema = @Schema(example = "Check OTP!"))})
  })
  @PostMapping("/forgot-password-chek-token")
  public Map cheKTOkenValid(@RequestBody ResetPasswordDTO model) {
    if (model.getOtp() == null) return templateCRUD.notFound("Token " + config.isRequired);

    Users user = userRepository.findOneByOTP(model.getOtp());
    if (user == null) {
      return templateCRUD.templateEror("Token not valid");
    }
    return templateCRUD.templateSukses("Success");
  }

  // Step 3 : lakukan reset password baru
  @Operation(summary = "Reset Password Login Naqos")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Reset Password Login Naqos!",
          content = {@Content(schema = @Schema(example = "Reset Password Login Naqos!"))})
  })
  @PostMapping("/forgot-password-reset")
  public Map<String, String> resetPassword(@RequestBody ResetPasswordDTO model) {
    if (model.getOtp() == null) return templateCRUD.notFound("Token " + config.isRequired);
    if (model.getNewPassword() == null) return templateCRUD.notFound("New Password " + config.isRequired);
    Users user = userRepository.findOneByOTP(model.getOtp());
    String success;
    if (user == null) return templateCRUD.notFound("Token not valid");

    user.setPassword(passwordEncoder.encode(model.getNewPassword().replaceAll("\\s+", "")));
    user.setOtpExpiredDate(null);
    user.setOtp(null);

    try {
      userRepository.save(user);
      success = "success";
    } catch (Exception e) {
      return templateCRUD.templateEror("Gagal simpan user");
    }
    return templateCRUD.templateSukses(success);
  }
}
