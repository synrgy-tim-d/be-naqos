package com.binar.kelompokd.utils.seeder;

import com.binar.kelompokd.models.entity.location.City;
import com.binar.kelompokd.models.entity.location.Province;
import com.binar.kelompokd.models.entity.oauth.*;
import com.binar.kelompokd.repos.location.CityRepository;
import com.binar.kelompokd.repos.location.ProvinceRepository;
import com.binar.kelompokd.repos.oauth.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Component
@Service
public class DatabaseSeeder implements ApplicationRunner {

  private static final String TAG = "DatabaseSeeder {}";

  private final Logger logger = LoggerFactory.getLogger(DatabaseSeeder.class);

  @Autowired
  private PasswordEncoder encoder;

  @Autowired
  private RoleRepository roleRepository;

  @Autowired
  private ClientRepository clientRepository;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private RolePathRepository rolePathRepository;

  @Autowired
  private ProvinceRepository provinceRepository;

  @Autowired
  private CityRepository cityRepository;

  @Autowired
  private BankRepository bankRepository;

  private final String defaultPassword = "password";

  private final String[] users = new String[]{
      "admin@mail.com:ROLE_SUPERUSER ROLE_USER ROLE_ADMIN",
      "user@mail.com:ROLE_USER"
  };

  private final String[] clients = new String[]{
      "my-client-apps:ROLE_READ ROLE_WRITE", // mobile
      "my-client-web:ROLE_READ ROLE_WRITE" // web
  };

  private final String[] roles = new String[] {
      "ROLE_SUPERUSER:user_role:^/.*:GET|PUT|POST|PATCH|DELETE|OPTIONS",
      "ROLE_ADMIN:user_role:^/.*:GET|PUT|POST|PATCH|DELETE|OPTIONS",
      "ROLE_USER:user_role:^/.*:GET|PUT|POST|PATCH|DELETE|OPTIONS",
      "ROLE_READ:oauth_role:^/.*:GET|PUT|POST|PATCH|DELETE|OPTIONS",
      "ROLE_WRITE:oauth_role:^/.*:GET|PUT|POST|PATCH|DELETE|OPTIONS",
      "ROLE_PENYEWA:user_role:^/.*:GET|PUT|POST|PATCH|DELETE|OPTIONS",
      "ROLE_PEMILIK:user_role:^/.*:GET|PUT|POST|PATCH|DELETE|OPTIONS"
  };

  private final String[] provinces = new String[] {
      "Banten", "DKI Jakarta", "DI Yogyakarta", "Jawa Barat", "Jawa Tengah", "Jawa Timur"};

