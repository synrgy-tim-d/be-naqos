package com.binar.kelompokd.controllers;

import com.binar.kelompokd.interfaces.IKostWishlistService;
import com.binar.kelompokd.interfaces.INotificationService;
import com.binar.kelompokd.interfaces.IUserAuthService;
import com.binar.kelompokd.interfaces.KostService;
import com.binar.kelompokd.models.entity.kost.KostWishlist;
import com.binar.kelompokd.models.entity.kost.Kost;
import com.binar.kelompokd.models.entity.oauth.Users;
import com.binar.kelompokd.models.request.wishlist.WishlistAuthRequest;
import com.binar.kelompokd.models.response.MessageResponse;
import com.binar.kelompokd.models.response.wishlist.WishlistResponse;
import com.binar.kelompokd.models.response.wishlist.WishlistStatusResponse;
import com.binar.kelompokd.utils.response.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@RestController
@Tag(name = "Wishlist Managment", description = "APIs for Managing Wishlist User")
@RequestMapping("/wishlists")
public class KostWishlistController {
  private IKostWishlistService kostWishlistService;
  private IUserAuthService iUserAuthService;
  private KostService kostService;
  private INotificationService notificationService;

  @Autowired
  private Response response;

  @Operation(summary = "Get all wishlist by user.", description = "Get all wishlist by user.", tags ={"Wishlist Managment"})
  @GetMapping("/get")
  public ResponseEntity<MessageResponse> getAllWishlistUser(Authentication authentication,
                                                            @RequestParam(value = "page", defaultValue = "0") int page,
                                                            @RequestParam(value = "size", defaultValue = "10") int size){
    Users user = iUserAuthService.findByUsername(authentication.getName());
    List<KostWishlist> list = kostWishlistService.readWishList(user.getId());
    List<Kost> kosts = new ArrayList<>();
    for (KostWishlist wishlist : list){
      Kost kost = wishlist.getKostId();
      kosts.add(kost);
    }
    Page<Kost> kostResponsePage = new PageImpl<>(kosts);
    return kostService.getMessageResponse(page,size,kostResponsePage);
  }

  @Operation(summary = "Get status wishlist kost by user.", description = "Get status wishlist kost by user.", tags ={"Wishlist Managment"})
  @GetMapping("/status")
  public ResponseEntity<?> getAWishlistAuth(@RequestParam("kostId") String kostId,
                                                                 Authentication authentication) {
    Users user = iUserAuthService.findByUsername(authentication.getName());
    Long userId = user.getId();
    List<KostWishlist> wishlist = kostWishlistService.getAWishlist(UUID.fromString(kostId), userId);

    Boolean hasil = !wishlist.isEmpty();

    WishlistStatusResponse wishlistStatusResponse = new WishlistStatusResponse(kostId, userId, hasil);
    return new ResponseEntity<>(response.templateSukses(wishlistStatusResponse), HttpStatus.OK);
  }

  @Operation(summary = "Add Kost to user wishlist.", description = "Add Kost to user wishlist.", tags ={"Wishlist Managment"})
  @PostMapping("/add")
  public ResponseEntity<?> addWishListAuth(@RequestBody WishlistAuthRequest request,
                                                          Authentication authentication) {
    Users user = iUserAuthService.findByUsername(authentication.getName());
    Long userId = user.getId();
    Users users = iUserAuthService.findUsersById(userId);
    Kost kosts = kostService.getKostById(UUID.fromString(request.getKostId()));
    KostWishlist wishlist = new KostWishlist(users, kosts);
    kostWishlistService.createWishList(wishlist);
    WishlistResponse wishlistResponse = new WishlistResponse(request.getKostId(), userId, "Add '" + kosts.getName() + "' to " + users.getUsername() + "'s Wishlist.");
    notificationService.saveNotification("Kost Wishlist Added","Added "+kosts.getName()+" to your Wishlist", userId);
    return new ResponseEntity<>(response.created(wishlistResponse), HttpStatus.CREATED);
  }

  @Operation(summary = "delete Kost from user wishlist.", description = "delete Kost from user wishlist.", tags ={"Wishlist Managment"})
  @DeleteMapping("/destroy")
  public ResponseEntity<?> deleteWishlistAuth(@RequestParam("kostId") String kostId,
                                                             Authentication authentication) {
    Users user = iUserAuthService.findByUsername(authentication.getName());
    Long userId = user.getId();
    Kost kosts = kostService.getKostById(UUID.fromString(kostId));
    try {
      kostWishlistService.deleteWishlistByKostIdAndUserId(UUID.fromString(kostId), userId);
      WishlistResponse wishlistResponse = new WishlistResponse(kostId, userId, "Deleted '" + kosts.getName() + "' from " + user.getUsername() + "'s Wishlist.");
      notificationService.saveNotification("Kost Wishlist deleted","deleted "+kosts.getName()+" from your Wishlist", userId);
      return new ResponseEntity<>(response.created(wishlistResponse), HttpStatus.ACCEPTED);
    } catch (Exception e) {
      return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
