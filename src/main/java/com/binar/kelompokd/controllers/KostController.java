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
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@AllArgsConstructor
@RestController
@RequestMapping("/kost")
@Tag(name = "Kost Management", description = "APIs for Managing Kost")
public class KostController {
  private final static Logger logger = LoggerFactory.getLogger(KostController.class);
  @Autowired
  KostService kostService;
  @Autowired
  SimpleStringUtils simpleStringUtils;

  private IUserAuthService iUserAuthService;
  private ImageService imageService;
  private CityService cityService;

  @Autowired
  public Response templateCRUD;

  @Operation(summary = "Get All List Kosts", tags = {"Kost Management"})
  @GetMapping()
  public ResponseEntity<?> getAllKosts(){
    return new ResponseEntity<>(templateCRUD.templateSukses(kostService.getAllKost()), HttpStatus.OK);
  }

  @Operation(summary = "Get List Kosts By City Id", tags = {"Kost Management"})
  @GetMapping("/city-id/{cityId}")
  public ResponseEntity<?> getKostsByCityId(
          @RequestParam() @Schema(example = "1") int page,
          @RequestParam() @Schema(example = "10") int size,
          @RequestParam(required = false, defaultValue = "id") @Schema(example = "id") String orderBy,
          @RequestParam(required = false, defaultValue = "desc") @Schema(example = "desc") String orderType,
          @PathVariable("cityId") @Schema(example = "1") Integer cityId){
    Pageable pageable = simpleStringUtils.getShort(orderBy, orderType, page-1, size);
    Page<Kost> kosts = kostService.getKostsByCityId(cityId, pageable);
    logger.info("list Kosts",kosts);
    return new ResponseEntity<>(templateCRUD.templateSukses(kosts.getContent()), HttpStatus.OK);
  }

  @Operation(summary = "Get List Kosts by City Name", tags = {"Kost Management"})
  @GetMapping("/by-city/{cityName}")
  public ResponseEntity<?> getKostsByCityName(
          @RequestParam() @Schema(example = "1") int page,
          @RequestParam() @Schema(example = "10") int size,
          @RequestParam(required = false, defaultValue = "id") @Schema(example = "id") String orderBy,
          @RequestParam(required = false, defaultValue = "desc") @Schema(example = "desc") String orderType,
          @PathVariable("cityName") @Schema(example = "Kabupaten Lebak") String cityName){
    Pageable pageable = simpleStringUtils.getShort(orderBy, orderType, page-1, size);
    Page<Kost> kosts = kostService.getKostsByCity(cityName, pageable);
    logger.info("list Kosts By City",kosts);
    return new ResponseEntity<>(templateCRUD.templateSukses(kosts.getContent()), HttpStatus.OK);
  }

  @Operation(summary = "Get All List Kosts with Pagination", tags = {"Kost Management"})
  @GetMapping("/page")
  public ResponseEntity<?> getAllKostsWithPaginationAndFilter(@RequestParam() @Schema(example = "1") int page, @RequestParam() @Schema(example = "10") int size, @RequestParam(required = false, defaultValue = "id") @Schema(example = "id") String orderBy, @RequestParam(required = false, defaultValue = "desc") @Schema(example = "desc") String orderType){
    Pageable pageable = simpleStringUtils.getShort(orderBy, orderType, page-1, size);
    Page<Kost> kosts = kostService.getListData(pageable);
    logger.info("list Kosts page",kosts);
    return new ResponseEntity<>(kosts.getContent(), HttpStatus.OK);
  }

  @Operation(summary = "Get List Kosts by Kost Typee", tags = {"Kost Management"})
  @GetMapping("/by-type/page")
  public ResponseEntity<?> getKostsByKostType(
          @RequestParam() @Schema(example = "1") int page,
          @RequestParam() @Schema(example = "10") int size,
          @RequestParam(required = false, defaultValue = "id") @Schema(example = "id") String orderBy,
          @RequestParam(required = false, defaultValue = "desc") @Schema(example = "desc") String orderType,
          @RequestParam() @Schema(example = "KOS_PUTRA") String kostType){
    Pageable pageable = simpleStringUtils.getShort(orderBy, orderType, page-1, size);
    Page<Kost> kosts = kostService.getKostsByKostType(kostType, pageable);
    logger.info("getKostsByKostType",kosts);
    return new ResponseEntity<>(kosts.getContent(), HttpStatus.OK);
  }

  @Operation(summary = "Get Kost by Id", tags = {"Kost Management"})
  @GetMapping("/{id}")
  public ResponseEntity<?> getKostById(@PathVariable("id") @Schema(example = "123e4567-e89b-12d3-a456-426614174000") UUID id){

    try {
      Kost kost = kostService.getKostById(id);
      return new ResponseEntity<>(kost, HttpStatus.OK);
    }
    catch (NoSuchElementException noSuchElementException){
      logger.error("Kost tidak ada", noSuchElementException);
      return new ResponseEntity<>("error : \"Kos doesn't exist\"", HttpStatus.NOT_FOUND);
    }
  }

