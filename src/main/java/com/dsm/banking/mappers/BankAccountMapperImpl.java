package com.dsm.banking.mappers;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.dsm.banking.dto.CustomerDto;
import com.dsm.banking.entities.Customer;

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

}
