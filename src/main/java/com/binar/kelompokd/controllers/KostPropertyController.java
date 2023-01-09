package com.binar.kelompokd.controllers;

import com.binar.kelompokd.models.entity.KostProperty;
import com.binar.kelompokd.models.request.KostPropertyRequest;
import com.binar.kelompokd.services.KostPropertyService;
import com.binar.kelompokd.utils.SimpleStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("kost-property")
public class KostPropertyController {

    @Autowired
    KostPropertyService kostPropertyService;

    SimpleStringUtils simpleStringUtils = new SimpleStringUtils();

    @GetMapping("{page}/{size}/{orderBy}/{orderType}")
    public ResponseEntity<List<KostProperty>> getAllKost(@PathVariable("page") int page, @PathVariable("size") int size, @PathVariable("orderBy") String orderBy, @PathVariable("orderType") String orderType){
        List<KostProperty> kostProperties = kostPropertyService.getAllKost(page, size, orderBy, orderType);
        return new ResponseEntity<>(kostProperties, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<KostProperty> getKostById(@PathVariable("id") int id){
        KostProperty kostProperty = kostPropertyService.getKostById(id);
        return new ResponseEntity<>(kostProperty, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> createKost(@RequestBody KostPropertyRequest kostPropertyRequest){
        KostProperty kostProperty1 = kostPropertyService.save(kostPropertyRequest);
        return new ResponseEntity<>("Data on review", HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<String> updateKost(@PathVariable("id") int id, @RequestBody KostPropertyRequest kostPropertyRequest){
        kostPropertyService.updateKost(id, kostPropertyRequest);
        return new ResponseEntity<>("Kost updated successfully. Data on review", HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteKost(@PathVariable("id") int id){
        kostPropertyService.deleteKost(id);
        return new ResponseEntity<>("Kost deleted successfully", HttpStatus.OK);
    }
}
