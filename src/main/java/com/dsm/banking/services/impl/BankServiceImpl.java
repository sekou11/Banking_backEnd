package com.dsm.banking.services.impl;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dsm.banking.dao.AccountOperationDao;
import com.dsm.banking.dao.BankAccountDao;
import com.dsm.banking.dao.CustomerDao;
import com.dsm.banking.ennum.AccountStatus;
import com.dsm.banking.ennum.OperationType;
import com.dsm.banking.entities.AccountOperation;
import com.dsm.banking.entities.BankAccount;
import com.dsm.banking.entities.CurrentAccount;
import com.dsm.banking.entities.Customer;
import com.dsm.banking.entities.SavingAccount;
import com.dsm.banking.exceptions.BalanceNotSufficientException;
import com.dsm.banking.exceptions.BankAccountNotFoundException;
import com.dsm.banking.exceptions.CustomerNotFoundException;
import com.dsm.banking.services.BankService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
@AllArgsConstructor
public class BankServiceImpl implements BankService {
	private CustomerDao customerDao;
	private BankAccountDao bankAccountDao;
	private AccountOperationDao accountOperationDao;

	@Override
	public Customer saveCustomer(Customer customer) {
		Customer customerSaved = customerDao.save(customer);
		return customerSaved;
	}

	@Override
	public List<Customer> listCustomer() {
		// TODO Auto-generated method stub
		return customerDao.findAll();
	}

	@Override
	public CurrentAccount saveCurrentBankAccount(double initialBalance, double overDraft, Long customerId)
			throws CustomerNotFoundException {
		Customer customer = customerDao.findById(customerId).orElse(null);
		if (customer == null) {
			throw new CustomerNotFoundException("Customer not Found");
		}
		CurrentAccount ca = new CurrentAccount();
		ca.setId(UUID.randomUUID().toString());
		ca.setCreateAt(new Date());
		ca.setStatus(AccountStatus.CREATED);
		ca.setBalance(initialBalance);
		ca.setOverDraft(overDraft);
		ca.setCustomer(customer);
		CurrentAccount saveCurrentAccount = bankAccountDao.save(ca);
		return saveCurrentAccount;
	}

	@Override
	public SavingAccount saveSavingBankAccount(double initialBalance, double interestRate, Long customerId)
			throws CustomerNotFoundException {
		Customer customer = customerDao.findById(customerId).orElse(null);
		if (customer == null) {
			throw new CustomerNotFoundException("Customer not Found");
		}
		SavingAccount sa = new SavingAccount();
		sa.setId(UUID.randomUUID().toString());
		sa.setCreateAt(new Date());
		sa.setStatus(AccountStatus.CREATED);
		sa.setBalance(initialBalance);
		sa.setInterestRate(interestRate);
		sa.setCustomer(customer);
		SavingAccount saveSavingAccount = bankAccountDao.save(sa);
		return saveSavingAccount;
	}

	@Override
	public BankAccount getBankAccount(String accountId) throws BankAccountNotFoundException {
		BankAccount bankAccount = bankAccountDao.findById(accountId)
				.orElseThrow(() -> new BankAccountNotFoundException("Bank Account Not Found"));
		return bankAccount;
	}

	@Override
	public void debit(String accountId, double amount, String description)
			throws BankAccountNotFoundException, BalanceNotSufficientException {
		BankAccount bankAccountId = getBankAccount(accountId);

		if (bankAccountId.getBalance() < amount) {
			throw new BalanceNotSufficientException("Bablance Not Suffissant");
		}
		AccountOperation operation = new AccountOperation();
		operation.setType(OperationType.DEBIT);
		operation.setAmount(amount);
		operation.setDescription(description);
		operation.setBankAccount(bankAccountId);
		accountOperationDao.save(operation);
		bankAccountId.setBalance(bankAccountId.getBalance() - amount);
		bankAccountDao.save(bankAccountId);

	}

	@Override
	public void credit(String accountId, double amount, String description) throws BankAccountNotFoundException {
		BankAccount bankAccountId = getBankAccount(accountId);

		AccountOperation operation = new AccountOperation();
		operation.setType(OperationType.CREDIT);
		operation.setAmount(amount);
		operation.setDescription(description);
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

	@Override
	public List<BankAccount> listBankAccount() {
		
		return bankAccountDao.findAll();
	}

}