  @Operation(summary = "Add Kost with kostType must ('KOS_PUTRA' or 'KOS_PUTRI' or 'KOS_CAMPURAN')", description = "Add Kost", tags = {"Kost Management"})
  @PostMapping(value = "/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<?> createKost(@RequestPart("imageFiles") MultipartFile[] imageFiles,
                                      @RequestParam("name") @Schema(example = "Kost Binar Academy") String name,
                                      @RequestParam("description") @Schema(example = "Description Binar Academy") String description,
                                      @RequestParam("kostType") @Schema(example = "KOS_CAMPURAN") String kostType,
                                      @RequestParam("isAvailable") Boolean isAvailable,
                                      @RequestParam("latitude") Double latitude,
                                      @RequestParam("longitude") Double longitude,
                                      @RequestParam("address") @Schema(example = "Jl Medan Merdeka No 69") String address,
                                      @RequestParam("subdistrict") @Schema(example = "Pengasinan") String subdistrict,
                                      @RequestParam("district") @Schema(example = "Rawalumbu") String district,
                                      @RequestParam("postalCode") @Schema(example = "18116") String postalCode,
                                      @RequestParam("cityId") Integer cityId,
                                      Authentication authentication){
    List<String> urls = new ArrayList<>();
    UUID uuid = UUID.randomUUID();
    Users user = iUserAuthService.findByUsername(authentication.getName());
    City cityKost = cityService.getCityById(cityId);

    if (imageFiles.length > 4){
      logger.error("Image lebih dari 4");
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
        logger.error("Kost tidak ada");
        return new ResponseEntity<>(templateCRUD.notFound("Kost Not Found"),HttpStatus.NOT_FOUND);
      } else {
        for (String url : urls) {
          imageService.saveImageKostToDb(url, currentKost);
        }
      }
      NewKostResponse kostResponse = new NewKostResponse(currentKost, currentKost.getOwnerId());
      logger.info("add kost", kostResponse);
      return new ResponseEntity<>(templateCRUD.templateSukses(kostResponse), HttpStatus.CREATED);
    } catch (Exception e){
      logger.error("Gagal Add Kost",e);
      return new ResponseEntity<>(templateCRUD.badRequest(e), HttpStatus.BAD_REQUEST);
    }
  }

  @Operation(summary = "Update Kost with kostType must ('KOS_PUTRA' or 'KOS_PUTRI' or 'KOS_CAMPURAN')", description = "Update Kost", tags = {"Kost Management"})
  @PatchMapping("/{id}")
  public ResponseEntity<?> updateKost(@PathVariable("id") @Schema(example = "123e4567-e89b-12d3-a456-426614174000") UUID id,
                                      @RequestParam("name") @Schema(example = "Kost Binar Academy") String name,
                                      @RequestParam("description") @Schema(example = "Description Binar Academy") String description,
                                      @RequestParam("kostType") @Schema(example = "KOS_CAMPURAN") String kostType,
                                      @RequestParam("isAvailable") Boolean isAvailable,
                                      @RequestParam("latitude") Double latitude,
                                      @RequestParam("longitude") Double longitude,
                                      @RequestParam("address") @Schema(example = "Jl Medan Merdeka No 69") String address,
                                      @RequestParam("subdistrict") @Schema(example = "Pengasinan") String subdistrict,
                                      @RequestParam("district") @Schema(example = "Rawalumbu") String district,
                                      @RequestParam("postalCode") @Schema(example = "18116") String postalCode,
                                      @RequestParam("cityId") Integer cityId){
    try {
        Kost currentKost = kostService.getKostById(id);
        if (currentKost == null) {
          return new ResponseEntity<>(templateCRUD.notFound("Kost Not Found"),HttpStatus.NOT_FOUND);
        }
        kostService.updateKost(id,name,description,kostType,isAvailable,latitude,longitude,address,subdistrict,district,postalCode,cityId);

      Kost updatedKost = kostService.getKostById(id);
      NewKostResponse updateKostRes = new NewKostResponse(updatedKost, updatedKost.getOwnerId());

      return new ResponseEntity<>(templateCRUD.templateSukses(updateKostRes), HttpStatus.OK);
    }
    catch (NoSuchElementException noSuchElementException){
      logger.error("Gagal update kost",noSuchElementException);
      return new ResponseEntity<>("error : \"Kos doesn't exist\"", HttpStatus.NOT_FOUND);
    }
  }

  @Operation(summary = "Hard Delete Kost by Id", tags = {"Kost Management"})
  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteKost(@PathVariable("id") @Schema(example = "123e4567-e89b-12d3-a456-426614174000") UUID id){

    try {
      return new ResponseEntity<>(kostService.deleteKost(id), HttpStatus.ACCEPTED);
    }
    catch (EmptyResultDataAccessException emptyResultDataAccessException){
      logger.error(String.valueOf(emptyResultDataAccessException));
      return new ResponseEntity<>("error : \"Kos doesn't exist\"", HttpStatus.NOT_FOUND);
    }
  }

  @Operation(summary = "Soft Delete Kost by Id", tags = {"Kost Management"})
  @DeleteMapping("/soft-delete/{id}")
  public ResponseEntity<?> softDeleteKost(@PathVariable("id") @Schema(example = "123e4567-e89b-12d3-a456-426614174000") UUID id){

    try {
      return new ResponseEntity<>(kostService.softDeleteKost(id), HttpStatus.NO_CONTENT);
    }
    catch (EmptyResultDataAccessException emptyResultDataAccessException){
      logger.error(String.valueOf(emptyResultDataAccessException));
      return new ResponseEntity<>("error : \"Kos doesn't exist\"", HttpStatus.NOT_FOUND);
    }
  }
}
