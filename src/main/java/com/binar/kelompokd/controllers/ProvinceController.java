package com.binar.kelompokd.controllers;

import com.binar.kelompokd.models.entity.Province;
import com.binar.kelompokd.interfaces.ProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
