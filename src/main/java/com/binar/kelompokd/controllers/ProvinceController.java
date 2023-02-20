package com.binar.kelompokd.controllers;

import com.binar.kelompokd.models.entity.location.Province;
import com.binar.kelompokd.interfaces.ProvinceService;
import com.binar.kelompokd.models.response.location.ProvinceResponse;
import com.binar.kelompokd.utils.response.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "Province Management", description = "APIs for Managing Province")
@RequestMapping("/provinces")
public class ProvinceController {
    private final static Logger logger = LoggerFactory.getLogger(ProvinceController.class);
    @Autowired
    ProvinceService provinceService;

    @Autowired
    public Response templateCRUD;

    @Operation(summary = "Get All List Province", tags = {"Province Management"})
    @GetMapping()
    public ResponseEntity<?> getAllProvinces(){
        try {
            List<Province> get = provinceService.getAllProvinces();
            ProvinceResponse res = new ProvinceResponse(get);
            logger.info("Get all Province",res);
            return new ResponseEntity<>(templateCRUD.templateSukses(get), HttpStatus.OK);
        } catch (Exception e){
            logger.error("get all province error",e);
            return new ResponseEntity<>(templateCRUD.notFound(e), HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Get Province by Id", tags = {"Province Management"})
    @GetMapping("/{id}")
    public ResponseEntity<?> getProvinceById(@PathVariable("id") @Schema(example = "1") Integer id){
        try {
            Province getData = provinceService.getProvinceById(id);
        return new ResponseEntity<>(templateCRUD.templateSukses(getData), HttpStatus.OK);
        } catch (Exception e){
            logger.error("get province by id error",e);
            return new ResponseEntity<>(templateCRUD.notFound("Province is not Found"),HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Add Province", tags = {"Province Management"})
    @PostMapping()
    public ResponseEntity<?> saveProvince(@RequestBody Province province){
        return new ResponseEntity<>(templateCRUD.templateSukses(provinceService.save(province)), HttpStatus.OK);
    }

    @Operation(summary = "Update Province by id", tags = {"Province Management"})
    @PatchMapping("/{id}")
    public ResponseEntity<?> updateProvince(@PathVariable("id") @Schema(example = "1") Integer id, @RequestBody Province province){
        return new ResponseEntity<>(templateCRUD.templateSukses(provinceService.update(id, province)), HttpStatus.OK);
    }

    @Operation(summary = "Delete Province by id", tags = {"Province Management"})
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProvince(@PathVariable("id") @Schema(example = "1") Integer id){
        provinceService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
