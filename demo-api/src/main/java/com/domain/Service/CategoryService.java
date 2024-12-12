package com.domain.Service;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.domain.models.entity.Category;
import com.domain.models.repost.CategoryRepo;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class CategoryService {

    @Autowired
    private CategoryRepo categoryRepo;

    public Category Save(Category category){
        return categoryRepo.save(category);
    }
    
    public Category findOne(Long id){
        Optional<Category> category = categoryRepo.findById(id);
        if(category.isEmpty()){
            return null;
        }
        return category.get();
    }
    
    public Iterable<Category> findAll(){
        return categoryRepo.findAll();
    }

    public void removeOne(Long id){
        categoryRepo.deleteById(id);
    }
}   
