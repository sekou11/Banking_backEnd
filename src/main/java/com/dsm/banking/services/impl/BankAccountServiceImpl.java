package com.dsm.banking.services.impl;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.dsm.banking.dao.BankAccountDao;
import com.dsm.banking.dao.CustomerDao;
import com.dsm.banking.dto.BankAccountDto;
import com.dsm.banking.dto.CurrentBankAccountDto;
import com.dsm.banking.dto.SavingBankAccountDto;
import com.dsm.banking.ennum.AccountStatus;
import com.dsm.banking.entities.BankAccount;
import com.dsm.banking.entities.CurrentAccount;
import com.dsm.banking.entities.Customer;
import com.dsm.banking.entities.SavingAccount;
import com.dsm.banking.exceptions.BankAccountNotFoundException;
import com.dsm.banking.exceptions.CustomerNotFoundException;
import com.dsm.banking.mappers.BankAccountMapperImpl;
import com.dsm.banking.services.BankAccountServices;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j

public class BankAccountServiceImpl implements BankAccountServices {

	private CustomerDao customerDao;
	private BankAccountDao bankAccountDao;

	private BankAccountMapperImpl dtoMapper;

	@Override
	public List<BankAccountDto> listBankAccount() {
		List<BankAccount> bankAccounts = bankAccountDao.findAll();

		List<BankAccountDto> bankAccountDtos = bankAccounts.stream().map(bankAccount -> {

			if (bankAccount instanceof SavingAccount) {
				SavingAccount savingAccount = (SavingAccount) bankAccount;
				return dtoMapper.fromSavingAccount(savingAccount);
			} else {
				CurrentAccount currentAccount = (CurrentAccount) bankAccount;
				return dtoMapper.fromcurrentAccount(currentAccount);
			}
		}).collect(Collectors.toList());
		return bankAccountDtos;

	}

	@Override
	public BankAccountDto getBankAccount(String accountId) throws BankAccountNotFoundException {
		BankAccount bankAccount = bankAccountDao.findById(accountId)
				.orElseThrow(() -> new BankAccountNotFoundException("Bank Account Not Found"));
		if (bankAccount instanceof SavingAccount) {
			SavingAccount savingAccount = (SavingAccount) bankAccount;
			return dtoMapper.fromSavingAccount(savingAccount);
		} else {
			CurrentAccount currentAccount = (CurrentAccount) bankAccount;
			return dtoMapper.fromcurrentAccount(currentAccount);
		}

	}

	@Override
	public CurrentBankAccountDto saveCurrentBankAccount(double initialBalance, double overDraft, Long customerId)
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

		return dtoMapper.fromcurrentAccount(currentAccountSaved);
	}

	@Override
	public SavingBankAccountDto saveSavingBankAccount(double initialBalance, double interestRate, Long customerId)
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
		return dtoMapper.fromSavingAccount(savingAccountSaved);
	}

}
