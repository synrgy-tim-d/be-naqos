package com.binar.kelompokd.controllers;

import com.binar.kelompokd.interfaces.RoomService;
import com.binar.kelompokd.models.entity.kost.Facility;
import com.binar.kelompokd.models.entity.kost.Room;
import com.binar.kelompokd.models.request.FacilityRequest;
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

    @Autowired
    RoomService roomService;

    @Operation(summary = "Add Facility", tags = {"Facility Management"})
    @PostMapping()
    public ResponseEntity<?> addFacility(@RequestBody FacilityRequest facilityRequest){
        return new ResponseEntity<>(response.templateSukses(facilityService.addFacility(facilityRequest)), HttpStatus.OK);
    }

    @PostMapping("/room/{roomId}")
    public ResponseEntity<?> addFacilityToRoom(@PathVariable("roomId") UUID roomId, @RequestBody Facility facility){
        return new ResponseEntity<>(response.templateSukses(facilityService.addFacilityToRoom(roomId, facility)), HttpStatus.OK);
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
    public ResponseEntity<?> editFacility(@PathVariable("id") @Schema(example = "123e4567-e89b-12d3-a456-426614174000") UUID id, @RequestBody FacilityRequest facilityRequest){
        return new ResponseEntity<>(response.templateSukses(facilityService.editFacility(id, facilityRequest)), HttpStatus.OK);
    }

    @Operation(summary = "Delete Facility by Id", tags = {"Facility Management"})
    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteFacility(@PathVariable("id") @Schema(example = "123e4567-e89b-12d3-a456-426614174000") UUID id){
        facilityService.deleteFacility(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("{facilityId}/room/{roomId}")
    public ResponseEntity<?> deleteFacilityFromRoom(@PathVariable(value = "facilityId") @Schema(example = "123e4567-e89b-12d3-a456-426614174000") UUID facilityId, @PathVariable(value = "roomId") @Schema(example = "123e4567-e89b-12d3-a456-426614174000") UUID roomId) {
        return new ResponseEntity<>(facilityService.deleteFacilityFromRoom(facilityId, roomId), HttpStatus.NO_CONTENT);
    }
}