  private final String[] cities = new String[] {
      "Kabupaten Lebak:1", "Kabupaten Pandeglang:1", "Kabupaten Serang:1", "Kabupaten Tangerang:1", "Kota Cilegon:1", "Kota Serang:1", "Kota Tangerang:1", "Kota Tangerang Selatan:1",
      "Kabupaten Administrasi Kepulauan Seribu:2", "Kota Administrasi Jakarta Barat:2", "Kota Administrasi Jakarta Pusat:2", "Kota Administrasi Jakarta Selatan:2", "Kota Administrasi Jakarta Timur:2", "Kota Administrasi Jakarta Utara:2",
      "Kabupaten Bantul:3", "Kabupaten Gunungkidul:3", "Kabupaten Kulon Progo:3", "Kabupaten Sleman:3", "Kota Yogyakarta:3",
      "Kabupaten Bandung:4", "Kabupaten Bandung Barat:4", "Kabupaten Bekasi:4", "Kabupaten Bogor:4", "Kabupaten Ciamis:4", "Kabupaten Cianjur:4", "Kabupaten Cirebon:4", "Kabupaten Garut:4", "Kabupaten Indramayu:4", "Kabupaten Karawang:4", "Kabupaten Kuningan:4", "Kabupaten Majalengka:4", "Kabupaten Pangandaran:4", "Kabupaten Purwakarta:4", "Kabupaten Subang:4", "Kabupaten Sukabumi:4", "Kabupaten Sumedang:4", "Kabupaten Tasikmalaya:4", "Kota Bandung:4", "Kota Banjar:4", "Kota Bekasi:4", "Kota Bogor:4", "Kota Cimahi:4", "Kota Cirebon:4", "Kota Depok:4", "Kota Sukabumi:4", "Kota Tasikmalaya:4",
      "Kabupaten Banjarnegara:5", "Kabupaten Banyumas:5", "Kabupaten Batang:5", "Kabupaten Blora:5", "Kabupaten Boyolali:5", "Kabupaten Brebes:5", "Kabupaten Cilacap:5", "Kabupaten Demak:5", "Kabupaten Grobogan:5", "Kabupaten Jepara:5", "Kabupaten Semarang:5", "Kabupaten Sragen:5", "Kabupaten Sukoharjo:5", "Kabupaten Tegal:5", "Kabupaten Temanggung:5", "Kabupaten Wonogiri:5", "Kabupaten Wonosobo:5", "Kabupaten Karanganyar:5", "Kabupaten Kebumen:5", "Kabupaten Kendal:5", "Kabupaten Klaten:5", "Kabupaten Kudus:5", "Kabupaten Magelang:5", "Kabupaten Pati:5", "Kabupaten Pekalongan:5", "Kabupaten Pemalang:5", "Kabupaten Purbalingga:5", "Kabupaten Purworejo:5", "Kabupaten Rembang:5", "Kota Magelang:5", "Kota Pekalongan:5", "Kota Salatiga:5", "Kota Semarang:5", "Kota Surakarta:5", "Kota Tegal:5",
      "Kabupaten Bangkalan:6", "Kabupaten Banyuwangi:6", "Kabupaten Blitar:6", "Kabupaten Bojonegoro:6", "Kabupaten Bondowoso:6", "Kabupaten Gresik:6", "Kabupaten Jember:6", "Kabupaten Jombang:6", "Kabupaten Kediri:6", "Kabupaten Lamongan:6", "Kabupaten Lumajang:6", "Kabupaten Madiun:6", "Kabupaten Magetan:6", "Kabupaten Malang:6", "Kabupaten Mojokerto:6", "Kabupaten Nganjuk:6", "Kabupaten Ngawi:6", "Kabupaten Pacitan:6", "Kabupaten Pamekasan:6", "Kabupaten Pasuruan:6", "Kabupaten Ponorogo:6", "Kabupaten Probolinggo:6", "Kabupaten Sampang:6", "Kabupaten Sidoarjo:6", "Kabupaten Situbondo:6", "Kabupaten Sumenep:6", "Kabupaten Trenggalek:6", "Kabupaten Tuban:6", "Kabupaten Tulungagung:6", "Kota Batu:6", "Kota Blitar:6", "Kota Kediri:6", "Kota Madiun:6", "Kota Malang:6", "Kota Mojokerto:6", "Kota Pasuruan:6", "Kota Probolinggo:6", "Kota Surabaya:6"
  };

  private final String[] banks = new String[] {
          "Bank BCA;https://cdn.freebiesupply.com/logos/thumbs/2x/bca-bank-central-asia-logo.png",
          "Bank Mandiri;https://logos-download.com/wp-content/uploads/2016/06/Mandiri_logo.png",
          "Bank BNI;https://upload.wikimedia.org/wikipedia/id/thumb/5/55/BNI_logo.svg/1280px-BNI_logo.svg.png",
          "Bank BRI;https://upload.wikimedia.org/wikipedia/commons/thumb/6/68/BANK_BRI_logo.svg/2560px-BANK_BRI_logo.svg.png",
          "Bank BTN;https://upload.wikimedia.org/wikipedia/commons/thumb/f/fd/Bank_BTN_logo.svg/2560px-Bank_BTN_logo.svg.png",
  };

  @Override
  @Transactional
  public void run(ApplicationArguments applicationArguments) {
    String password = encoder.encode(defaultPassword);

    this.insertRoles();
    this.insertClients(password);
    this.insertUser(password);
    this.insertProvince();
    this.insertCity();
    this.insertBank();
  }

