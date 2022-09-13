package com.dsm.banking.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dsm.banking.entities.Customer;

public interface CustomerDao extends JpaRepository<Customer, Long> {
  
}
