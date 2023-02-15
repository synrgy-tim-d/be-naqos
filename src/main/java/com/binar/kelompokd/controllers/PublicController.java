package com.binar.kelompokd.controllers;

import com.binar.kelompokd.interfaces.IKostReviewService;
import com.binar.kelompokd.interfaces.KostService;
import com.binar.kelompokd.models.QueryParams;
import com.binar.kelompokd.utils.response.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;
import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping("/public")
@Tag(name = "Public Management", description = "APIs for Managing Public Consumes")
public class PublicController {
  private final static Logger logger = LoggerFactory.getLogger(PublicController.class);
  @Autowired
  KostService kostService;
  @Autowired
  IKostReviewService kostReviewService;
  @Autowired
  Response Response;

  @Operation(summary = "Get All List Kosts", description = "Get All kost by search, filter, and fields",tags = {"Public Management"})
  @GetMapping("/kost")
  public ResponseEntity<?> getKost(QueryParams params) throws Exception {
    return new ResponseEntity<>(kostService.getKost(params), HttpStatus.OK);
  }
  @Operation(summary = "Get Review Kost", description = "Get Kost Review By Kost Id", tags = {"Public Management"})
  @GetMapping("/kost_review/{id}")
  public ResponseEntity<?> getKostReviewByKostId(@PathVariable("id") @Schema(example = "123e4567-e89b-12d3-a456-426614174000") UUID id){
    try {
      return new ResponseEntity<>(Response.templateSukses(kostReviewService.getReviewByKostId(id)), HttpStatus.OK);
    }
    catch (NoSuchElementException noSuchElementException){
      logger.error(noSuchElementException.toString());
      return new ResponseEntity<>(Response.notFound("Review doesn't exist"), HttpStatus.NOT_FOUND);
    }
  }
}
