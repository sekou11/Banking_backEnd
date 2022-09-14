package com.dsm.banking.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dsm.banking.entities.BankAccount;

public interface BankAccountDao extends JpaRepository<BankAccount, String> {

}
