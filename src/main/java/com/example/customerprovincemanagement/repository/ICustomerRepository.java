package com.example.customerprovincemanagement.repository;

import com.example.customerprovincemanagement.model.Customer;
import com.example.customerprovincemanagement.model.Province;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import org.springframework.stereotype.Repository;



@Repository
public interface ICustomerRepository extends CrudRepository<Customer, Long> {
    Iterable<Customer> findAllByProvince(Province province);

    Page<Customer> findAll(Pageable pageable);
    Page<Customer> findAllByFirstNameContaining(String name, Pageable pageable );
}
