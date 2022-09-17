package com.dsm.banking.web;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.dsm.banking.dto.CustomerDto;
import com.dsm.banking.exceptions.CustomerNotFoundException;
import com.dsm.banking.services.CustomerService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@AllArgsConstructor
@Slf4j
public class CustomerRestController {
	
	private CustomerService customerService;
	
	 @GetMapping(path = "/customers")
	public List<CustomerDto>customers(){
		 log.info("all customers{}");
		return customerService.listCustomer();
	}
	 
	 @GetMapping(path = "/customer/{id}")
		public CustomerDto getcustomer(@PathVariable(name="id") Long customerId) throws CustomerNotFoundException{
			 log.info("get customer{}");
			return customerService.getCustomer(customerId);
		}
	 
	 @PostMapping(path = "/saveCustomer")
		public CustomerDto saveCustomer( @RequestBody CustomerDto customerDto) {
			 log.info("save customer{}");
			return customerService.saveCustomer(customerDto);
		}
	 
	  @PutMapping(path="/updateCustomer/{customerId}")
	 public CustomerDto updateCustomer(@PathVariable Long customerId , @RequestBody CustomerDto customerDto) {
		 customerDto.setId(customerId);
		 return customerService.UpdtateCustomer(customerDto);
	 }
	  
	  @DeleteMapping(path="/deleteCustomer/{customerId}")
	  public void deleteCustomer(@PathVariable Long customerId) {
		  customerService.deleteCustomer(customerId);
	  }

}
