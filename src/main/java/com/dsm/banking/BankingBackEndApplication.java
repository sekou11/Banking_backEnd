package com.dsm.banking;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.dsm.banking.dao.AccountOperationDao;
import com.dsm.banking.dao.BankAccountDao;
import com.dsm.banking.dao.CustomerDao;
import com.dsm.banking.ennum.AccountStatus;
import com.dsm.banking.ennum.OperationType;
import com.dsm.banking.entities.AccountOperation;
import com.dsm.banking.entities.BankAccount;
import com.dsm.banking.entities.CurrentAccount;
import com.dsm.banking.entities.Customer;
import com.dsm.banking.entities.SavingAccount;
import com.dsm.banking.exceptions.BalanceNotSufficientException;
import com.dsm.banking.exceptions.BankAccountNotFoundException;
import com.dsm.banking.exceptions.CustomerNotFoundException;
import com.dsm.banking.services.BankService;

@SpringBootApplication
public class BankingBackEndApplication {

	public static void main(String[] args) {
		SpringApplication.run(BankingBackEndApplication.class, args);
	}

	@Bean
	public CommandLineRunner start1(BankService bankService) {
		return args -> {
			Stream.of("dia", "sekou", "momo").forEach(name -> {
				Customer customer = new Customer();
				customer.setName(name);
				customer.setEmail(name + "@email.com");
				bankService.saveCustomer(customer);
			});
			bankService.listCustomer().forEach(cust -> {
				try {

					bankService.saveCurrentBankAccount(Math.random() * 9000, 9000, cust.getId());
					bankService.saveSavingBankAccount(Math.random() * 120000, 5.5, cust.getId());

					List<BankAccount> bankAccountList = bankService.listBankAccount();
					for (BankAccount bankAccount : bankAccountList) {
						for (int i = 0; i < 10; i++) {
							bankService.credit(bankAccount.getId(), 10000 + Math.random() * 120000, "Credit");
							bankService.debit(bankAccount.getId(), 1000 + Math.random() * 9000, "Debit");
						}
					}

				} catch (CustomerNotFoundException | BankAccountNotFoundException | BalanceNotSufficientException e) {

					e.printStackTrace();
				}
			});

		};

	}

	// @Bean
	public CommandLineRunner start(CustomerDao customerDao, BankAccountDao bankAccountDao,
			AccountOperationDao accountOperationDao) {
		return args -> {
			Stream.of("user1", "user2", "user3").forEach(name -> {
				Customer c = new Customer();
				c.setEmail(name + "@email.com");
				c.setName(name);
				customerDao.save(c);
			});
			customerDao.findAll().forEach(cust -> {

				CurrentAccount ca = new CurrentAccount();
				ca.setId(UUID.randomUUID().toString());
				ca.setBalance(Math.random() * 90000);
				ca.setCreateAt(new Date());
				ca.setStatus(AccountStatus.CREATED);
				ca.setOverDraft(Math.random() * 9000);
				ca.setCustomer(cust);
				bankAccountDao.save(ca);

				SavingAccount sa = new SavingAccount();
				sa.setId(UUID.randomUUID().toString());
				sa.setBalance(Math.random() * 90000);
				sa.setCreateAt(new Date());
				sa.setStatus(AccountStatus.CREATED);
				sa.setInterestRate(Math.random() * 9000);
				sa.setCustomer(cust);
				bankAccountDao.save(sa);
			});
			bankAccountDao.findAll().forEach(account -> {
				for (int i = 0; i < 10; i++) {
					AccountOperation operation = new AccountOperation();
					operation.setOperationDate(new Date());
					operation.setAmount(Math.random() * 12000);
					operation.setDescription("description for operation");
					operation.setType(Math.random() > 0.5 ? OperationType.DEBIT : OperationType.CREDIT);
					operation.setBankAccount(account);
					accountOperationDao.save(operation);
				}

			});

		};
	}

}
