package com.dsm.banking.services.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.dsm.banking.dao.AccountOperationDao;
import com.dsm.banking.dao.BankAccountDao;
import com.dsm.banking.dao.CustomerDao;
import com.dsm.banking.dto.AccountHistoryDto;
import com.dsm.banking.dto.AccountOperationDto;
import com.dsm.banking.ennum.OperationType;
import com.dsm.banking.entities.AccountOperation;
import com.dsm.banking.entities.BankAccount;
import com.dsm.banking.exceptions.BalanceNotSufficientException;
import com.dsm.banking.exceptions.BankAccountNotFoundException;
import com.dsm.banking.mappers.BankAccountMapperImpl;
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
	private BankAccountMapperImpl dtoMapper;

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
		operation.setDescription(description);
		operation.setOperationDate(new Date());
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
		operation.setDescription(description);
		operation.setOperationDate(new Date());
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
	public List<AccountOperationDto> AccountHistorique(String accountId) {
		  List<AccountOperation> accountOperationsList = accountOperationDao.findByBankAccountId(accountId);
		  return accountOperationsList.stream().map(op->dtoMapper.fromAccountOperation(op)).collect(Collectors.toList());
		 
	}

	@Override
	public AccountHistoryDto getAccountHistory(String accountId, int page, int size) throws BankAccountNotFoundException {
		BankAccount bankAccount = getBankAccount(accountId);
		Page<AccountOperation> accountOperations = accountOperationDao.findByBankAccountId(accountId, PageRequest.of(page,size));
		AccountHistoryDto accountHistoryDto = new AccountHistoryDto();
		List<AccountOperationDto> accountoperationDtos = accountOperations.getContent().stream().map(op->dtoMapper.fromAccountOperation(op)).collect(Collectors.toList());
		accountHistoryDto.setAccountOperationDtos(accountoperationDtos);
		accountHistoryDto.setAccountId(bankAccount.getId());
		accountHistoryDto.setBalance(bankAccount.getBalance());
		accountHistoryDto.setCurrentPage(page);
		accountHistoryDto.setPageSize(size);
		accountHistoryDto.setTotalPage(accountOperations.getTotalPages());
		
		return accountHistoryDto;
	}

}
