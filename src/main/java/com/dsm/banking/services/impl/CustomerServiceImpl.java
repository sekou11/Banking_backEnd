package com.dsm.banking.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.dsm.banking.dao.AccountOperationDao;
import com.dsm.banking.dao.BankAccountDao;
import com.dsm.banking.dao.CustomerDao;
import com.dsm.banking.dto.CustomerDto;
import com.dsm.banking.entities.Customer;
import com.dsm.banking.exceptions.CustomerNotFoundException;
import com.dsm.banking.mappers.BankAccountMapperImpl;
import com.dsm.banking.services.CustomerService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@AllArgsConstructor
public class CustomerServiceImpl implements CustomerService{
  private CustomerDao customerDao;
  private BankAccountDao bankAccountDao;
  private AccountOperationDao accountOperationDao;
  
  private BankAccountMapperImpl dtoMapper;
  
	@Override
	public CustomerDto saveCustomer(CustomerDto customerDto) {
		Customer customer = dtoMapper.fromCustomerDto(customerDto);
		 Customer customerSaved = customerDao.save(customer);
		return dtoMapper.fromCustomer(customerSaved);
	}

	@Override
	public List<CustomerDto> listCustomer() {
		
		 List<Customer> customers = customerDao.findAll();
		 List<CustomerDto> customerDtos = customers.stream().
				 					map(customer->dtoMapper.fromCustomer(customer))
				 					.collect(Collectors.toList());
		 return customerDtos;
	}

	@Override
	public CustomerDto getCustomer(Long customerId) throws CustomerNotFoundException {
		Customer customer = customerDao.findById(customerId)
							.orElseThrow(()-> new CustomerNotFoundException("Customer Not Found") );
		return dtoMapper.fromCustomer(customer);
	}

	@Override
	public CustomerDto UpdtateCustomer(CustomerDto customerDto) {
		Customer customer = dtoMapper.fromCustomerDto(customerDto);
		 Customer customerSaved = customerDao.save(customer);
		return dtoMapper.fromCustomer(customerSaved);
	}

	@Override
	public void deleteCustomer(Long customerId) {
		customerDao.deleteById(customerId);
		
	}
	
	

	
}