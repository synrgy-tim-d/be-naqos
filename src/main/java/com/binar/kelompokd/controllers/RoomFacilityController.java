package com.binar.kelompokd.controllers;

import com.binar.kelompokd.models.request.KostRoomRequest;
import com.binar.kelompokd.models.request.RoomFacilityRequest;
import com.binar.kelompokd.services.RoomFacilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/facility")
public class RoomFacilityController {

    @Autowired
    RoomFacilityService roomFacilityService;

    @PostMapping()
    public ResponseEntity<?> addFacility(@RequestBody RoomFacilityRequest roomFacilityRequest){
        return new ResponseEntity<>(roomFacilityService.addFacility(roomFacilityRequest), HttpStatus.OK);
    }

    @PatchMapping("{id}")
    public ResponseEntity<?> editFacility(@PathVariable("id") UUID id, @RequestBody RoomFacilityRequest roomFacilityRequest){
        return new ResponseEntity<>(roomFacilityService.editFacility(id, roomFacilityRequest), HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteFacility(@PathVariable("id") UUID id){
        roomFacilityService.deleteFacility(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
