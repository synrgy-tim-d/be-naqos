package com.binar.kelompokd.controllers;

import com.binar.kelompokd.interfaces.RoomService;
import com.binar.kelompokd.models.entity.kost.Room;
import com.binar.kelompokd.models.request.RoomRequest;
import com.binar.kelompokd.models.response.KostRoomResponse;
import com.binar.kelompokd.utils.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@RestController
@Tag(name = "Kost Room Management", description = "APIs for Managing Kost Room")
@RequestMapping("/rooms")
public class RoomController {

    @Autowired
    RoomService roomService;

    @Autowired
    private Response response;

    @Operation(summary = "Add Kost Room", tags = {"Kost Room Management"})
    @PostMapping()
    public ResponseEntity<?> addRoom(@RequestBody RoomRequest roomRequest){
        return new ResponseEntity<>(response.templateSukses(roomService.addRoom(roomRequest)), HttpStatus.OK);
    }

    @Operation(summary = "Update Kost Room by Id", tags = {"Kost Room Management"})
    @PatchMapping("{id}")
    public ResponseEntity<?> editRoom(@PathVariable("id") @Schema(example = "123e4567-e89b-12d3-a456-426614174000") UUID id, @RequestBody RoomRequest roomRequest){
        try {
            return new ResponseEntity<>(response.templateSukses(roomService.updateRoom(id, roomRequest)), HttpStatus.OK);        }
        catch (NoSuchElementException noSuchElementException){
            return new ResponseEntity<>(response.notFound("Room doesn't exist"), HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Delete Kost Room by Id", tags = {"Kost Room Management"})
    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteRoom(@PathVariable("id") @Schema(example = "123e4567-e89b-12d3-a456-426614174000") UUID id){
        try {
            roomService.deleteRoom(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        catch (EmptyResultDataAccessException emptyResultDataAccessException){
            return new ResponseEntity<>(response.notFound("Room doesn't exist"), HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Soft Delete Kost Room by Id", tags = {"Kost Room Management"})
    @DeleteMapping("/soft-delete/{id}")
    public ResponseEntity<?> softDeleteRoom(@PathVariable("id") @Schema(example = "123e4567-e89b-12d3-a456-426614174000") UUID id){

        try {
            return new ResponseEntity<>(roomService.softDeleteRoom(id), HttpStatus.NO_CONTENT);
        }
        catch (EmptyResultDataAccessException emptyResultDataAccessException){
            return new ResponseEntity<>(response.notFound("Kos doesn't exist"), HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Get All List Kost Rooms", tags = {"Kost Room Management"})
    @GetMapping()
    public ResponseEntity<?> getAllRooms(){
        List<Room> room = roomService.getAllRooms();
        KostRoomResponse kostRoomResponse = KostRoomResponse.builder().room(room).build();
        return new ResponseEntity<>(response.templateSukses(room), HttpStatus.OK);
    }

    @GetMapping("/kost/{kostId}")
    public ResponseEntity<?> getAllAvailableRoomsByKostId(@PathVariable("kostId") UUID kostId){
        List<Room> rooms = roomService.getAllAvailableRoomsByKostId(kostId);
        KostRoomResponse kostRoomResponse = KostRoomResponse.builder().room(rooms).build();
        return new ResponseEntity<>(response.templateSukses(rooms), HttpStatus.OK);
    }

    @Operation(summary = "Get Kost Room by Id", tags = {"Kost Room Management"})
    @GetMapping("/{id}")
    public ResponseEntity<?> getRoomById(@PathVariable("id") @Schema(example = "123e4567-e89b-12d3-a456-426614174000") UUID id){
        try {
            return new ResponseEntity<>(response.templateSukses(roomService.getRoomById(id)), HttpStatus.OK);
        }
        catch (NoSuchElementException noSuchElementException){
            return new ResponseEntity<>(response.notFound("Room doesn't exist"), HttpStatus.NOT_FOUND);
        }
    }
}
