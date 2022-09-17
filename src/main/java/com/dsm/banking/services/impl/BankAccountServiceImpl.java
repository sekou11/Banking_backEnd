package com.dsm.banking.services.impl;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.dsm.banking.dao.AccountOperationDao;
import com.dsm.banking.dao.BankAccountDao;
import com.dsm.banking.dao.CustomerDao;
import com.dsm.banking.ennum.AccountStatus;
import com.dsm.banking.entities.BankAccount;
import com.dsm.banking.entities.CurrentAccount;
import com.dsm.banking.entities.Customer;
import com.dsm.banking.entities.SavingAccount;
import com.dsm.banking.exceptions.BankAccountNotFoundException;
import com.dsm.banking.exceptions.CustomerNotFoundException;
import com.dsm.banking.services.BankAccountServices;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j

public class BankAccountServiceImpl implements BankAccountServices {

	private CustomerDao customerDao;
	private BankAccountDao bankAccountDao;
	private AccountOperationDao accountOperationDao;

	@Override
	public List<BankAccount> listBankAccount() {

		return bankAccountDao.findAll();
	}

	@Override
	public BankAccount getBankAccount(String accountId) throws BankAccountNotFoundException {
		BankAccount bankAccount = bankAccountDao.findById(accountId)
				.orElseThrow(() -> new BankAccountNotFoundException("Bank Account Not Found"));
		return bankAccount;
	}

	@Override
	public CurrentAccount saveCurrentBankAccount(double initialBalance, double overDraft, Long customerId)
			throws CustomerNotFoundException {
		Customer customer = customerDao.findById(customerId).orElse(null);
		if (customer == null) {
			throw new CustomerNotFoundException("Customer Not Found");
		}
		CurrentAccount currentAccount = new CurrentAccount();
		currentAccount.setId(UUID.randomUUID().toString());
		currentAccount.setBalance(initialBalance);
		currentAccount.setCreateAt(new Date());
		currentAccount.setCustomer(customer);
		currentAccount.setStatus(AccountStatus.CREATED);
		currentAccount.setOverDraft(overDraft);
		CurrentAccount currentAccountSaved = bankAccountDao.save(currentAccount);

		return currentAccountSaved;
	}

	@Override
	public SavingAccount saveSavingBankAccount(double initialBalance, double interestRate, Long customerId)
			throws CustomerNotFoundException {
		Customer customer = customerDao.findById(customerId)
				.orElseThrow(() -> new CustomerNotFoundException("Customer Not Found"));
		SavingAccount savingAccount = new SavingAccount();
		savingAccount.setId(UUID.randomUUID().toString());
		savingAccount.setBalance(initialBalance);
		savingAccount.setCreateAt(new Date());
		savingAccount.setCustomer(customer);
		savingAccount.setStatus(AccountStatus.CREATED);
		savingAccount.setInterestRate(interestRate);
		SavingAccount savingAccountSaved = bankAccountDao.save(savingAccount);
		return savingAccountSaved;
	}

}
