package com.springprofessional.dscommerce.repositories;

import com.springprofessional.dscommerce.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
