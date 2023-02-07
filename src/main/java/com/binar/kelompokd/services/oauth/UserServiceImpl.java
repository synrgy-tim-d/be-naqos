package com.binar.kelompokd.services.oauth;


import com.binar.kelompokd.interfaces.ICloudinaryService;
import com.binar.kelompokd.interfaces.IUserAuthService;
import com.binar.kelompokd.models.dto.user.LoginDTO;
import com.binar.kelompokd.models.dto.user.RegisterDTO;
import com.binar.kelompokd.models.dto.user.RegisterGoogleDTO;
import com.binar.kelompokd.models.entity.oauth.Roles;
import com.binar.kelompokd.models.entity.oauth.Users;
import com.binar.kelompokd.repos.oauth.RoleRepository;
import com.binar.kelompokd.repos.oauth.UserRepository;
import com.binar.kelompokd.utils.response.Response;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.oauth2.Oauth2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.List;

@Service
public class UserServiceImpl implements IUserAuthService {
  @Value("${BASEURL}")
  private String baseUrl;
  @Autowired
  private RestTemplateBuilder restTemplateBuilder;
  @Autowired
  private UserRepository userRepository;
  @Autowired
  RoleRepository repoRole;
  @Autowired
  private PasswordEncoder encoder;
  private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
  @Autowired
  public Response templateResponse;
  private ICloudinaryService iCloudinaryService;

  @Override
  public Map login(LoginDTO loginModel) {
    try {
      Map<String, Object> map = new HashMap<>();

      Users checkUser = userRepository.findOneByUsername(loginModel.getUsername());

      if ((checkUser != null) && (encoder.matches(loginModel.getPassword(), checkUser.getPassword()))) {
        if (!checkUser.isEnabled()) {
          map.put("is_enabled", checkUser.isEnabled());
          return templateResponse.unauthorized(map);
        }
      }
      if (checkUser == null) {
        return templateResponse.notFound("User not found");
      }
      if (!(encoder.matches(loginModel.getPassword(), checkUser.getPassword()))) {
        return templateResponse.unauthorized("Your password is not correct");
      }
      String url = baseUrl +"/api"+ "/oauth/token?username=" + loginModel.getUsername() +
              "&password=" + loginModel.getPassword() +
              "&grant_type=password" +
              "&client_id=my-client-web" +
              "&client_secret=password";
      ResponseEntity<Map> response = restTemplateBuilder.build().exchange(url, HttpMethod.POST, null, new
              ParameterizedTypeReference<Map>() {
              });

      if (response.getStatusCode() == HttpStatus.OK) {
        Users user = userRepository.findOneByUsername(loginModel.getUsername());
        List<String> roles = new ArrayList<>();

        for (Roles role : user.getRoles()) {
          roles.add(role.getName());
        }

        map.put("access_token", response.getBody().get("access_token"));
        map.put("token_type", response.getBody().get("token_type"));
        map.put("refresh_token", response.getBody().get("refresh_token"));
        map.put("expires_in", response.getBody().get("expires_in"));
        map.put("scope", response.getBody().get("scope"));
        map.put("jti", response.getBody().get("jti"));
        map.put("user_id", checkUser.getId());
        if (checkUser.getRoles().size() >2){
          map.put("role", checkUser.getRoles().get(2).getName()) ;
        }
        map.put("status",response.getStatusCode());
        map.put("code",response.getStatusCodeValue());
        return templateResponse.templateSukses(map);
      } else {
        return templateResponse.notFound("user not found");
      }
    } catch (HttpStatusCodeException e) {
      e.printStackTrace();
      if (e.getStatusCode() == HttpStatus.BAD_REQUEST) {
        return templateResponse.templateEror("invalid login");
      }
      return templateResponse.templateEror(e);
    } catch (Exception e) {
      e.printStackTrace();
      return templateResponse.templateEror(e);
    }
  }

  @Override
  public Users findUsersById(Long id) {
    return userRepository.findUsersById(id);
  }

  @Override
  public Users findByUsername(String email) {
    return userRepository.findByUsername(email);
  }

