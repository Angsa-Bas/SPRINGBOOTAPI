package com.domain.models.repost;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.repository.CrudRepository;

import com.domain.models.entity.Supplier;

public interface SupplierRepo extends JpaRepository<Supplier, Long> {   

    Supplier findByEmail(String email);

    List<Supplier> findByNameStartingWith(String ferfix);

    List<Supplier> findByNameContainsOrEmailContains(String name, String email);

    List<Supplier> findByNameContainingOrderByIdDesc(String name);
}
