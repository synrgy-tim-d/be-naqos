package com.binar.kelompokd.controllers;

import com.binar.kelompokd.models.entity.KostRoom;
import com.binar.kelompokd.models.request.KostRoomRequest;
import com.binar.kelompokd.models.response.KostRoomResponse;
import com.binar.kelompokd.services.KostRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/room")
public class KostRoomController {

    @Autowired
    KostRoomService kostRoomService;

    @PostMapping()
    public ResponseEntity<?> addRoom(@RequestBody KostRoomRequest kostRoomRequest){
        return new ResponseEntity<>(kostRoomService.addRoom(kostRoomRequest), HttpStatus.OK);
    }

    @PatchMapping("{id}")
    public ResponseEntity<?> editRoom(@PathVariable("id") Integer id, @RequestBody KostRoomRequest kostRoomRequest){
        return new ResponseEntity<>(kostRoomService.updateRoom(id, kostRoomRequest), HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteRoom(@PathVariable("id") Integer id){
        return new ResponseEntity<>(kostRoomService.deleteRoom(id), HttpStatus.NO_CONTENT);
    }

    @GetMapping()
    public ResponseEntity<?> getAllRooms(){
        List<KostRoom> room = kostRoomService.getAllRooms();
        KostRoomResponse kostRoomResponse = KostRoomResponse.builder().room(room).build();
        return new ResponseEntity<>(kostRoomResponse, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getRoomById(@PathVariable("id") Integer id){
        return new ResponseEntity<>(kostRoomService.getRoomById(id), HttpStatus.OK);
    }
}
