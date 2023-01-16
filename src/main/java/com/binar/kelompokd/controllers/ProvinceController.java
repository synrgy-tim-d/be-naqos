package com.binar.kelompokd.controllers;

import com.binar.kelompokd.models.entity.Kost;
import com.binar.kelompokd.models.entity.Province;
import com.binar.kelompokd.models.request.KostRequest;
import com.binar.kelompokd.models.request.KostRoomFacilityImageRequest;
import com.binar.kelompokd.models.response.KostResponse;
import com.binar.kelompokd.services.ProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/province")
public class ProvinceController {

    @Autowired
    ProvinceService provinceService;

    @GetMapping()
    public ResponseEntity<?> getAllProvinces(){
        return new ResponseEntity<>(provinceService.getAllProvinces(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProvinceById(@PathVariable("id") Integer id){
        return new ResponseEntity<>(provinceService.getProvinceById(id), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<?> saveProvince(@RequestBody Province province){
        return new ResponseEntity<>(provinceService.save(province), HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateProvince(@PathVariable("id") Integer id, @RequestBody Province province){
        return new ResponseEntity<>(provinceService.update(id, province), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProvince(@PathVariable("id") Integer id){
        provinceService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
