package com.binar.kelompokd.controllers;

import com.binar.kelompokd.interfaces.CityService;
import com.binar.kelompokd.models.entity.location.Province;
import com.binar.kelompokd.interfaces.ProvinceService;
import com.binar.kelompokd.models.response.ProvinceResponse;
import com.binar.kelompokd.utils.Response;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/province")
public class ProvinceController {

    @Autowired
    ProvinceService provinceService;

    @Autowired
    CityService cityService;

    @Autowired
    public Response templateCRUD;

    @GetMapping()
    public ResponseEntity<?> getAllProvinces(){
        try {
            List<Province> get = provinceService.getAllProvinces();
            ProvinceResponse res = new ProvinceResponse(get);
            return new ResponseEntity<>(templateCRUD.templateSukses(res), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(templateCRUD.notFound(e), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProvinceById(@PathVariable("id") @Schema(example = "1") Integer id){
        try {
            Province getData = provinceService.getProvinceById(id);
        return new ResponseEntity<>(templateCRUD.templateSukses(getData), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(templateCRUD.notFound("Province is not Found"),HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping()
    public ResponseEntity<?> saveProvince(@RequestBody Province province){
        return new ResponseEntity<>(provinceService.save(province), HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateProvince(@PathVariable("id") @Schema(example = "1") Integer id, @RequestBody Province province){
        return new ResponseEntity<>(provinceService.update(id, province), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProvince(@PathVariable("id") @Schema(example = "1") Integer id){
        provinceService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
