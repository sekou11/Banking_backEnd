package com.dsm.banking.dto;

import java.util.Date;
import java.util.List;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.dsm.banking.ennum.AccountStatus;
import com.dsm.banking.entities.AccountOperation;
import com.dsm.banking.entities.Customer;

import lombok.Data;

@Data
public class SavingBankAccountDto extends BankAccountDto {
	 
	private String id;
	private double balance;
	private Date createAt;
	private AccountStatus status;
	private CustomerDto customerDto;
	private double interestRate;
	
	
	

}
