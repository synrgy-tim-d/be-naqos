//package com.binar.kelompokd.utils;
//
//import com.binar.kelompokd.services.UserDetailServiceImpl;
//import io.jsonwebtoken.*;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.security.core.Authentication;
//import org.springframework.stereotype.Component;
//
//import java.util.Date;
//
//@Component
//public class JwtUtils {
//    private static final Logger LOG = LoggerFactory.getLogger(JwtUtils.class);
//
//    @Value("${jwtSecret}")
//    private String jwtSecret;
//
//    @Value("${jwtExpirationMs}")
//    private int jwtExpirationMs;
//
//    public String generateJwt(Authentication auth) {
//        UserDetailServiceImpl userDetailService = (UserDetailServiceImpl) auth.getPrincipal();
//        return Jwts.builder()
//                .setSubject((userDetailService))
//                .setIssuedAt(new Date())
//                .setExpiration(new Date(new Date().getTime()+ jwtExpirationMs))
//                .signWith(SignatureAlgorithm.HS512, jwtSecret)
//                .compact();
//    }
//
//    public String getFromJwtToken(String token) {
//        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJwt(token).getBody().getSubject();
//    }
//
//    public boolean validateToken(String token) {
//        try {
//            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
//            return true;
//        } catch (SignatureException e) {
//            LOG.error("Invalid JWT Signature: {}", e.getMessage());
//        } catch (MalformedJwtException e) {
//            LOG.error("Invalid JWT Token: {}", e.getMessage());
//        } catch (ExpiredJwtException e) {
//            LOG.error("JWT token is expired: {}", e.getMessage());
//        } catch (UnsupportedJwtException e) {
//            LOG.error("JWT token is unsupported: {}", e.getMessage());
//        } catch (IllegalArgumentException e) {
//            LOG.error("JWT claims string is empty: {}", e.getMessage());
//        }
//        return false;
//    }
//}
