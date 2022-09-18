package com.dsm.banking.web;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dsm.banking.dto.AccountHistoryDto;
import com.dsm.banking.dto.AccountOperationDto;
import com.dsm.banking.dto.BankAccountDto;
import com.dsm.banking.exceptions.BankAccountNotFoundException;
import com.dsm.banking.services.AccountOperationService;
import com.dsm.banking.services.BankAccountServices;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class BankAccountRestApi {

	private BankAccountServices bankAccountService;
	private AccountOperationService accountOperationService;

	@GetMapping(path = "/account/{accountId}")
	public BankAccountDto getBankAccount(@PathVariable String accountId) throws BankAccountNotFoundException {
		return bankAccountService.getBankAccount(accountId);
	}

	@GetMapping(path = "/accounts")
	public List<BankAccountDto> lsiteAccount() {
		return bankAccountService.listBankAccount();
	}

	@GetMapping(path = "/account/{accountId}/operations")
	public List<AccountOperationDto> getHistorique(@PathVariable String accountId) {
		return accountOperationService.AccountHistorique(accountId);

	}

	@GetMapping(path ="/account/{accountId}/pageOperations")
	public AccountHistoryDto getAccountHistorique(@PathVariable String accountId,
			@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "page", defaultValue = "5") int size) throws BankAccountNotFoundException {
		return accountOperationService.getAccountHistory(accountId, page, size);

	}

}
