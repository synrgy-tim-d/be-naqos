package com.binar.kelompokd.controllers;

import com.binar.kelompokd.config.Config;
import com.binar.kelompokd.interfaces.IUserAuthService;
import com.binar.kelompokd.models.dto.LoginDTO;
import com.binar.kelompokd.repos.oauth.UserRepository;
import com.binar.kelompokd.utils.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/user-login/")
public class LoginController {
  @Autowired
  private UserRepository userRepository;

  Config config = new Config();

  @Autowired
  public IUserAuthService serviceReq;

  @Autowired
  public Response templateCRUD;

  @Operation(summary = "User Login with username/email and password")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Login Success!",
          content = {@Content(schema = @Schema(example = "Login Success!"))})
  })
  @PostMapping("/login")
  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<Map> login(@Valid @RequestBody LoginDTO objModel) {
    Map map = serviceReq.login(objModel);
    return new ResponseEntity<Map>(map, HttpStatus.OK);
  }

}
