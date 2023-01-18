package com.binar.kelompokd.controllers;

import com.binar.kelompokd.models.entity.KostRoom;
import com.binar.kelompokd.models.request.KostRoomRequest;
import com.binar.kelompokd.models.response.KostRoomResponse;
import com.binar.kelompokd.interfaces.KostRoomService;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

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
    public ResponseEntity<?> editRoom(@PathVariable("id") @Schema(example = "123e4567-e89b-12d3-a456-426614174000") UUID id, @RequestBody KostRoomRequest kostRoomRequest){
        try {
            return new ResponseEntity<>(kostRoomService.updateRoom(id, kostRoomRequest), HttpStatus.OK);        }
        catch (NoSuchElementException noSuchElementException){
            return new ResponseEntity<>("error : \"Room doesn't exist\"", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteRoom(@PathVariable("id") @Schema(example = "123e4567-e89b-12d3-a456-426614174000") UUID id){
        try {
            kostRoomService.deleteRoom(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        catch (EmptyResultDataAccessException emptyResultDataAccessException){
            return new ResponseEntity<>("error : \"Room doesn't exist\"", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping()
    public ResponseEntity<?> getAllRooms(){
        List<KostRoom> room = kostRoomService.getAllRooms();
        KostRoomResponse kostRoomResponse = KostRoomResponse.builder().room(room).build();
        return new ResponseEntity<>(kostRoomResponse, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getRoomById(@PathVariable("id") @Schema(example = "123e4567-e89b-12d3-a456-426614174000") UUID id){
        try {
            return new ResponseEntity<>(kostRoomService.getRoomById(id), HttpStatus.OK);
        }
        catch (NoSuchElementException noSuchElementException){
            return new ResponseEntity<>("error : \"Room doesn't exist\"", HttpStatus.NOT_FOUND);
        }
    }
}
