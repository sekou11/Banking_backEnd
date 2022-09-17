package com.dsm.banking.services;

import java.util.List;

import com.dsm.banking.entities.BankAccount;
import com.dsm.banking.entities.CurrentAccount;
import com.dsm.banking.entities.SavingAccount;
import com.dsm.banking.exceptions.BankAccountNotFoundException;
import com.dsm.banking.exceptions.CustomerNotFoundException;

public interface BankAccountServices {

	public CurrentAccount saveCurrentBankAccount(double initialBalance, double overDraft, Long customerId)
			throws CustomerNotFoundException;

	public SavingAccount saveSavingBankAccount(double initialBalance, double interestRate, Long customerId)
			throws CustomerNotFoundException;

	public BankAccount getBankAccount(String accountId) throws BankAccountNotFoundException;

	public List<BankAccount> listBankAccount();

}
