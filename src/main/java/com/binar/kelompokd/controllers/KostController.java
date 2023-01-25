package com.binar.kelompokd.controllers;

import com.binar.kelompokd.interfaces.CityService;
import com.binar.kelompokd.interfaces.IUserAuthService;
import com.binar.kelompokd.interfaces.ImageService;
import com.binar.kelompokd.models.entity.kost.Kost;
import com.binar.kelompokd.models.entity.location.City;
import com.binar.kelompokd.models.entity.oauth.Users;
import com.binar.kelompokd.interfaces.KostService;
import com.binar.kelompokd.models.response.NewKostResponse;
import com.binar.kelompokd.utils.Response;
import com.binar.kelompokd.utils.SimpleStringUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@AllArgsConstructor
@RestController
@RequestMapping("/kost")
public class KostController {

  @Autowired
  KostService kostService;

  @Autowired
  SimpleStringUtils simpleStringUtils;

  private IUserAuthService iUserAuthService;
  private ImageService imageService;

  private CityService cityService;

  @Autowired
  public Response templateCRUD;

  @GetMapping()
  public ResponseEntity<?> getAllKosts(){
    return new ResponseEntity<>(templateCRUD.templateSukses(kostService.getAllKost()), HttpStatus.OK);
  }

  @GetMapping("/city-id/{cityId}")
  public ResponseEntity<?> getKostsByCityId(
          @RequestParam() @Schema(example = "1") int page,
          @RequestParam() @Schema(example = "10") int size,
          @RequestParam(required = false, defaultValue = "id") @Schema(example = "id") String orderBy,
          @RequestParam(required = false, defaultValue = "desc") @Schema(example = "desc") String orderType,
          @PathVariable("cityId") @Schema(example = "1") Integer cityId){
    Pageable pageable = simpleStringUtils.getShort(orderBy, orderType, page-1, size);
    Page<Kost> kosts = kostService.getKostsByCityId(cityId, pageable);
    return new ResponseEntity<>(templateCRUD.templateSukses(kosts.getContent()), HttpStatus.OK);
  }

  @GetMapping("/by-city/{cityName}")
  public ResponseEntity<?> getKostsByCityName(
          @RequestParam() @Schema(example = "1") int page,
          @RequestParam() @Schema(example = "10") int size,
          @RequestParam(required = false, defaultValue = "id") @Schema(example = "id") String orderBy,
          @RequestParam(required = false, defaultValue = "desc") @Schema(example = "desc") String orderType,
          @PathVariable("cityName") @Schema(example = "Kabupaten Lebak") String cityName){
    Pageable pageable = simpleStringUtils.getShort(orderBy, orderType, page-1, size);
    Page<Kost> kosts = kostService.getKostsByCity(cityName, pageable);
    return new ResponseEntity<>(templateCRUD.templateSukses(kosts.getContent()), HttpStatus.OK);
  }

  @GetMapping("/page")
  public ResponseEntity<?> getAllKostsWithPaginationAndFilter(@RequestParam() @Schema(example = "1") int page, @RequestParam() @Schema(example = "10") int size, @RequestParam(required = false, defaultValue = "id") @Schema(example = "id") String orderBy, @RequestParam(required = false, defaultValue = "desc") @Schema(example = "desc") String orderType){
    Pageable pageable = simpleStringUtils.getShort(orderBy, orderType, page-1, size);
    Page<Kost> kosts = kostService.getListData(pageable);
    return new ResponseEntity<>(kosts.getContent(), HttpStatus.OK);
  }

  @GetMapping("/by-type/page")
  public ResponseEntity<?> getKostsByKostType(
          @RequestParam() @Schema(example = "1") int page,
          @RequestParam() @Schema(example = "10") int size,
          @RequestParam(required = false, defaultValue = "id") @Schema(example = "id") String orderBy,
          @RequestParam(required = false, defaultValue = "desc") @Schema(example = "desc") String orderType,
          @RequestParam() @Schema(example = "KOS_PUTRA") String kostType){
    Pageable pageable = simpleStringUtils.getShort(orderBy, orderType, page-1, size);
    Page<Kost> kosts = kostService.getKostsByKostType(kostType, pageable);
    return new ResponseEntity<>(kosts.getContent(), HttpStatus.OK);
  }
  @GetMapping("/{id}")
  public ResponseEntity<?> getKostById(@PathVariable("id") @Schema(example = "123e4567-e89b-12d3-a456-426614174000") UUID id){

    try {
      Kost kost = kostService.getKostById(id);
      return new ResponseEntity<>(kost, HttpStatus.OK);
    }
    catch (NoSuchElementException noSuchElementException){
      return new ResponseEntity<>("error : \"Kos doesn't exist\"", HttpStatus.NOT_FOUND);
    }
  }

