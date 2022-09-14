package com.dsm.banking.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dsm.banking.entities.AccountOperation;

public interface AccountOperationDao extends JpaRepository<AccountOperation, Long> {

}
