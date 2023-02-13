package com.binar.kelompokd.controllers;

import com.binar.kelompokd.interfaces.IKostReviewService;
import com.binar.kelompokd.interfaces.INotificationService;
import com.binar.kelompokd.interfaces.IUserAuthService;
import com.binar.kelompokd.interfaces.KostService;
import com.binar.kelompokd.models.entity.kost.Kost;
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
  private INotificationService notificationService;
  private KostService kostService;

  @Operation(summary = "Add Kost Review by User.", description = "Add Kost Review by User.", tags ={"Kost Review Management"})
  @PostMapping("/add")
  public ResponseEntity<?> addKostReview(@RequestParam("kostId") UUID kostId,
                                         @RequestParam("rating") double rating,
                                         @RequestParam("ratingText") String reviewText,
                                         Authentication authentication) {
    Users user = iUserAuthService.findByUsername(authentication.getName());
    Long userId = user.getId();
    Kost kost = kostService.getKostById(kostId);
    try {
      kostReviewService.addReviewKost(kostId,userId,rating,reviewText);
      notificationService.saveNotification("Added Kost Review", "Thank you for submitted your review of "+ kost.getName(), user.getId());
      return new ResponseEntity<>(response.templateSukses("Add Review Kost Success"), HttpStatus.OK);
    } catch (Exception e){
      notificationService.saveNotification("Failed Added Review", "Failed to Added Review Kost "+ kost.getName(), user.getId());
      return new ResponseEntity<>(response.unauthorized("Please Login to Add Kost Review"), HttpStatus.UNAUTHORIZED);
    }
  }
}
