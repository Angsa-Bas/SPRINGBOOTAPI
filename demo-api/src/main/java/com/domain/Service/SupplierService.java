package com.domain.Service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.domain.models.entity.Supplier;
import com.domain.models.repost.SupplierRepo;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class SupplierService {

    @Autowired
    private SupplierRepo supplierRepo; 

    public Supplier save(Supplier Supplier){
        return supplierRepo.save(Supplier);
    }

    public Supplier findOne(Long id){
        Optional<Supplier> supplier = supplierRepo.findById(id);
        if(supplier.isEmpty()){
            return null;
        }
        return supplier.get();
    }

    public Iterable<Supplier> findAll(){
    return supplierRepo.findAll();
    }

    public void removeOne(Long id){
        supplierRepo.deleteById(id);
    }

    public Supplier findByEmail(String email){
        return supplierRepo.findByEmail(email);
    }

    public List<Supplier> findByName(String name){
        return supplierRepo.findByNameContainingOrderByIdDesc(name);
    }

    public List<Supplier> findByNameStartingWith(String frefix){
        return supplierRepo.findByNameStartingWith(frefix);
    }

    public List<Supplier> findByNameOrEmail(String name, String email){
        return supplierRepo.findByNameContainsOrEmailContains(name, email);
    }
}
