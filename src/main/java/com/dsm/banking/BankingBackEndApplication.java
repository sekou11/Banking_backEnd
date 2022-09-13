package com.dsm.banking;

import java.util.stream.Stream;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.dsm.banking.dao.CustomerDao;
import com.dsm.banking.entities.Customer;

@SpringBootApplication
public class BankingBackEndApplication {

	public static void main(String[] args) {
		SpringApplication.run(BankingBackEndApplication.class, args);
	}
	@Bean
	public CommandLineRunner start(CustomerDao customerDao) {
		return args->{
			Stream.of("user1","user2","user3").forEach(name->{
				Customer c = new Customer();
				c.setEmail(name+"@email.com");
				c.setName(name);
				customerDao.save(c);
			});
		};
	}

}
