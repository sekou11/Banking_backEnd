package com.dsm.banking.services;

import java.util.List;

import com.dsm.banking.dto.BankAccountDto;
import com.dsm.banking.dto.CurrentBankAccountDto;
import com.dsm.banking.dto.SavingBankAccountDto;
import com.dsm.banking.entities.BankAccount;
import com.dsm.banking.exceptions.BankAccountNotFoundException;
import com.dsm.banking.exceptions.CustomerNotFoundException;

public interface BankAccountServices {

	public CurrentBankAccountDto saveCurrentBankAccount(double initialBalance, double overDraft, Long customerId)
			throws CustomerNotFoundException;

	public SavingBankAccountDto saveSavingBankAccount(double initialBalance, double interestRate, Long customerId)
			throws CustomerNotFoundException;

	public BankAccountDto getBankAccount(String accountId) throws BankAccountNotFoundException;

	public List<BankAccountDto> listBankAccount();

}
