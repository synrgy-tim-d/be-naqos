package com.binar.kelompokd.controllers;

import com.binar.kelompokd.models.request.KostRoomRequest;
import com.binar.kelompokd.services.KostRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/rooms")
public class KostRoomController {

    @Autowired
    KostRoomService kostRoomService;

    @PostMapping("/add-room")
    public ResponseEntity<?> addRoom(@RequestBody KostRoomRequest kostRoomRequest){
        return new ResponseEntity<>(kostRoomService.addRoom(kostRoomRequest), HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<?> editRoom(@PathVariable("id") Integer id, @RequestBody KostRoomRequest kostRoomRequest){
        return new ResponseEntity<>(kostRoomService.updateRoom(id, kostRoomRequest), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteRoom(@PathVariable("id") Integer id){
        return new ResponseEntity<>(kostRoomService.deleteRoom(id), HttpStatus.ACCEPTED);
    }
}
