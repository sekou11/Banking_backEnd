package com.dsm.banking.services;

import java.util.List;

import com.dsm.banking.dto.CustomerDto;
import com.dsm.banking.entities.BankAccount;
import com.dsm.banking.entities.CurrentAccount;
import com.dsm.banking.entities.Customer;
import com.dsm.banking.entities.SavingAccount;
import com.dsm.banking.exceptions.BalanceNotSufficientException;
import com.dsm.banking.exceptions.BankAccountNotFoundException;
import com.dsm.banking.exceptions.CustomerNotFoundException;

public interface CustomerService {

	public CustomerDto saveCustomer(CustomerDto customerDto);

	public List<CustomerDto> listCustomer();

	public CustomerDto getCustomer(Long customerId) throws CustomerNotFoundException;
	
	public CustomerDto UpdtateCustomer(CustomerDto customerDto) ;
	
	public void deleteCustomer(Long customerId) ;

	
	
	

}
