package com.binar.kelompokd.controllers;

import com.binar.kelompokd.models.request.RoomFacilityRequest;
import com.binar.kelompokd.interfaces.FacilityService;
import com.binar.kelompokd.utils.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@Tag(name = "Facility Management", description = "APIs for Managing Facility")
@RequestMapping("/facilities")
public class FacilityController {
    private final static Logger logger = LoggerFactory.getLogger(FacilityController.class);
    @Autowired
    FacilityService facilityService;

    @Autowired
    private Response response;

    @Operation(summary = "Add Facility", tags = {"Facility Management"})
    @PostMapping()
    public ResponseEntity<?> addFacility(@RequestBody RoomFacilityRequest roomFacilityRequest){
        return new ResponseEntity<>(response.templateSukses(facilityService.addFacility(roomFacilityRequest)), HttpStatus.OK);
    }

    @Operation(summary = "Get All List Facility", tags = {"Facility Management"})
    @GetMapping()
    public ResponseEntity<?> getAllFacilities(){
        return new ResponseEntity<>(response.templateSukses(facilityService.getAllFacilities()), HttpStatus.OK);
    }

    @Operation(summary = "Get Facility by Id", tags = {"Facility Management"})
    @GetMapping("/{id}")
    public ResponseEntity<?> getFacilityById(@PathVariable("id") @Schema(example = "123e4567-e89b-12d3-a456-426614174000") UUID id){
        return new ResponseEntity<>(response.templateSukses(facilityService.getFacilityById(id)), HttpStatus.OK);
    }

    @Operation(summary = "Update Facility by Id", tags = {"Facility Management"})
    @PatchMapping("{id}")
    public ResponseEntity<?> editFacility(@PathVariable("id") @Schema(example = "123e4567-e89b-12d3-a456-426614174000") UUID id, @RequestBody RoomFacilityRequest roomFacilityRequest){
        return new ResponseEntity<>(response.templateSukses(facilityService.editFacility(id, roomFacilityRequest)), HttpStatus.OK);
    }

    @Operation(summary = "Delete Facility by Id", tags = {"Facility Management"})
    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteFacility(@PathVariable("id") @Schema(example = "123e4567-e89b-12d3-a456-426614174000") UUID id){
        facilityService.deleteFacility(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
