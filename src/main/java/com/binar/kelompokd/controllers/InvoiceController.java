package com.binar.kelompokd.controllers;

import com.binar.kelompokd.interfaces.InvoiceService;
import com.binar.kelompokd.models.response.transaction.TransactionOwnerBookingList;
import com.binar.kelompokd.models.response.transaction.TransactionOwnerDetail;
import com.binar.kelompokd.utils.SimpleStringUtils;
import com.binar.kelompokd.utils.response.Response;
import com.cloudinary.api.exceptions.NotFound;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/transaction")
@Tag(name = "Transaction Management", description = "APIs for Managing Transaction")
public class InvoiceController {
    @Autowired
    InvoiceService invoiceService;
    @Autowired
    SimpleStringUtils simpleStringUtils;
    @Autowired
    Response Response;
//
//    @Operation(summary = "Get All Transaction Invoice", tags = {"Transaction Management"})
//    @GetMapping("/invoice")
//    public ResponseEntity<?> getAllTransaction() {
//        try {
//            List<Invoice> invoices = invoiceService.getAllInvoice();
//            return new ResponseEntity<>(Response.templateSukses(invoices), HttpStatus.OK);
//        } catch (Exception e) {
//            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }


    @Operation(summary = "Get Owner History Transaction Confirmation", tags = {"Transaction Management"})
    @GetMapping("/owner/{ownerId}")
    public ResponseEntity<?> getPendingConfirmationById(@PathVariable("ownerId") @Schema(example = "12") Long ownerId) {
        try {
            List<TransactionOwnerBookingList> invoices = invoiceService.getTransactionOwnerById(ownerId);
            return new ResponseEntity<>(Response.templateSukses(invoices), HttpStatus.OK);
        } catch (NotFound nf){
            return new ResponseEntity<>(Response.notFound(nf.getMessage()), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            log.error("History Owner Transaction error : ", e);
            return new ResponseEntity<>(Response.templateEror(e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Get Owner Kos Transaction Detail", tags = {"Transaction Management"})
    @GetMapping("/owner/detail/{transactionId}")
    public ResponseEntity<?> getOwnerKosTransaction(@PathVariable("transactionId") @Schema(example = "123e4567-e89b-12d3-a456-426614174000") UUID transactionId) {
        try {
            TransactionOwnerDetail response = invoiceService.getOwnerKosById(transactionId);
            return new ResponseEntity<>(Response.templateSukses(response), HttpStatus.OK);
        } catch (NoSuchElementException nee){
            return new ResponseEntity<>(Response.notFound(nee.getMessage()), HttpStatus.NOT_FOUND);
        }catch (Exception e) {
            log.error("History Transaction error", e);
            return new ResponseEntity<>(Response.templateEror(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