  @Override
  public void updateUser(Long id, String fullname, String phoneNumber) {
    userRepository.updateUser(id, fullname, phoneNumber);
  }

  @Override
  public void updatePassword(Long id, String password) {
    userRepository.updatePassword(id, encoder.encode(password));
  }

  @Override
  public void uploadAvatarUser(Long id, String url) {
    userRepository.uploadAvatar(id, url);
  }

  @Override
  public String registerManual(RegisterDTO registerModel) {
    Map map = new HashMap();
    try {
      List<Roles> r;
      if (registerModel.getRole() != null){
        if (registerModel.getRole().equals("PENYEWA")){
          String[] roleNames = new String[]{"ROLE_USER", "ROLE_READ", "ROLE_PENYEWA"}; // penyewa
          r = repoRole.findByNameIn(roleNames);
        }else if (registerModel.getRole().equals("PEMILIK")){
          String[] roleNames = new String[]{"ROLE_USER", "ROLE_READ", "ROLE_PEMILIK"}; // pemilik
          r = repoRole.findByNameIn(roleNames);
        }else {
          String[] roleNames = new String[]{"ROLE_USER", "ROLE_READ"}; // default
          r = repoRole.findByNameIn(roleNames);
        }
      }else{
        String[] roleNames = new String[]{"ROLE_USER", "ROLE_READ"}; // default
        r = repoRole.findByNameIn(roleNames);
      }
      Users user = new Users();
      user.setUsername(registerModel.getUsername().toLowerCase());
      user.setFullname(registerModel.getFullname());
      user.setPhoneNumber(registerModel.getPhoneNumber());
      //step 1 :
      user.setEnabled(false); // matikan user
      String password = encoder.encode(registerModel.getPassword().replaceAll("\\s+", ""));
      user.setRoles(r);
      user.setPassword(password);
      Users obj = userRepository.save(user);
      return "Register User Success";
    } catch (Exception e) {
      logger.error("Eror registerManual=", e);
      return ("eror:"+e);
    }
  }

  @Override
  public String registerGoogle(RegisterGoogleDTO registerModel) {
    Map map = new HashMap();
    try {
      List<Roles> r;
      if (registerModel.getRole() != null){
        if (registerModel.getRole().equals("PENYEWA")){
          String[] roleNames = new String[]{"ROLE_USER", "ROLE_READ", "ROLE_PENYEWA"}; // penyewa
          r = repoRole.findByNameIn(roleNames);
        }else if (registerModel.getRole().equals("PEMILIK")){
          String[] roleNames = new String[]{"ROLE_USER", "ROLE_READ", "ROLE_PEMILIK"}; // pemilik
          r = repoRole.findByNameIn(roleNames);
        }else {
          String[] roleNames = new String[]{"ROLE_USER", "ROLE_READ"}; // default
          r = repoRole.findByNameIn(roleNames);
        }
      }else{
        String[] roleNames = new String[]{"ROLE_USER", "ROLE_READ"}; // default
        r = repoRole.findByNameIn(roleNames);
      }
      Users user = new Users();
      user.setUsername(registerModel.getUsername().toLowerCase());
      user.setFullname(registerModel.getFullname());
      user.setPhoneNumber(registerModel.getPhoneNumber());
      user.setImgUrl(registerModel.getImageUrl());
      //step 1 :
      user.setEnabled(true); // enable user
      String password = encoder.encode(registerModel.getPassword().replaceAll("\\s+", ""));
      user.setRoles(r);
      user.setPassword(password);
      Users obj = userRepository.save(user);
      return "Register User Success";
    } catch (Exception e) {
      logger.error("Eror registerManual=", e);
      return ("eror:"+e);
    }
  }
  @PostMapping("/avatar")
  public ResponseEntity<?> uploadImage(@RequestParam("imageFile") MultipartFile imageFile) {
    String url = iCloudinaryService.uploadFile(imageFile);
    return new ResponseEntity<>(templateResponse.templateSukses(url), HttpStatus.CREATED);
  }
}
