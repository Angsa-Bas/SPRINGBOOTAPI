package com.domain.models.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "tbl_suppliers")
@JsonIdentityInfo(
    generator = ObjectIdGenerators.PropertyGenerator.class,
    property = "id"
)
public class Supplier implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(length = 150, nullable = false)
    @NotBlank(message = "Nama tidak boleh kosong")
    private String name;

    @Column(length = 150, nullable = false)
    @NotBlank(message = "Address tidak boleh kosong")
    private String address;

    @Column(length = 150, nullable = false, unique = true)
    private String email;

   @ManyToMany(mappedBy = "suppliers")
   // @JsonBackReference
   private Set<Product> products = new HashSet<>(); 


   public Long getId() {
       return id; 
   }

   public void setId(Long id) {
       this.id = id; 
   }

   public String getName() {
       return name; 
   }

   public void setName(String name) {
       this.name = name; 
   }

   public String getAddress() {
       return address; 
   }

   public void setAddress(String address) {
       this.address = address; 
   }

   public String getEmail() {
       return email; 
   }

   public void setEmail(String email) {
       this.email = email; 
   }

   public Set<Product> getProducts() {
       return products; 
   }

   public void setProducts(Set<Product> products) {
       this.products = products; 
   }
}