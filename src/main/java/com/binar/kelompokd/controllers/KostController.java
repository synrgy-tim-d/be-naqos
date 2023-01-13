package com.binar.kelompokd.controllers;

import com.binar.kelompokd.models.entity.Kost;
import com.binar.kelompokd.models.request.KostRequest;
import com.binar.kelompokd.models.request.KostRoomFacilityImageRequest;
import com.binar.kelompokd.models.response.KostResponse;
import com.binar.kelompokd.services.KostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/kos")
public class KostController {

    @Autowired
    KostService kostService;

    @GetMapping()
    public ResponseEntity<?> getAllKosts(){
        List<Kost> kostList =  kostService.getAllKost();
        KostResponse kostResponse = KostResponse.builder().kos(kostList).build();
        return new ResponseEntity<>(kostResponse, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getKostById(@PathVariable("id") UUID id){
        Kost kost = kostService.getKostById(id).get();
        return new ResponseEntity<>(kost, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<?> createKost(@RequestBody KostRequest kostRequest){
        Kost kost = kostService.createKost(kostRequest);
        return new ResponseEntity<>(kost, HttpStatus.OK);
    }

//    @PostMapping("/add-room")
//    public ResponseEntity<?> createRoom(@RequestBody KostRo roomRequest){
//        Kost kost = roomService.createKost(kostRequest);
//        return new ResponseEntity<>(kost, HttpStatus.CREATED);
//    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateKost(@PathVariable("id") UUID id, @RequestBody KostRequest kostRequest){
        return new ResponseEntity<>(kostService.updateKost(id, kostRequest), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteKost(@PathVariable("id") UUID id){
        return new ResponseEntity<>(kostService.deleteKost(id), HttpStatus.NO_CONTENT);
    }

    @PostMapping("/add-arrays")
    public ResponseEntity<?> addArrays(@RequestParam("kostId") UUID kostId, @RequestParam("roomId") Integer roomId,@RequestBody KostRoomFacilityImageRequest request){
        return new ResponseEntity<>(kostService.addArrays(kostId, roomId, request), HttpStatus.CREATED);
    }
}
