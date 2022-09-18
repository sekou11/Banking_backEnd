package com.dsm.banking.dto;

import java.util.Date;

import com.dsm.banking.ennum.AccountStatus;

import lombok.Data;

@Data
public class CurrentBankAccountDto extends BankAccountDto {
	
	private String id;
	private double balance;
	private Date createAt;
	private AccountStatus status;
	private CustomerDto customerDto;
	private double overDraft;
	

}
