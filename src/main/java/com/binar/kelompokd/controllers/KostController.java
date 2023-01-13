package com.binar.kelompokd.controllers;

import com.binar.kelompokd.models.entity.Kost;
import com.binar.kelompokd.models.request.KostRequest;
import com.binar.kelompokd.services.KostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/kosts")
public class KostController {

    @Autowired
    KostService kostService;

    @GetMapping()
    public ResponseEntity<?> getAllKosts(){
        List<Kost> kosts =  kostService.getAllKost();
        return new ResponseEntity<>(kosts, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getKostById(@PathVariable("id") UUID id){
        Kost kost = kostService.getKostById(id).get();
        return new ResponseEntity<>(kost, HttpStatus.OK);
    }

    @PostMapping("/add-kost")
    public ResponseEntity<?> createKost(@RequestBody KostRequest kostRequest){
        Kost kost = kostService.createKost(kostRequest);
        return new ResponseEntity<>(kost, HttpStatus.CREATED);
    }

    @PutMapping("/edit-kost/{id}")
    public ResponseEntity<?> updateKost(@PathVariable("id") UUID id, @RequestBody KostRequest kostRequest){
        return new ResponseEntity<>(kostService.updateKost(id, kostRequest), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteKost(@PathVariable("id") UUID id){
        return new ResponseEntity<>(kostService.deleteKost(id), HttpStatus.ACCEPTED);
    }

}