  @Operation(summary = "Add Kost", description = "Add Kost")
  @PostMapping("/add")
  public ResponseEntity<?> createKost(@RequestParam("imageFiles") MultipartFile[] imageFiles,
                                                    @RequestParam("name") String name,
                                                    @RequestParam("description") String description,
                                                    @RequestParam("kostType") String kostType,
                                                    @RequestParam("isAvailable") Boolean isAvailable,
                                                    @RequestParam("latitude") Double latitude,
                                                    @RequestParam("longitude") Double longitude,
                                                    @RequestParam("address") String address,
                                                    @RequestParam("subdistrict") String subdistrict,
                                                    @RequestParam("district") String district,
                                                    @RequestParam("postalCode") String postalCode,
                                                    @RequestParam("cityId") Integer cityId,
                                                    Authentication authentication){
    List<String> urls = new ArrayList<>();
    UUID uuid = UUID.randomUUID();
    Users user = iUserAuthService.findByUsername(authentication.getName());
    City cityKost = cityService.getCityById(cityId);

    if (imageFiles.length > 4){
      return new ResponseEntity<>(templateCRUD.badRequest("Max Files Upload are 4 files"),HttpStatus.BAD_REQUEST);
    }

    try {
      Arrays.stream(imageFiles)
          .forEach(imageFile -> {
            urls.add(imageService.uploadFileKost(imageFile));
          });

      kostService.saveKost(uuid, name, description, kostType, isAvailable, latitude, longitude,address, subdistrict, district, postalCode, user.getId(), cityKost.getId());
      Kost currentKost = kostService.getKostById(uuid);
      if (currentKost == null){
        return new ResponseEntity<>(templateCRUD.notFound("Kost Not Found"),HttpStatus.NOT_FOUND);
      } else {
        for (String url : urls) {
          imageService.saveImageKostToDb(url, currentKost);
        }
      }
      NewKostResponse kostResponse = new NewKostResponse(currentKost, currentKost.getOwnerId());
      return new ResponseEntity<>(templateCRUD.templateSukses(kostResponse), HttpStatus.CREATED);
    } catch (Exception e){
      return new ResponseEntity<>(templateCRUD.badRequest(e), HttpStatus.BAD_REQUEST);
    }
  }

  @PatchMapping("/{id}")
  public ResponseEntity<?> updateKost(@PathVariable("id") @Schema(example = "123e4567-e89b-12d3-a456-426614174000") UUID id, @RequestBody Kost kost){
    try {
      return new ResponseEntity<>(kostService.updateKost(id, kost), HttpStatus.OK);
    }
    catch (NoSuchElementException noSuchElementException){
      return new ResponseEntity<>("error : \"Kos doesn't exist\"", HttpStatus.NOT_FOUND);
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteKost(@PathVariable("id") @Schema(example = "123e4567-e89b-12d3-a456-426614174000") UUID id){

    try {
      return new ResponseEntity<>(kostService.deleteKost(id), HttpStatus.NO_CONTENT);
    }
    catch (EmptyResultDataAccessException emptyResultDataAccessException){
      return new ResponseEntity<>("error : \"Kos doesn't exist\"", HttpStatus.NOT_FOUND);
    }
  }

  @DeleteMapping("/soft-delete/{id}")
  public ResponseEntity<?> softDeleteKost(@PathVariable("id") @Schema(example = "123e4567-e89b-12d3-a456-426614174000") UUID id){

    try {
      return new ResponseEntity<>(kostService.softDeleteKost(id), HttpStatus.NO_CONTENT);
    }
    catch (EmptyResultDataAccessException emptyResultDataAccessException){
      return new ResponseEntity<>("error : \"Kos doesn't exist\"", HttpStatus.NOT_FOUND);
    }
  }
}
