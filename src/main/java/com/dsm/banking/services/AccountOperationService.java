package com.dsm.banking.services;

import java.util.List;

import com.dsm.banking.dto.AccountHistoryDto;
import com.dsm.banking.dto.AccountOperationDto;
import com.dsm.banking.exceptions.BalanceNotSufficientException;
import com.dsm.banking.exceptions.BankAccountNotFoundException;

public interface AccountOperationService {
	public void debit(String accountId, double amount, String description)
			throws BankAccountNotFoundException, BalanceNotSufficientException;

	public void credit(String accountId, double amount, String description) throws BankAccountNotFoundException;

	public void transfer(String accountIdSource, String accountIdDest, double amount)
			throws BankAccountNotFoundException, BalanceNotSufficientException;
	
	
	public List<AccountOperationDto>AccountHistorique(String accountId);

	public AccountHistoryDto getAccountHistory(String accountId, int page, int size) throws BankAccountNotFoundException;
	

}
