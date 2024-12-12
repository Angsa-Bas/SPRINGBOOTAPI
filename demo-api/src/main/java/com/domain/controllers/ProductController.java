package com.domain.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.domain.Service.ProductService;
import com.domain.dto.ResponseData;
import com.domain.dto.SearchData;
import com.domain.models.entity.Product;
import com.domain.models.entity.Supplier;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/product")
public class ProductController {

    @Autowired
    private ProductService productService; 

    @PostMapping
    public ResponseEntity<ResponseData<Product>> create(@RequestBody @Valid Product product, Errors errors) {
        ResponseData<Product> responseData = new ResponseData<>();

        if (errors.hasErrors()) {
            for (ObjectError error : errors.getAllErrors()) {
                responseData.getMessages().add(error.getDefaultMessage());
            }
            responseData.setStatus(false);
            responseData.setPayload(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseData);
        }

        responseData.setStatus(true);
        responseData.setPayload(productService.save(product));
        return ResponseEntity.ok(responseData);
    }

    @GetMapping
    public ResponseEntity<ResponseData<Iterable<Product>>> findAll() {
        ResponseData<Iterable<Product>> responseData = new ResponseData<>();
        responseData.setStatus(true);
        responseData.setPayload(productService.findAll());
        return ResponseEntity.ok(responseData);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseData<Product>> findOne(@PathVariable Long id) {
        Product product = productService.findOne(id);
        ResponseData<Product> responseData = new ResponseData<>();

        if (product == null) {
            responseData.setStatus(false);
            responseData.getMessages().add("Product not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseData);
        }

        responseData.setStatus(true);
        responseData.setPayload(product);
        return ResponseEntity.ok(responseData);
    }

    @PutMapping
    public ResponseEntity<ResponseData<Product>> update(@Valid @RequestBody Product product, Errors errors) {
        ResponseData<Product> responseData = new ResponseData<>();

        if (errors.hasErrors()) {
            for (ObjectError error : errors.getAllErrors()) {
                responseData.getMessages().add(error.getDefaultMessage());
            }
            responseData.setStatus(false);
            responseData.setPayload(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseData);
        }

        Product updatedProduct = productService.save(product); 
        responseData.setStatus(true);
        responseData.setPayload(updatedProduct);
        return ResponseEntity.ok(responseData);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseData<Void>> removeOne(@PathVariable Long id) {
        productService.removeOne(id);

        ResponseData<Void> responseData = new ResponseData<>();
        responseData.setStatus(true);
        return ResponseEntity.ok(responseData);
    }

    @PostMapping("/{id}")
    public void addSupplier(@RequestBody Supplier supplier, @PathVariable("id") Long productId ){
        productService.addSupplier(supplier, productId);
    }
    
    @PostMapping("/search/name")
    public Product getProductByName(@RequestBody SearchData searchData){
        return productService.findByProductName(searchData.getSearchKey());
    }

    @PostMapping("/search/namelike")
    public List<Product> getProductByNameLike(@RequestBody SearchData searchData){
        return productService.findByProductNameLike(searchData.getSearchKey());
    }
     
    @GetMapping("/search/category/{categoryId}")
    public List<Product> getProductByCategory(@PathVariable Long categoryId){
        return productService.findByCategory(categoryId);
   }

   @GetMapping("/search/supplier/{supplierId}")
    public List<Product> getProductBySupplier(@PathVariable Long supplierId){
        return productService.findBySupplier(supplierId);
    }
}