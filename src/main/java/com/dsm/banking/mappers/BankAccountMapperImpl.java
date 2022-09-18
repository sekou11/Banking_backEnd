package com.dsm.banking.mappers;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.dsm.banking.dto.AccountOperationDto;
import com.dsm.banking.dto.CurrentBankAccountDto;
import com.dsm.banking.dto.CustomerDto;
import com.dsm.banking.dto.SavingBankAccountDto;
import com.dsm.banking.entities.AccountOperation;
import com.dsm.banking.entities.CurrentAccount;
import com.dsm.banking.entities.Customer;
import com.dsm.banking.entities.SavingAccount;

@Service
public class BankAccountMapperImpl {
	
	public CustomerDto fromCustomer(Customer customer) {
		CustomerDto customerDto = new CustomerDto();
		BeanUtils.copyProperties(customer, customerDto);
		return customerDto;
	}
	
	
	public Customer fromCustomerDto(CustomerDto customerDto) {
		Customer customer = new Customer();
		BeanUtils.copyProperties(customerDto, customer);
		return customer;
	}
	
	public SavingBankAccountDto fromSavingAccount(SavingAccount savingAccount) {
		 SavingBankAccountDto savingBankAccountDto = new SavingBankAccountDto();
		 BeanUtils.copyProperties(savingAccount, savingBankAccountDto);
		 savingBankAccountDto.setCustomerDto(fromCustomer(savingAccount.getCustomer()));
		 savingBankAccountDto.setType(savingAccount.getClass().getSimpleName());
		 return savingBankAccountDto;
		
	}
	
	
	public SavingAccount fromSavingAccountDto(SavingBankAccountDto savingBankAccountDto) {
		 SavingAccount savingAccount = new SavingAccount();
		 BeanUtils.copyProperties(savingBankAccountDto, savingAccount);
		 savingAccount.setCustomer(fromCustomerDto(savingBankAccountDto.getCustomerDto()));
		 savingBankAccountDto.setType(savingAccount.getClass().getSimpleName());
	
		 return savingAccount;
		
	}
	
	
	
	public CurrentBankAccountDto fromcurrentAccount(CurrentAccount currentAccount) {
		CurrentBankAccountDto currentBankAccountDto = new CurrentBankAccountDto();
		BeanUtils.copyProperties(currentAccount, currentBankAccountDto);
		currentBankAccountDto.setCustomerDto(fromCustomer(currentAccount.getCustomer()));
		currentBankAccountDto.setType(currentAccount.getClass().getSimpleName());
			return currentBankAccountDto;
		
	}
	
	
	

	public CurrentAccount fromcurrentAccountDto(CurrentBankAccountDto currentBankAccountDto) {
		CurrentAccount currentAccount = new CurrentAccount();
		BeanUtils.copyProperties(currentBankAccountDto, currentAccount);
		currentAccount.setCustomer(fromCustomerDto(currentBankAccountDto.getCustomerDto()));
			return currentAccount;
		
	}
	
	public AccountOperationDto fromAccountOperation(AccountOperation accountOperation) {
		AccountOperationDto accountOperationDto = new AccountOperationDto();
		BeanUtils.copyProperties(accountOperation, accountOperationDto);
		return accountOperationDto;
		
	}
	
//	public AccountOperation fromAccountOperationDto(AccountOperationDto accountOperationDto) {
//		AccountOperation accountOperation = new AccountOperation();
//		BeanUtils.copyProperties(accountOperationDto, accountOperation);
//		return accountOperation;
//		
//	}

}
