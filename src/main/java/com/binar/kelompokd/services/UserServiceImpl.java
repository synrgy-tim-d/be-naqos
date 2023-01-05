package com.binar.kelompokd.services;

import com.binar.kelompokd.interfaces.IUserService;
import com.binar.kelompokd.models.dto.LoginDTO;
import com.binar.kelompokd.models.dto.RegisterDTO;
import com.binar.kelompokd.models.entity.User;
import com.binar.kelompokd.models.oauath.Role;
import com.binar.kelompokd.models.oauath.Users;
import com.binar.kelompokd.repos.oauth.RoleRepository;
import com.binar.kelompokd.repos.oauth.UserRepository;
import com.binar.kelompokd.utils.Config;
import com.binar.kelompokd.utils.Response;
import lombok.AllArgsConstructor;
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
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements IUserService {
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

    @Override
    public Map login(LoginDTO loginModel) {
        /**
         * bussines logic for login here
         * **/
        try {
            Map<String, Object> map = new HashMap<>();

            Users checkUser = userRepository.findOneByUsername(loginModel.getEmail());

            if ((checkUser != null) && (encoder.matches(loginModel.getPassword(), checkUser.getPassword()))) {
                if (!checkUser.isEnabled()) {
                    map.put("is_enabled", checkUser.isEnabled());
                    return templateResponse.templateEror(map);
                }
            }
            if (checkUser == null) {
                return templateResponse.notFound("user not found");
            }
            if (!(encoder.matches(loginModel.getPassword(), checkUser.getPassword()))) {
                return templateResponse.templateEror("wrong password");
            }
            String url = baseUrl + "/oauth/token?username=" + loginModel.getEmail() +
                    "&password=" + loginModel.getPassword() +
                    "&grant_type=password" +
                    "&client_id=my-client-web" +
                    "&client_secret=password";
            ResponseEntity<Map> response = restTemplateBuilder.build().exchange(url, HttpMethod.POST, null, new
                    ParameterizedTypeReference<Map>() {
                    });

            if (response.getStatusCode() == HttpStatus.OK) {
                Users user = userRepository.findOneByUsername(loginModel.getEmail());
                List<String> roles = new ArrayList<>();

                for (Role role : user.getRoles()) {
                    roles.add(role.getName());
                }
                //save token
//                checkUser.setAccessToken(response.getBody().get("access_token").toString());
//                checkUser.setRefreshToken(response.getBody().get("refresh_token").toString());
//                userRepository.save(checkUser);

                map.put("access_token", response.getBody().get("access_token"));
                map.put("token_type", response.getBody().get("token_type"));
                map.put("refresh_token", response.getBody().get("refresh_token"));
                map.put("expires_in", response.getBody().get("expires_in"));
                map.put("scope", response.getBody().get("scope"));
                map.put("jti", response.getBody().get("jti"));

                return map;
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
    public Map registerManual(RegisterDTO registerModel) {
        Map map = new HashMap();
        try {
            String[] roleNames = {"ROLE_USER", "ROLE_READ", "ROLE_CUSTOMER"}; // admin
            Users user = new Users();
            user.setUsername(registerModel.getEmail().toLowerCase());
            user.setFullname(registerModel.getFullname());

            //step 1 :
            user.setEnabled(false); // matikan user

            String password = encoder.encode(registerModel.getPassword().replaceAll("\\s+", ""));
            List<Role> r = repoRole.findByNameIn(roleNames);

            user.setRoles(r);
            user.setPassword(password);
            Users obj = userRepository.save(user);

            return templateResponse.templateSukses(obj);

        } catch (Exception e) {
            logger.error("Eror registerManual=", e);
            return templateResponse.templateEror("eror:"+e);
        }

    }



//    @Override
//    public Map updateUser(Users user ) {
//        try {
//        /*
//        bisa validasi : atau tidak
//        wajib melakukan pengekan ke db : ada atau ganya id supplier
//         */
//            if (user.getUsername() == null) {
//                return  templateResponse.error("Username wajib diisi.", Config.ERROR_401);
//            }
////            wajib melakukan pengekan ke db : ada atau ganya id supplier
//            Users chekData = userRepository.checkExistingEmail(user.getUsername());
//            if (chekData.getUsername() == null) {
//                return  templateResponse.error("Data tidak ditemukan", Config.ERROR_404);
//            }
//
////            if (user.getPassword() == null) {
////                return  templateResponse.error("Password wajib diisi.", Config.ERROR_401);
////            }
////            if (user.getAddress() == null) {
////                return  templateResponse.error("Akanat wajib diisi.", Config.ERROR_401);
////            }
//            //do update
//            chekData.setUsername(user.getUsername());
//            chekData.setPassword(user.getPassword());
//
//            //langsung update
//            Users updateUser = userRepository.save(chekData);
//            return templateResponse.sukses(updateUser);
//        } catch (Exception e) {
//            logger.error("Eror save,{} " + e);
//            return templateResponse.error("eror update: " + e.getMessage(), Config.ERROR_500);
//        }
//    }

}