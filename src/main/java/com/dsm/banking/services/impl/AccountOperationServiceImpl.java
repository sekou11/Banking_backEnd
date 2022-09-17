package com.dsm.banking.services.impl;

import org.springframework.stereotype.Service;

import com.dsm.banking.dao.AccountOperationDao;
import com.dsm.banking.dao.BankAccountDao;
import com.dsm.banking.dao.CustomerDao;
import com.dsm.banking.ennum.OperationType;
import com.dsm.banking.entities.AccountOperation;
import com.dsm.banking.entities.BankAccount;
import com.dsm.banking.exceptions.BalanceNotSufficientException;
import com.dsm.banking.exceptions.BankAccountNotFoundException;
import com.dsm.banking.services.AccountOperationService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class AccountOperationServiceImpl implements AccountOperationService {

	private CustomerDao customerDao;
	private BankAccountDao bankAccountDao;
	private AccountOperationDao accountOperationDao;

	@Override
	public void debit(String accountId, double amount, String description)
			throws BankAccountNotFoundException, BalanceNotSufficientException {
		// on verifie si le compte existe
		BankAccount bankAccount = getBankAccount(accountId);

		// on verifie si le solde du compte n'est pas inferieur au montant à retirer
		if (bankAccount.getBalance() < amount) {
			throw new BalanceNotSufficientException("Balance Not Sufficient");
		}
		// on modifie les operations
		AccountOperation operation = new AccountOperation();
		operation.setType(OperationType.DEBIT);
		operation.setAmount(amount);
		operation.setBankAccount(bankAccount);
		accountOperationDao.save(operation);
		// on modifie le solde du compte avec le montant retirer
		bankAccount.setBalance(bankAccount.getBalance() - amount);
		bankAccountDao.save(bankAccount);

	}

	private BankAccount getBankAccount(String accountId) throws BankAccountNotFoundException {
		BankAccount bankAccount = bankAccountDao.findById(accountId)
				.orElseThrow(() -> new BankAccountNotFoundException("Bank Account Not Found"));
		return bankAccount;
	}

	@Override
	public void credit(String accountId, double amount, String description) throws BankAccountNotFoundException {
		// on verifie si le compte existe
		BankAccount bankAccountId = getBankAccount(accountId);
		// on modifie les operations
		AccountOperation operation = new AccountOperation();
		operation.setType(OperationType.CREDIT);
		operation.setAmount(amount);
		operation.setBankAccount(bankAccountId);
		accountOperationDao.save(operation);
		bankAccountId.setBalance(bankAccountId.getBalance() + amount);
		bankAccountDao.save(bankAccountId);

	}

	@Override
	public void transfer(String accountIdSource, String accountIdDest, double amount)
			throws BankAccountNotFoundException, BalanceNotSufficientException {
		debit(accountIdSource, amount, "Transfert to " + accountIdDest);
		credit(accountIdDest, amount, "Transfert from " + accountIdSource);

	}

}
