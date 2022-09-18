package com.dsm.banking.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.dsm.banking.entities.AccountOperation;

public interface AccountOperationDao extends JpaRepository<AccountOperation, Long> {
 public List<AccountOperation>findByBankAccountId(String accountId);
 
 public Page<AccountOperation>findByBankAccountId(String accountId ,Pageable pageable);
}
