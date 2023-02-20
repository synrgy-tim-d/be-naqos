package com.binar.kelompokd.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
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
  /**
   * Manage endpoints.
   */
  @Override
  public void configure(HttpSecurity http) throws Exception {
    http.cors()
        .and()
        .csrf()
        .disable()
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
        .authorizeRequests()
        .antMatchers("/auth/**",
            "/swagger-ui/**",
            "/forget-password/**",
            "/naqos-swagger.html",
            "/provinces/**",
            "/cities/**",
            "/public/**",
            "/facilities/**",
            "/v3/api-docs/**",
            "/review/**",
            "/transaction/**").permitAll()
        .antMatchers("/kost/**","/images/**","/users/**","/notifications/**","/rooms/**").hasAnyAuthority("ROLE_PEMILIK", "ROLE_USER")
        .antMatchers("/wishlists/**","/users/**","/notifications/**","/payment/**").hasAnyAuthority("ROLE_PENYEWA", "ROLE_USER")
        .antMatchers("/kost/**","/images/**","/users/**","/notifications/**","/rooms/**","/wishlists/**","/payment/**").hasAnyAuthority("ROLE_SUPERUSER", "ROLE_USER")
        .and()
        .authorizeRequests()
        .anyRequest()
        .authenticated()
        .and()
        .formLogin()
        .permitAll();
  }
}
