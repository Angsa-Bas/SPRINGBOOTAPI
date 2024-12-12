package com.domain.controllers;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import com.domain.Service.SupplierService;
import com.domain.dto.ResponseData;
import com.domain.dto.SearchData;
import com.domain.dto.SupplierData;
import com.domain.models.entity.Supplier;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/supplier")
public class SupplierController {

    @Autowired
    private SupplierService supplierService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity<ResponseData<Supplier>> create(@Valid @RequestBody SupplierData supplierData, Errors errors) {
        ResponseData<Supplier> responseData = new ResponseData<>();

        if (errors.hasErrors()) {
            for (ObjectError error : errors.getAllErrors()) {
                responseData.getMessages().add(error.getDefaultMessage());
            }
            responseData.setStatus(false);
            responseData.setPayload(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseData);
        }

        Supplier supplier = modelMapper.map(supplierData, Supplier.class);
        responseData.setStatus(true);
        responseData.setPayload(supplierService.save(supplier));
        return ResponseEntity.ok(responseData);
    }

    @GetMapping
    public ResponseEntity<ResponseData<Iterable<Supplier>>> findAll() {
        ResponseData<Iterable<Supplier>> responseData = new ResponseData<>();
        responseData.setStatus(true);
        responseData.setPayload(supplierService.findAll());
        return ResponseEntity.ok(responseData);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseData<Supplier>> findOne(@PathVariable Long id) {
        Supplier supplier = supplierService.findOne(id);
        ResponseData<Supplier> responseData = new ResponseData<>();

        if (supplier == null) {
            responseData.setStatus(false);
            responseData.getMessages().add("Supplier not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseData);
        }

        responseData.setStatus(true);
        responseData.setPayload(supplier);
        return ResponseEntity.ok(responseData);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ResponseData<Supplier>> update(@PathVariable Long id, @Valid @RequestBody SupplierData supplierData, Errors errors) {
        ResponseData<Supplier> responseData = new ResponseData<>();

        if (errors.hasErrors()) {
            for (ObjectError error : errors.getAllErrors()) {
                responseData.getMessages().add(error.getDefaultMessage());
            }
            responseData.setStatus(false);
            responseData.setPayload(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseData);
        }

        Supplier existingSupplier = supplierService.findOne(id);
        if (existingSupplier == null) {
            responseData.setStatus(false);
            responseData.getMessages().add("Supplier not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseData);
        }

        Supplier updatedSupplier = modelMapper.map(supplierData, Supplier.class);
        updatedSupplier.setId(id);

        responseData.setStatus(true);
        responseData.setPayload(supplierService.save(updatedSupplier));
        return ResponseEntity.ok(responseData);
    }

    @PostMapping("/search/byemail")
    public Supplier findByEmail(@RequestBody SearchData searchData){
        return supplierService.findByEmail(searchData.getSearchKey());
    }

    @PostMapping("/search/name")
    public List<Supplier> findByName(@RequestBody SearchData searchData){
        return supplierService.findByName(searchData.getSearchKey());
    }

    @PostMapping("/search/namestartwith")
    public List<Supplier> findByNamestartWith(@RequestBody SearchData searchData){
        return supplierService.findByNameStartingWith(searchData.getSearchKey());
    }

    @PostMapping("/search/nameoremail")
    public List<Supplier> findByNameOrEmail(@RequestBody SearchData searchData){
        return supplierService.findByNameOrEmail(searchData.getOtherSearchKey(), searchData.getOtherSearchKey());
    }
}