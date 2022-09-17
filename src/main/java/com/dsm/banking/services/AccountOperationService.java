package com.dsm.banking.services;

import com.dsm.banking.exceptions.BalanceNotSufficientException;
import com.dsm.banking.exceptions.BankAccountNotFoundException;

public interface AccountOperationService {
	public void debit(String accountId, double amount, String description)
			throws BankAccountNotFoundException, BalanceNotSufficientException;

	public void credit(String accountId, double amount, String description) throws BankAccountNotFoundException;

	public void transfer(String accountIdSource, String accountIdDest, double amount)
			throws BankAccountNotFoundException, BalanceNotSufficientException;
	

}
