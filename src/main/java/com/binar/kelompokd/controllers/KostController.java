package com.binar.kelompokd.controllers;

import com.binar.kelompokd.models.entity.kost.Kost;
import com.binar.kelompokd.models.request.KostRequest;
import com.binar.kelompokd.models.response.KostResponse;
import com.binar.kelompokd.interfaces.KostService;
import com.binar.kelompokd.utils.SimpleStringUtils;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@RestController
@RequestMapping("/kos")
public class KostController {

    @Autowired
    KostService kostService;

    @Autowired
    SimpleStringUtils simpleStringUtils;

    @GetMapping()
    public ResponseEntity<?> getAllKosts(){
        List<Kost> kostList =  kostService.getAllKost();
        KostResponse kostResponse = KostResponse.builder().kos(kostList).build();
        return new ResponseEntity<>(kostResponse, HttpStatus.OK);
    }

    @GetMapping("/page")
    public ResponseEntity<?> getAllKostsWithPaginationAndFilter(@RequestParam() @Schema(example = "1") int page, @RequestParam() @Schema(example = "10") int size, @RequestParam(required = false, defaultValue = "id") @Schema(example = "id") String orderBy, @RequestParam(required = false, defaultValue = "desc") @Schema(example = "desc") String orderType){
        Pageable pageable = simpleStringUtils.getShort(orderBy, orderType, page-1, size);
        Page<Kost> kosts = kostService.getListData(pageable);
        return new ResponseEntity<>(kosts.getContent(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getKostById(@PathVariable("id") @Schema(example = "123e4567-e89b-12d3-a456-426614174000") UUID id){

        try {
            Kost kost = kostService.getKostById(id).get();
            return new ResponseEntity<>(kost, HttpStatus.OK);
        }
        catch (NoSuchElementException noSuchElementException){
            return new ResponseEntity<>("error : \"Kos doesn't exist\"", HttpStatus.NOT_FOUND);
        }
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
    public ResponseEntity<?> updateKost(@PathVariable("id") @Schema(example = "123e4567-e89b-12d3-a456-426614174000") UUID id, @RequestBody KostRequest kostRequest){
        try {
            return new ResponseEntity<>(kostService.updateKost(id, kostRequest), HttpStatus.OK);
        }
        catch (NoSuchElementException noSuchElementException){
            return new ResponseEntity<>("error : \"Kos doesn't exist\"", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteKost(@PathVariable("id") @Schema(example = "123e4567-e89b-12d3-a456-426614174000") UUID id){

        try {
            return new ResponseEntity<>(kostService.deleteKost(id), HttpStatus.NO_CONTENT);
        }
        catch (EmptyResultDataAccessException emptyResultDataAccessException){
            return new ResponseEntity<>("error : \"Kos doesn't exist\"", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/soft-delete/{id}")
    public ResponseEntity<?> softDeleteKost(@PathVariable("id") @Schema(example = "123e4567-e89b-12d3-a456-426614174000") UUID id){

        try {
            return new ResponseEntity<>(kostService.softDeleteKost(id), HttpStatus.NO_CONTENT);
        }
        catch (EmptyResultDataAccessException emptyResultDataAccessException){
            return new ResponseEntity<>("error : \"Kos doesn't exist\"", HttpStatus.NOT_FOUND);
        }
    }

//    @PostMapping("/add-arrays")
//    public ResponseEntity<?> addArrays(@RequestParam("kostId") UUID kostId, @RequestParam("roomId") UUID roomId,@RequestBody KostRoomFacilityImageRequest request){
//        return new ResponseEntity<>(kostService.addArrays(kostId, roomId, request), HttpStatus.CREATED);
//    }
}
