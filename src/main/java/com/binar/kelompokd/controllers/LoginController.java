package com.binar.kelompokd.controllers;

import com.binar.kelompokd.interfaces.IUserAuthService;
import com.binar.kelompokd.models.dto.user.LoginDTO;
import com.binar.kelompokd.repos.oauth.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.util.Map;

@RestController
@Tag(name = "User Management", description = "APIs for Managing User")
@RequestMapping("/user-login/")
public class LoginController {
  @Autowired
  private UserRepository userRepository;

  @Autowired
  public IUserAuthService serviceReq;

  @Operation(summary = "User Login with username/email and password", tags = {"User Management"})
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Login Success!",
          content = {@Content(schema = @Schema(example = "Login Success!"))})
  })
  @PostMapping("/login")
  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<Map> login(@Valid @RequestBody LoginDTO objModel) {
    Map map = serviceReq.login(objModel);
      return new ResponseEntity<Map>(map, (HttpStatus) map.get("message"));
  }

}