  @Transactional
  public void insertRoles() {
    for (String role: roles) {
      String[] str = role.split(":");
      String name = str[0];
      String type = str[1];
      String pattern = str[2];
      String[] methods = str[3].split("\\|");
      Roles oldRole = roleRepository.findOneByName(name);
      if (null == oldRole) {
        oldRole = new Roles();
        oldRole.setName(name);
        oldRole.setType(type);
        oldRole.setRolePaths(new ArrayList<>());
        for (String m: methods) {
          String rolePathName = name.toLowerCase()+"_"+m.toLowerCase();
          RolePath rolePath = rolePathRepository.findOneByName(rolePathName);
          if (null == rolePath) {
            rolePath = new RolePath();
            rolePath.setName(rolePathName);
            rolePath.setMethod(m.toUpperCase());
            rolePath.setPattern(pattern);
            rolePath.setRole(oldRole);
            rolePathRepository.save(rolePath);
            oldRole.getRolePaths().add(rolePath);
          }
        }
      }

      roleRepository.save(oldRole);
    }
  }

  @Transactional
  public void insertClients(String password) {
    for (String c: clients) {
      String[] s = c.split(":");
      String clientName = s[0];
      String[] clientRoles = s[1].split("\\s");
      Client oldClient = clientRepository.findOneByClientId(clientName);
      if (null == oldClient) {
        oldClient = new Client();
        oldClient.setClientId(clientName);
        oldClient.setAccessTokenValiditySeconds(28800);//1 jam 3600 :token valid : seharian kerja : normal 1 jam
        oldClient.setRefreshTokenValiditySeconds(7257600);// refresh
        oldClient.setGrantTypes("password refresh_token authorization_code");
        oldClient.setClientSecret(password);
        oldClient.setApproved(true);
        oldClient.setRedirectUris("");
        oldClient.setScopes("read write");
        List<Roles> rls = roleRepository.findByNameIn(clientRoles);

        if (rls.size() > 0) {
          oldClient.getAuthorities().addAll(rls);
        }
      }
      clientRepository.save(oldClient);
    }
  }

  @Transactional
  public void insertUser(String password) {
    for (String userNames: users) {
      String[] str = userNames.split(":");
      String username = str[0];
      String[] roleNames = str[1].split("\\s");

      Users oldUser = userRepository.findOneByUsername(username);
      if (null == oldUser) {
        oldUser = new Users();
        oldUser.setUsername(username);
        oldUser.setPassword(password);
        oldUser.setPhoneNumber("0218207977");
        List<Roles> r = roleRepository.findByNameIn(roleNames);
        oldUser.setRoles(r);
      }
      userRepository.save(oldUser);
    }
  }

  @Transactional
  public void insertProvince(){
    for (String provinceName: provinces){
      Province oldProvince = provinceRepository.findByProvince(provinceName);
      if (oldProvince==null){
        oldProvince = new Province();
        oldProvince.setProvince(provinceName);

      }
      provinceRepository.save(oldProvince);
    }
  }

  @Transactional
  public void insertCity(){
    for (String citiesName: cities){
      String[] str = citiesName.split(":");
      String city = str[0];
      Integer province = Integer.valueOf(str[1]);
      City oldCities = cityRepository.getCityByName(city);
      if (oldCities==null){
        oldCities = new City();
        oldCities.setCity(city);
        Province setProv = provinceRepository.findById(province).get();
        oldCities.setProvince(setProv);
      }
      cityRepository.save(oldCities);
    }
  }

  @Transactional
  public void insertBank() {
    for (String bank : banks){
      String[] str = bank.split(";");
      String bankName = str[0];
      String logoUrl = str[1];
      Bank isExist = bankRepository.findByName(bankName);
      if (isExist == null){
        isExist = new Bank();
        isExist.setName(bankName);
        isExist.setLogoUrl(logoUrl);

      }
      bankRepository.save(isExist);
    }
  }
}
