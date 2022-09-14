package com.dsm.banking.services;

import java.util.List;

import com.dsm.banking.entities.BankAccount;
import com.dsm.banking.entities.CurrentAccount;
import com.dsm.banking.entities.Customer;
import com.dsm.banking.entities.SavingAccount;
import com.dsm.banking.exceptions.BalanceNotSufficientException;
import com.dsm.banking.exceptions.BankAccountNotFoundException;
import com.dsm.banking.exceptions.CustomerNotFoundException;

public interface BankService {

	public Customer saveCustomer(Customer customer);

	public List<Customer> listCustomer();

	public CurrentAccount saveCurrentBankAccount(double initialBalance, double overDraft, Long customerId)
			throws CustomerNotFoundException;

	public SavingAccount saveSavingBankAccount(double initialBalance, double interestRate, Long customerId)
			throws CustomerNotFoundException;

	public BankAccount getBankAccount(String accountId) throws BankAccountNotFoundException;

	public void debit(String accountId, double amount, String description)
			throws BankAccountNotFoundException, BalanceNotSufficientException;

	public void credit(String accountId, double amount, String description) throws BankAccountNotFoundException;

	public void transfer(String accountIdSource, String accountIdDest, double amount)
			throws BankAccountNotFoundException, BalanceNotSufficientException;
	
	public List<BankAccount> listBankAccount();

}
