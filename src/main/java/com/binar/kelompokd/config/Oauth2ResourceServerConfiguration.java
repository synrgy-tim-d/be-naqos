package com.binar.kelompokd.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

@Configuration
@EnableResourceServer
@EnableGlobalMethodSecurity(securedEnabled = true) //secure definition
public class Oauth2ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

  /**
   * Manage resource server.
   */
  @Override
  public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
    super.configure(resources);
  }
//    private static final String SECURED_PATTERN = "/api/**";
  /**
   * Manage endpoints.
   */
  @Override
  public void configure(HttpSecurity http) throws Exception {
    http.cors()
        .and()
        .csrf()
        .disable()
        .antMatcher("/**")
        .authorizeRequests()
        .antMatchers("/user-login/*",
            "/user-register/**",
            "/swagger-ui/**",
            "/forget-password/*",
            "/naqos-swagger.html",
            "/kost",
            "/province/*",
            "/city/*",
            "/v3/api-docs/**").permitAll()
        .antMatchers("/kost/**","/image/*", "/wishlist/*").hasAnyAuthority("ROLE_READ")
        .antMatchers("/kost/add","/image/*","/wishlist/*").hasAnyAuthority("ROLE_WRITE")
        .antMatchers("/kost/**","/image/*","/wishlist/*").hasAnyAuthority("ROLE_USER")
//        .antMatchers("/v1/role-test-global/post-barang-admin").hasAnyAuthority("ROLE_ADMIN")
        .and()
        .authorizeRequests()
        .anyRequest()
        .authenticated()
        .and()
        .formLogin()
        .permitAll();
  }
}
