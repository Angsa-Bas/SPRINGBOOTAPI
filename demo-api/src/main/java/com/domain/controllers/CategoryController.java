package com.domain.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import com.domain.Service.CategoryService; // Perbaiki nama paket
import com.domain.dto.CategoryData;
import com.domain.dto.ResponseData;
import com.domain.models.entity.Category;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/categories") // Perbaiki URL
public class CategoryController {
    
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity<ResponseData<Category>> create(@Valid @RequestBody CategoryData categoryData, Errors errors) {
        ResponseData<Category> responseData = new ResponseData<>();

        if (errors.hasErrors()) {
            for (ObjectError error : errors.getAllErrors()) {
                responseData.getMessages().add(error.getDefaultMessage());
            }
            responseData.setStatus(false);
            responseData.setPayload(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseData);
        }

        Category category = modelMapper.map(categoryData, Category.class);
        responseData.setStatus(true);
        responseData.setPayload(categoryService.Save(category));
        return ResponseEntity.ok(responseData);
    }

    @GetMapping
    public ResponseEntity<ResponseData<Iterable<Category>>> findAll() {
        ResponseData<Iterable<Category>> responseData = new ResponseData<>();
        responseData.setStatus(true);
        responseData.setPayload(categoryService.findAll());
        return ResponseEntity.ok(responseData);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseData<Category>> findOne(@PathVariable Long id) {
        Category category = categoryService.findOne(id);
        ResponseData<Category> responseData = new ResponseData<>();

        if (category == null) {
            responseData.setStatus(false);
            responseData.getMessages().add("Category not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseData); 
        }

        responseData.setStatus(true);
        responseData.setPayload(category);
        return ResponseEntity.ok(responseData);
    }

    @PutMapping("/{id}") 
    public ResponseEntity<ResponseData<Category>> update(@PathVariable Long id, @Valid @RequestBody CategoryData categoryData, Errors errors) {
        ResponseData<Category> responseData = new ResponseData<>();

        if (errors.hasErrors()) {
            for (ObjectError error : errors.getAllErrors()) {
                responseData.getMessages().add(error.getDefaultMessage());
            }
            responseData.setStatus(false);
            responseData.setPayload(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseData);
        }

        Category existingCategory = categoryService.findOne(id);
        if (existingCategory == null) {
            responseData.setStatus(false);
            responseData.getMessages().add("Category not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseData); 
        }


        Category updatedCategory = modelMapper.map(categoryData, Category.class);
        updatedCategory.setId(id);

        responseData.setStatus(true);
        responseData.setPayload(categoryService.Save(updatedCategory));
        return ResponseEntity.ok(responseData);
    }

    
}