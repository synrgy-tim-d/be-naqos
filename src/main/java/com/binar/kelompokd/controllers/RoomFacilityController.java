package com.binar.kelompokd.controllers;

import com.binar.kelompokd.models.request.KostRoomRequest;
import com.binar.kelompokd.models.request.RoomFacilityRequest;
import com.binar.kelompokd.services.RoomFacilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/facilities")
public class RoomFacilityController {

    @Autowired
    RoomFacilityService roomFacilityService;

    @PostMapping("/add-facility")
    public ResponseEntity<?> addFacility(@RequestBody RoomFacilityRequest roomFacilityRequest){
        return new ResponseEntity<>(roomFacilityService.addFacility(roomFacilityRequest), HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<?> editFacility(@PathVariable("id") Integer id, @RequestBody RoomFacilityRequest roomFacilityRequest){
        return new ResponseEntity<>(roomFacilityService.editFacility(id, roomFacilityRequest), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteFacility(@PathVariable("id") Integer id){
        return new ResponseEntity<>(roomFacilityService.deleteFacility(id), HttpStatus.ACCEPTED);
    }
}
