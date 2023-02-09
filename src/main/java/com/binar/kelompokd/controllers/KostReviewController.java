package com.binar.kelompokd.controllers;

import com.binar.kelompokd.interfaces.IKostReviewService;
import com.binar.kelompokd.interfaces.IUserAuthService;
import com.binar.kelompokd.models.entity.oauth.Users;
import com.binar.kelompokd.utils.response.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@AllArgsConstructor
@RestController
@Tag(name = "Kost Review Management", description = "APIs for Managing Review Kost")
@RequestMapping("/review")
public class KostReviewController {
  private IKostReviewService kostReviewService;
  private IUserAuthService iUserAuthService;
  @Autowired
  private Response response;

  @Operation(summary = "Add Kost Review by User.", description = "Add Kost Review by User.", tags ={"Kost Review Management"})
  @PostMapping("/add")
  public ResponseEntity<?> addKostReview(@RequestParam("kostId") UUID kostId,
                                         @RequestParam("rating") double rating,
                                         @RequestParam("ratingText") String reviewText,
                                         Authentication authentication) {
    try {
      Users user = iUserAuthService.findByUsername(authentication.getName());
      Long userId = user.getId();
      kostReviewService.addReviewKost(kostId,userId,rating,reviewText);
      return new ResponseEntity<>(response.templateSukses("Add Review Kost Success"), HttpStatus.OK);
    } catch (Exception e){
      return new ResponseEntity<>(response.unauthorized("Please Login to Add Kost Review"), HttpStatus.UNAUTHORIZED);
    }
  }
}
