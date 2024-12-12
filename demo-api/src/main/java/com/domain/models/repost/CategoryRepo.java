package com.domain.models.repost;

import java.util.Optional;

//import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.domain.models.entity.Category;

public interface CategoryRepo extends PagingAndSortingRepository<Category, Long> {

    Category save(Category category);

    Optional<Category> findById(Long id);

    Iterable<Category> findAll();

    void deleteById(Long id); 
}
