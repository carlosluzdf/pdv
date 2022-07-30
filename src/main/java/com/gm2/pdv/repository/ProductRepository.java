package com.gm2.pdv.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gm2.pdv.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
